package net.golovach.rholp.lexer;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.LineMapImpl;
import net.golovach.rholp.log.impl.NopListener;
import net.golovach.rholp.msg.AbsentKeywords;
import net.golovach.rholp.msg.MisspelledKeywords;

import java.io.IOException;
import java.util.*;

import static java.lang.Character.*;
import static java.lang.String.format;
import static net.golovach.rholp.RhoTokenType.*;

public class RhoLexer {
    public static final String FILE_LEXER_NOTES = "/lexer-notes.properties";
    public static final String FILE_LEXER_WARNS = "/lexer-warns.properties";
    public static final String FILE_LEXER_ERRORS = "/lexer-errors.properties";

    private final Deque<RhoToken> returnedTokens = new ArrayDeque<>();
    private final DiagnosticListener listener;
    private LexerState state;

    public RhoLexer(String content) {
        this(content, new NopListener());
    }

    public RhoLexer(String content, DiagnosticListener listener) {
        this.listener = listener;
        this.state = new LexerState(content, listener, new LineMapImpl(content), loadDefaultMessages());
    }

    public RhoLexer(String content, DiagnosticListener listener, Properties messages) {
        this.listener = listener;
        this.state = new LexerState(content, listener, new LineMapImpl(content), messages);
    }

    private Properties loadDefaultMessages() {
        Properties notes = new Properties();
        Properties warns = new Properties();
        Properties errors = new Properties();
        Properties result = new Properties();

        try {
            notes.load(getClass().getResourceAsStream(FILE_LEXER_NOTES));
            warns.load(getClass().getResourceAsStream(FILE_LEXER_WARNS));
            errors.load(getClass().getResourceAsStream(FILE_LEXER_ERRORS));

            result.putAll(notes);
            result.putAll(warns);
            result.putAll(errors);

            return result;
        } catch (IOException ex) {
            throw new RuntimeException(ex); //todo: RuntimeException?
        }
    }

    public RhoToken readToken() {
        RhoToken result = readTokenImpl();
        returnedTokens.add(result);
        return result;
    }

    private RhoToken readTokenImpl() {
        while (true) {
            state = state.cleanMem();

            if (state.isEOF()) {
                listener.eof();
                return EOF.token();
            }

            if (state.isLetter()) {
                return processLetter();
            }

            if (state.isDigit()) {
                return processNumber();
            }

            switch (state.ch()) {
                // ============================== Whitespaces
                case ' ':
                case '\t':
                case '\f':
                    skipChar();
                    return readToken();
                // ============================== Line terminations
                case '\n':
                case '\r':
                    return processLineEnd();
                // ============================== Separators: (, ), [, ], {, }
                case '(':
                    skipChar();
                    return LPAREN.token();
                case ')':
                    skipChar();
                    return RPAREN.token();
                case '[':
                    skipChar();
                    return LBRACKET.token();
                case ']':
                    skipChar();
                    return RBRACKET.token();
                case '{':
                    skipChar();
                    return LBRACE.token();
                case '}':
                    skipChar();
                    return RBRACE.token();
                // ============================== Separators: . , ; : =
                case '.':
                    return processDot();
                case ',':
                    skipChar();
                    return COMMA.token();
                case ';':
                    skipChar();
                    return SEMI.token();
                case ':':
                    return processColon();
                case '=':
                    return processEq();
                // ============================== Under: '_'
                case '_':
                    return processUnder();
                // ============================== Operators (used): +, -, *, /, >, <, |, !, ~, @
                case '+':
                    return processPlus();
                case '-':
                    return processMinus();
                case '*':
                    return processStar();
                case '/':
                    return processDiv();
                case '\\':
                    return processBackSlash();
                case '<':
                    return processLt();
                case '>':
                    return processGt();
                case '|':
                    return processBar();
                case '!':
                    return processBang();
                case '~':
                    return processTilde();
                case '@':
                    skipChar();
                    return QUOTE.token();
                // ============================== Operators (unused): &, %, ^, ?, #, $
                case '&':
                    return processAmp();
                case '%':
                    return processPercent();
                case '^':
                    return processHat();
                case '?':
                    return processQuestion();
                case '#':
                    return processHash();
                case '$':
                    return processDollar();
                // ============================== String-like
                case '"':
                    return processString();
                case '`':
                    return processUri();
                case '\'':
                    return processQuote();
                default:
                    return processDefault();
            }
        }
    }

    public List<RhoToken> readAll() {
        List<RhoToken> result = new ArrayList<>();

        RhoToken token = readToken();
        while (token.type != EOF) {
            result.add(token);
            token = readToken();
        }
        result.add(token);

        return result;
    }

    private RhoToken processLetter() {
        memChar();
        while (!state.isEOF() && (state.isLetter() || state.isDigit() || state.ch() == '_')) {
            memChar();
        }

        // 'bundle0', 'bundle+', 'bundle-'
        if (!state.isEOF() && state.isOneOf('0', '+', '-') &&
                state.mem.equals("bundle")) {
            memChar();
            return keywordOrIdent(state.mem);
        }

        RhoToken result = keywordOrIdent(state.mem);

        // check: identifier like keyword (typo)
        if (result.type.group == TokenGroup.Identifier) {
            Optional<String> correct = MisspelledKeywords.tryCorrectMisspelled(state.mem);
            correct.ifPresent(c -> state.lexWarn("identifier.like-existing-keyword", c));
        }

        // check: identifier like absent keyword (language misunderstanding)
        if (result.type.group == TokenGroup.Identifier) {
            if (AbsentKeywords.contains(state.mem)) {
                state.lexNote("identifier.like-absent-keyword");
            }
        }

        return result;
    }

    private RhoToken processNumber() {
        if (!state.isEOF() && state.ch() == '0') {
            memChar();
            if (!state.isEOF() && (state.ch() == 'x' || state.ch() == 'X')) {
                memChar();
                return readNumberHex();
            } else {
                return readNumberDecimal();
            }
        } else { // '1' ... '9'
            memChar();
            return readNumberDecimal();
        }
    }

    /**
     * Read a decimal number (64-bit integer without 'l'/'L' at the end).
     */
    private RhoToken readNumberDecimal() {

        while (!state.isEOF() && state.isDigit()) {
            memChar();
        }

        if (!state.isEOF() && state.ch() == '.') {
            memChar();
            return readFractionAndSuffix();
        } else if (!state.isEOF() && state.isOneOf('e', 'E')) {
            return readFractionAndSuffix();
        } else if (!state.isEOF() && state.isOneOf('l', 'L')) {
            memChar();
            return state.lexError("literal.absent.int-L-suffix");
        } else if (!state.isEOF() && state.isOneOf('f', 'F', 'd', 'D')) {
            memChar();
            return state.lexError("literal.absent.floating-point");
        } else {
            try {
                Long.parseLong(state.mem);
                return LITERAL_INT.T(state.mem);
            } catch (NumberFormatException e) {
                // Workaround for Long.MIN_VALUE
                if ("9223372036854775808".equals(state.mem)
                        && !returnedTokens.isEmpty()
                        && returnedTokens.getLast() == MINUS.T) {
                    return LITERAL_INT.T(state.mem);
                } else {
                    return state.lexError("literal.int-too-big");
                }
            }
        }
    }

    /**
     * Read fractional part and 'd'/'D' or 'f'/'F' suffix of floating point number.
     */
    private RhoToken readFractionAndSuffix() {
        while (!state.isEOF() && state.isDigit()) {
            memChar();
        }

        if (!state.isEOF() && state.isOneOf('e', 'E')) {
            memChar();
        }

        if (!state.isEOF() && state.isOneOf('-', '+')) {
            memChar();
        }

        while (!state.isEOF() && state.isDigit()) {
            memChar();
        }

        if (!state.isEOF() && state.isOneOf('f', 'F', 'd', 'D')) {
            memChar();
        }

        return state.lexError("literal.absent.floating-point");
    }

    /**
     * Read a hex number.
     */
    private RhoToken readNumberHex() {

        while (!state.isEOF() && (state.isDigit()
                || ('a' <= state.ch() && state.ch() <= 'f')
                || ('A' <= state.ch() && state.ch() <= 'F'))) {
            memChar();
        }

        return state.lexError("literal.absent.int-hex-format");
    }

    private RhoToken processLineEnd() {
        if (!state.isEOF() && state.ch() == '\r') {
            skipChar();
            if (!state.isEOF() && state.ch() == '\n') {
                skipChar();
            }
        } else {
            skipChar();
        }
        return readToken();
    }

    private RhoToken processDot() {
        memChar();
        if (!state.isEOF() && state.ch() == '.') {
            memChar();
            if (!state.isEOF() && state.ch() == '.') { // '...'
                skipChar();
                return ELLIPSIS.T;
            } else {                                   // '..'
                return state.lexError("operator.absent.dot-dot");
            }
        } else if (!state.isEOF() && state.isDigit()) {
            return readFractionAndSuffix();
        } else {
            return DOT.T;
        }
    }

    private RhoToken processColon() {
        memChar();
        if (!state.isEOF() && state.ch() == ':') { // '::'
            memChar();
            return state.lexError("operator.absent", "::");
        } else { //                 ':'
            return COLON.token();
        }
    }

    private RhoToken processEq() {
        memChar();
        if (!state.isEOF() && state.ch() == '=') {
            memChar();
            if (!state.isEOF() && state.ch() == '=') { // '==='
                memChar();
                return state.lexError("operator.absent.eq");
            } else {                                   // '=='
                return EQ_EQ.token();
            }
        } else if (!state.isEOF() && state.ch() == '>') { // '=>'
            skipChar();
            return ARROW.token();
        } else {                        // '='
            return EQ.token();
        }
    }

    private RhoToken processUnder() {
        memChar();
        if (!state.isEOF() && (state.isLetter() || state.isDigit() || state.ch() == '_')) {
            while (!state.isEOF() && (state.isLetter() || state.isDigit() || state.ch() == '_')) {
                memChar();
            }
            return keywordOrIdent(state.mem);
        } else {
            return WILDCARD.token();
        }
    }

    // ============================== Operators (used): +, -, *, /, >, <, |, !, ~, @
    private RhoToken processPlus() {
        memChar();
        if (!state.isEOF() && state.ch() == '+') {        // '++'
            skipChar();
            return PLUS_PLUS.token();
        } else if (!state.isEOF() && state.ch() == '=') { // '+='
            memChar();
            return state.lexError("operator.absent.compound-assignment");
        } else {                        // '+'
            return PLUS.token();
        }
    }

    private RhoToken processMinus() {
        memChar();
        if (!state.isEOF() && state.ch() == '-') {        // '--'
            skipChar();
            return MINUS_MINUS.token();
        } else if (!state.isEOF() && state.ch() == '=') { // '-='
            memChar();
            return state.lexError("operator.absent.compound-assignment");
        } else if (!state.isEOF() && state.ch() == '>') { // '->'
            memChar();
            return state.lexError("operator.absent.arrow");
        } else {                        // '-'
            return MINUS.token();
        }
    }

    private RhoToken processStar() {
        memChar();
        if (!state.isEOF() && state.ch() == '*') {        // '**'
            memChar();
            return state.lexError("operator.absent.pow");
        } else if (!state.isEOF() && state.ch() == '=') { // '*='
            memChar();
            return state.lexError("operator.absent.compound-assignment");
        } else {                        // '-'
            return STAR.token();
        }
    }

    private RhoToken processDiv() {
        memChar();
        if (!state.isEOF() && state.ch() == '\\') {       // '/\'
            skipChar();
            return CONJUNCTION.token();
        } else if (!state.isEOF() && state.ch() == '/') { // '//'
            skipChar();
            return processCommentLine();
        } else if (!state.isEOF() && state.ch() == '*') { // '/*'
            memChar();
            return processCommentBlock();
        } else {                                          // '/'
            return DIV.token();
        }
    }

    private RhoToken processCommentLine() {
        while (!state.isEOF() && !state.isCRLF()) {
            skipChar();
        }
        if (!state.isEOF()) {
            skipChar();
        }
        return readToken();
    }

    private RhoToken processCommentBlock() {
        while (!state.isEOF() && !state.isCRLF()) {
            if (state.ch() == '*') {     // '*'
                memChar();
                if (!state.isEOF() && state.ch() == '/') { // '*/'
                    skipChar();
                    return readToken();
                }
            } else {
                memChar();
            }
        }

        return state.lexError("comment.unclosed");
    }

    private RhoToken processBackSlash() {
        memChar();
        if (!state.isEOF() && state.ch() == '/') { // '\/'
            skipChar();
            return DISJUNCTION.token();
        } else { //                 '\'
            return state.lexError("operator.absent.back-slash");
        }
    }

    private RhoToken processLt() {
        memChar();
        if (!state.isEOF() && state.ch() == '<') {        // '<<'
            memChar();
            return state.lexError("operator.absent.arithmetic");
        } else if (!state.isEOF() && state.ch() == '~') { // '<~'
            memChar();
            return state.lexError("operator.absent.arrow");
        } else if (!state.isEOF() && state.ch() == '=') { // '<='
            skipChar();
            return BACK_ARROW.token();
        } else { //                 '<'
            return LT.token();
        }
    }

    private RhoToken processGt() {
        memChar();
        if (!state.isEOF() && state.ch() == '>') {
            memChar();
            if (!state.isEOF() && state.ch() == '>') {   // '>>>'
                memChar();
                return state.lexError("operator.absent.arithmetic");
            } else {                                     // '>>'
                return state.lexError("operator.absent.arithmetic");
            }
        } else if (!state.isEOF() && state.ch() == '=') { // '>='
            skipChar();
            return GT_EQ.token();
        } else {                                          // '>'
            return GT.token();
        }
    }

    private RhoToken processBar() {
        memChar();
        if (!state.isEOF() && state.ch() == '|') { // '||'
            memChar();
            return state.lexError("operator.absent.logic");
        } else { //                 '|'
            return PAR.token();
        }
    }

    private RhoToken processBang() {
        skipChar();
        if (!state.isEOF() && state.ch() == '!') { // '!!'
            skipChar();
            return SEND_MULTIPLE.token();
        } else if (!state.isEOF() && state.ch() == '=') { // '!='
            skipChar();
            return NOT_EQ.token();
        } else { //                 '!'
            return SEND_SINGLE.token();
        }
    }

    private RhoToken processTilde() {
        memChar();
        if (!state.isEOF() && state.ch() == '>') { // '~>'
            memChar();
            return state.lexError("operator.absent.arrow");
        } else { //                 '~'
            return TILDE.token();
        }
    }

    private RhoToken processPercent() {
        memChar();
        if (!state.isEOF() && state.ch() == '%') { // '%%'
            skipChar();
            return PERCENT_PERCENT.token();
        } else { //                 '%'
            return state.lexError("operator.absent.arithmetic");
        }
    }

    // ============================== Operators (unused): &, ^, ?, #, $
    private RhoToken processAmp() {
        memChar();
        if (!state.isEOF() && state.ch() == '&') { // '&&'
            memChar();
            return state.lexError("operator.absent.logic");
        } else { //                 '&'
            return state.lexError("operator.absent.logic");
        }
    }

    private RhoToken processHat() {
        memChar();
        if (!state.isEOF() && state.ch() == '^') { // '^^'
            memChar();
            return state.lexError("operator.absent.pow");
        } else {                                   // '^'
            return state.lexError("operator.absent.pow");
        }
    }

    private RhoToken processQuestion() {
        memChar();
        if (!state.isEOF() && state.ch() == '?') { // '??'
            memChar();
            return state.lexError("operator.absent");
        } else {                                   // '?'
            return state.lexError("operator.absent");
        }
    }

    private RhoToken processHash() {
        memChar();
        if (!state.isEOF() && state.ch() == '#') { // '##'
            memChar();
            return state.lexError("operator.absent");
        } else {                                   // '#'
            return state.lexError("operator.absent");
        }
    }

    private RhoToken processDollar() {
        memChar();
        if (!state.isEOF() && state.ch() == '$') { // '$$'
            memChar();
            return state.lexError("operator.absent");
        } else {                                   // '$'
            return state.lexError("operator.absent");
        }
    }

    // ============================== String-like
    // todo: warn UTF-unsupported
    private RhoToken processString() {
        memChar();
        while (!state.isEOF() && state.ch() != '"' && !state.isCRLF()) {
            memChar();
        }
        if (!state.isEOF() && state.ch() == '"') {
            memChar();
            return LITERAL_STRING.token(state.mem.substring(1, state.mem.length() - 1));
        } else {
            return state.lexError("literal.string.unclosed");
        }
    }

    private RhoToken processUri() {
        memChar();
        while (!state.isEOF() && state.ch() != '`' && !state.isCRLF()) {
            memChar();
        }
        if (!state.isEOF() && state.ch() == '`') {
            memChar();
            return LITERAL_URI.T(state.mem.substring(1, state.mem.length() - 1));
        } else {
            return state.lexError("literal.uri.unclosed");
        }
    }

    private RhoToken processQuote() {
        memChar();
        while (!state.isEOF() && state.ch() != '\'' && !state.isCRLF()) {
            memChar();
        }
        if (!state.isEOF() && state.ch() == '\'') {
            memChar();
            return state.lexError("literal.absent.single-quote");
        } else {
//            pushbackMem();
            return state.lexError("operator.absent.single-quote");
        }
    }

    // ============================== Unicode + internals
    private RhoToken processDefault() {
        char ch0 = state.ch();
        String codepoint0 = String.valueOf((int) ch0);
        String hex0 = format("'\\u%04X'", (int) ch0);
        memChar();

        if (ch0 <= '\u0080') {
            // ========== Illegal ASCII
            return state.lexError("codepoint.illegal.ascii", hex0, codepoint0);
        } else if (isDefined(ch0)) {
            // ========== Correct but illegal 1-char Unicode
            return state.lexError("codepoint.illegal.unicode-1-char", "" + ch0, hex0, codepoint0);
        } else if (isHighSurrogate(ch0)) {
            // todo: add state.isEOF() checking
            char ch1 = state.ch();
            String hex01 = format("'\\u%04X\\u%04X'", (int) ch0, (int) ch1);
            skipChar();
            if (isLowSurrogate(ch1)) {
                // ========== Correct but illegal 2-char Unicode
                char[] arr = {ch0, ch1};
                return state.lexError("codepoint.illegal.unicode-2-chars",
                        new String(arr), hex01, "" + codePointAt(arr, 0));
            } else {
                // ========== Incorrect 2-chars unicode combination
                return state.lexError("codepoint.incorrect-encoding.2-chars", hex01);
            }
        } else {
            // ========== Incorrect 1-char unicode
            return state.lexError("codepoint.incorrect-encoding.1-char", hex0);
        }
    }

    private void skipChar() {
        state = state.skipChar();
    }

    private void memChar() {
        state = state.memChar();
    }
}













