/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package net.golovach.rholp;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Character.isHighSurrogate;
import static java.lang.Character.isLowSurrogate;
import static java.lang.Integer.toHexString;
import static java.util.ResourceBundle.getBundle;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.log.RhoLayoutCharacters.*;

/**
 * The lexical analyzer maps an input stream consisting of
 * ASCII characters and Unicode escapes into a token sequence.
 * <p/>
 * LineTerminator
 * + ( WhiteSpace + Comment
 * + (Identifier + Keyword + Literal + Separator + Operator)
 * <p/>
 * Whitespace, LineBreak
 * <p/>
 * Comment
 * - line
 * - block
 * <p/>
 * Literals
 * - int
 * - long
 * - float
 * - double
 * - String
 * - char
 * <p/>
 * Operator
 * <p/>
 * <p/>
 * Identifier
 * - keyword
 * - identifier
 */
public class RhoLexer {

    public enum CommentStyle {LINE, BLOCK}

    public static final String ERROR_PREFIX = "lexer.err.";
    public static final String WARN_PREFIX = "lexer.warn.";
    public static final String NOTE_PREFIX = "lexer.note.";

    private static boolean debug = false;

    // Output variables; set by nextToken()

    private RhoTokenType token = RhoTokenType.STAR;    // The token, set by nextToken() //todo
    private int pos;        // The token's position, 0-based offset from beginning of text
    private int endPos;     // Character position just after the last character of the token
    private int prevEndPos; // The last character position of the previous token
    private int errPos = -1; // The position where a lexical error occurred //todo
    private String name;      // The name of an identifier or token //todo: rename

    private char[] sbuf = new char[128]; // A character buffer for literals
    private int sp;                      // index of next free position in buffer for literals

    private char[] buf; // The input buffer
    private int bufLen;
    private int bp;     // Index of next character to be read
    private int eofPos; // Index of one past last character in buffer.

    private char ch; // The current character.

    // diagnostic/error generation
    private final LineMap lineMap; // a map for translating between line numbers and positions in the input.
    private final ResourceBundle msgBundle;

    /**
     * Diagnostic listener, if provided through programmatic
     * interface to javac (JSR 199).
     */
    public final DiagnosticListener listener;

    public RhoLexer(String content) {
        this(content.toCharArray(), content.length(), null);
    }

    public RhoLexer(String content, DiagnosticListener listener) {
        this(content.toCharArray(), content.length(), listener);
    }

    /**
     * Create a scanner from the input array.  This method might
     * modify the array.  To avoid copying the input array, ensure
     * that {@code inputLength < input.length} or
     * {@code input[input.length -1]} is a white space character.
     *
     * @param input       the input, might be modified
     * @param inputLength the size of the input.
     *                    Must be positive and less than or equal to input.length.
     */
    private RhoLexer(char[] input, int inputLength, DiagnosticListener listener) {
        this.eofPos = inputLength;
        if (inputLength == input.length) {
            if (input.length > 0 && Character.isWhitespace(input[input.length - 1])) {
                inputLength--;
            } else {
                char[] newInput = new char[inputLength + 1];
                System.arraycopy(input, 0, newInput, 0, input.length);
                input = newInput;
            }
        }
        this.buf = input;
        this.bufLen = inputLength;
        this.buf[this.bufLen] = EOI;
        this.bp = -1;
        scanChar();

        this.listener = listener;

        this.lineMap = new LineMapImpl(buf, bufLen); //todo: no tab expansion?
        this.msgBundle = getBundle("lexer");
    }

    public RhoTokenType scanToken() {
        nextToken();
        return token();
    }

    public List<RhoTokenType> scanAll() {
        List<RhoTokenType> result = new ArrayList<>();

        do {
            scanToken();
            result.add(token());
        } while (token() != EOF);

        return result;
    }

    /**
     * Read token.
     */
    public void nextToken() {

        prevEndPos = endPos;
        sp = 0;

        while (true) {
            pos = bp;
            switch (ch) {
                // ==================== Whitespaces
                case ' ':
                case '\t':
                case FF:
                    do {
                        scanChar();
                    } while (ch == ' ' || ch == '\t' || ch == FF);
                    endPos = bp;
                    processWhiteSpace();
                    break;
                // ==================== Line terminations
                case LF:
                    scanChar();
                    endPos = bp;
                    processLineTerminator();
                    break;
                case CR:
                    scanChar();
                    if (ch == LF) {
                        scanChar();
                    }
                    endPos = bp;
                    processLineTerminator();
                    break;
                // ==================== Identifiers + Keywords
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case '_': // todo: or wildcard!
                    scanIdentifierOrKeyword();
                    return;
                // ==================== Number (hex | octal) //todo: octal?
                case '0':
                    scanLitChar();
                    if (ch == 'x' || ch == 'X') {
                        scanLitChar();
                        scanNumberHex();
                    } else {
                        scanNumberDecimal();
                    }
                    return;
                // ==================== Number (decimal)
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    scanLitChar();
                    scanNumberDecimal();
                    return;
                // ==================== DOT | ELLIPSIS | Number (float/double)
                case '.':
                    scanLitChar();
                    if ('0' <= ch && ch <= '9') {
                        scanFractionAndSuffix();
                    } else if (ch == '.') {
                        scanLitChar();
                        if (ch == '.') {
                            scanLitChar();
                            token = ELLIPSIS;
                        } else {
                            lexError("nonexistent.two_dots");
                        }
                    } else {
                        token = DOT;
                    }
                    return;
                // ==================== Separators
                case ',':
                    scanChar();
                    token = COMMA;
                    return;
                case ';':
                    scanChar();
                    token = SEMI;
                    return;
                case ':':
                    scanChar();
                    if (ch == ':') {
                        // '::'
                        scanLitChar();
                        lexError("non-existent.operator", "::");
                        return;
                    } else {
                        // ':'
                        token = COLON;
                        return;
                    }
                case '(':
                    scanChar();
                    token = LPAREN;
                    return;
                case ')':
                    scanChar();
                    token = RPAREN;
                    return;
                case '[':
                    scanChar();
                    token = LBRACKET;
                    return;
                case ']':
                    scanChar();
                    token = RBRACKET;
                    return;
                case '{':
                    scanChar();
                    token = LBRACE;
                    return;
                case '}':
                    scanChar();
                    token = RBRACE;
                    return;
                case '=':
                    scanChar();
                    if (ch == '=') {
                        // '=='
                        scanChar();
                        token = EQ_EQ;
                        return;
                    } else if (ch == '>') {
                        // '+='
                        scanChar();
                        token = ARROW;
                        return;
                    } else {
                        // '='
                        token = EQ;
                        return;
                    }
                // ========== Operators
                //todo: ~> <~
                case '+':
                    scanLitChar();
                    if (ch == '+') {
                        // '++'
                        scanLitChar();
                        token = PLUS_PLUS;
                        return;
                    } else if (ch == '=') {
                        // '+='
                        scanLitChar();
                        lexError("non-existent.operator.compound-assignment");
                        return;
                    } else {
                        // '+'
                        token = PLUS;
                        return;
                    }
                case '-':
                    scanLitChar();
                    if (ch == '-') {
                        // '--'
                        scanLitChar();
                        token = MINUS_MINUS;
                        return;
                    } else if (ch == '=') {
                        // '-='
                        scanLitChar();
                        lexError("non-existent.operator.compound-assignment");
                        return;
                    } else if (ch == '>') {
                        // '->'
                        scanLitChar();
                        lexError("non-existent.operator.arrow", "->");
                        return;
                    } else {
                        // '-'
                        token = MINUS;
                        return;
                    }
                case '*':
                    scanLitChar();
                    if (ch == '*') {
                        // '**'
                        scanLitChar();
                        lexError("non-existent.operator", "**"); //todo: use stdlib for pow
                        return;
                    } else if (ch == '=') {
                        // '*='
                        scanLitChar();
                        lexError("non-existent.operator.compound-assignment");
                        return;
                    } else {
                        // '*'
                        token = STAR;
                        return;
                    }
                case '/':
                    scanChar();

                    if (ch == '\\') {
                        // ==================== CONJUNCTION (/\)
                        scanChar();
                        token = CONJUNCTION;
                        return;
                    } else if (ch == '/') {
                        // ==================== Comment (line)
                        do {
                            scanChar();
                        } while (ch != CR && ch != LF && bp < bufLen);
                        if (bp < bufLen) {
                            endPos = bp;
                            processComment(CommentStyle.LINE);
                        }
                        break;
                    } else if (ch == '*') {
                        // ==================== Comment (block)
                        scanChar();
                        CommentStyle style = CommentStyle.BLOCK;
                        while (bp < bufLen) {
                            if (ch == '*') {
                                scanChar();
                                if (ch == '/') break;
                            } else {
                                scanChar();
                            }
                        }
                        if (ch == '/') {
                            scanChar();
                            endPos = bp;
                            processComment(style);
                            break;
                        } else {
                            lexError("unclosed.comment");
                            return;
                        }
                    } else {
                        // ==================== DIV
                        token = DIV;
                    }
                    return;

                case '@':
                    scanChar();
                    token = QUOTE;
                    return;

                // ========== nonexistent characters todo rename comment
                case '?':
                    scanLitChar();
                    lexError("nonexistent.question");
                    return;
                case '$':
                    scanLitChar();
                    lexError("nonexistent.dollar");
                    return;
                case '#':
                    scanChar();
                    lexError("non-existent.operator", "#");
                    return;
                // ========== only parts todo rename comment
                case '%':
                    scanChar();
                    if (ch == '%') {
                        scanChar();
                        token = PERCENT_PERCENT;
                        return;
                    } else {
                        lexError("nonexistent.one_percent");
                    }
                    return;
                case '\\':  // todo:???
                    scanChar();
                    if (ch == '/') {
                        scanChar();
                        token = DISJUNCTION;
                        return;
                    } else {
                        lexError("back_slash.only_in_conjunction_disjunction");
                    }
                    return;

                // ========== LITERAL_STRING ("...")
                case '\"':
                    scanChar();
                    while (ch != '"' && ch != CR && ch != LF && bp < bufLen) {
                        scanLitChar();
                    }
                    if (ch == '\"') {
                        token = LITERAL_STRING;
                        scanChar();
                    } else {
                        sp = 1;
                        sbuf[0] = '"';
                        lexError("unclosed.string-literal");
                    }
                    return;
                // ========== LITERAL_URI (`...`)
                case '`':
                    scanChar();
                    while (ch != '`' && ch != CR && ch != LF && bp < bufLen) {
                        scanLitChar();
                    }
                    if (ch == '`') {
                        token = LITERAL_URI;
                        scanChar();
                    } else {
                        sp = 1;
                        sbuf[0] = '`';
                        lexError("unclosed.uri-literal");
                    }
                    return;

                // ========== single quote literals ('...')
                case '\'':
                    scanChar();
                    while (ch != '\'' && ch != CR && ch != LF && bp < bufLen) {
                        scanLitChar();
                    }
                    if (ch == '\'') {
                        scanLitChar();
                        lexError("non-existent.literal.single-quote");
                    } else {
                        sp = 1;
                        sbuf[0] = '\'';
                        // todo:
                        lexError("nonexistent.unclosed.singlequote.lit");
                    }
                    return;
                default:
                    // ========== Unicode
                    if (ch == EOI) {
                        token = EOF;
                        listener.eof();
                        return;
                    }

                    String unicodeStr = "" + ch;
                    String hex = toHexString(ch).toUpperCase();
                    while (hex.length() < 4) {
                        hex = "0" + hex;
                    }
                    hex = "'\\u" + hex + "'";
                    String codepoint = String.valueOf((int) ch);
                    if (0 <= ch && ch <= '\u0080') {
                        // ========== Illegal ASCII
                        lexError("illegal.char", unicodeStr, codepoint, hex);
                        scanChar();
                    } else {
                        if ('\u0080' < ch) {
                            lexError("non-existent.unicode.identifiers", unicodeStr, codepoint, hex);
                            scanChar();
                        } else if (isHighSurrogate(ch)) {
                            scanUnicode();
                        } else {
                            lexError("illegal.char", codepoint);
                            scanChar();
                        }
                    }
            }
        }
    }

    private void scanUnicode() {
        if (isLowSurrogate(ch)) {
            char high = ch;
            scanLitChar();
            if (isHighSurrogate(ch)) {
                char low = ch;
                scanLitChar();
                lexError("non-existent.unicode.identifiers", new String(new char[]{high, low}));
            } else {
                throw new Error();
            }
        } else {
            throw new Error();
        }
    }

    /**
     * Read next character.
     */
    private void scanChar() {
        ch = buf[++bp];
    }

    /**
     * Append a character to sbuf.
     */
    private void putChar(char ch) {
        if (sp == sbuf.length) {
            char[] newsbuf = new char[sbuf.length * 2];
            System.arraycopy(sbuf, 0, newsbuf, 0, sbuf.length);
            sbuf = newsbuf;
        }
        sbuf[sp++] = ch;
    }

    /**
     * Read next character in character or string or uri literal and copy into sbuf.
     */
    private void scanLitChar() {
        if (bp != bufLen) {
            putChar(ch);
            scanChar();
        } else {
            // todo: ?
        }
    }

    /**
     * Read a decimal number (64-bit integer without 'l'/'L' at the end).
     */
    private void scanNumberDecimal() {

        while ('0' <= ch && ch <= '9') {
            scanLitChar();
        }

        if (ch == '.') {
            scanLitChar();
            scanFractionAndSuffix();
        } else if (ch == 'e' || ch == 'E') {
            scanFractionAndSuffix();
        } else if (ch == 'l' || ch == 'L') {
            scanLitChar();
            lexError("non-existent.literal.int-with-L-suffix");
        } else if (ch == 'f' || ch == 'F' || ch == 'd' || ch == 'D') {
            scanLitChar();
            lexError("non-existent.literal.floating-point");
        } else {
            boolean ok = true;
            try {
                Long.parseLong(stringVal());
            } catch (NumberFormatException e) {
                // Workaround for Long.MIN_VALUE
                if (!"9223372036854775808".equals(stringVal()) || token != MINUS) {
                    ok = false;
                }
            }
            if (ok) {
                token = LITERAL_INT;
            } else {
                lexError("int-literal.too-big");
            }
        }
    }

    /**
     * Read fractional part and 'd'/'D' or 'f'/'F' suffix of floating point number.
     */
    private void scanFractionAndSuffix() {
        while ('0' <= ch && ch <= '9') {
            scanLitChar();
        }
        int sp1 = sp;
        if (ch == 'e' || ch == 'E') {
            scanLitChar();
            if (ch == '+' || ch == '-') {
                scanLitChar();
            }
            if ('0' <= ch && ch <= '9') {
                do {
                    scanLitChar();
                } while ('0' <= ch && ch <= '9');
            } else {
                lexError("malformed.fp.lit"); //todo: remove
                sp = sp1;
            }
        }
        if (ch == 'f' || ch == 'F' || ch == 'd' || ch == 'D') {
            scanLitChar();
        }
        lexError("non-existent.literal.floating-point");
    }

    /**
     * Read a hex number.
     */
    private void scanNumberHex() {

        while (('0' <= ch && ch <= '9')
                || ('a' <= ch && ch <= 'f')
                || ('A' <= ch && ch <= 'F')) {
            scanLitChar();
        }

        lexError("non-existent.literal.int-in-hex-format");
    }

    /**
     * Read an identifier.
     */
    private void scanIdentifierOrKeyword() {
        scanLitChar();
        do {
            switch (ch) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                case '_':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    break;
                case '\u0000': //todo: wtf?
                case '\u0001':
                case '\u0002':
                case '\u0003':
                case '\u0004':
                case '\u0005':
                case '\u0006':
                case '\u0007':
                case '\u0008':
                case '\u000E':
                case '\u000F':
                case '\u0010':
                case '\u0011':
                case '\u0012':
                case '\u0013':
                case '\u0014':
                case '\u0015':
                case '\u0016':
                case '\u0017':
                case '\u0018':
                case '\u0019':
                case '\u001B':
                case '\u007F':
                    scanChar();
                    continue;
                case '\u001A': // EOI is also a legal identifier part  // todo - remove
                    if (bp >= bufLen) {
                        name = new String(sbuf, 0, sp);
                        token = RhoTokenType.select(name);
//                        checkTypoInKeyword(); //todo
                        return;
                    }
                    scanChar();
                    continue;
                default:
                    if (ch < '\u0080') {
                        name = new String(sbuf, 0, sp);
                        token = RhoTokenType.select(name); //todo
//                        checkTypoInKeyword(); //todo
                        return;
                    } else {
                        throw new Error();   // todo: ?
                    }
            }
            scanLitChar();
        } while (true);
    }

    /**
     * Report an error at the current token position using the provided
     * arguments.
     */
    private void lexError(String key, Object... args) {
        this.token = ERROR;
        this.errPos = pos;

        String prefixedKey = ERROR_PREFIX + key;

        if (this.listener != null) {

            int lineNumber = lineMap.getLineNumber(pos);
            int columnNumber = lineMap.getColumnNumber(pos);
            String sourceCodeLine;

            int startPos = lineMap.getStartPosition(lineNumber);
            if (lineNumber < lineMap.getLineCount()) {
                int endPos = lineMap.getStartPosition(lineNumber + 1) - 1;
                sourceCodeLine = new String(buf, startPos, endPos - startPos + 1);
            } else {
                sourceCodeLine = new String(buf, startPos, bufLen);
            }

            this.listener.report(new Diagnostic(
                    Diagnostic.Kind.ERROR,
                    sourceCodeLine,
                    pos,
                    pos - startPos,
                    sp,
                    lineNumber,
                    columnNumber,
                    prefixedKey,
                    MessageFormat.format(msgBundle.getString(prefixedKey), args)
            ));
        }
    }

    /**
     * Report an error at the current token position using the provided
     * arguments.
     */
    private void lexNote(String key, Object... args) {
        String prefixedKey = NOTE_PREFIX + key;

        if (this.listener != null) {

            int lineNumber = lineMap.getLineNumber(pos);
            int columnNumber = lineMap.getColumnNumber(pos);
            String sourceCodeLine;

            int startPos = lineMap.getStartPosition(lineNumber);
            if (lineNumber < lineMap.getLineCount()) {
                int endPos = lineMap.getStartPosition(lineNumber + 1) - 1;
                sourceCodeLine = new String(buf, startPos, endPos);
            } else {
                sourceCodeLine = new String(buf, startPos, bufLen);
            }

            this.listener.report(new Diagnostic(
                    Diagnostic.Kind.ERROR,
                    sourceCodeLine,
                    pos,
                    pos - startPos,
                    sp,
                    lineNumber,
                    columnNumber,
                    prefixedKey,
                    MessageFormat.format(msgBundle.getString(prefixedKey), args)
            ));
        }
    }

    // ************************************************************************
    // ************************************************************************
    // ************************************************************************
    // ************************************************************************
    // ************************************************************************

    /**
     * The value of a literal token, recorded as a string.
     * For integers, leading 0x and 'l' suffixes are suppressed.
     */
    public String stringVal() {
        // todo: throw if empty
        return new String(sbuf, 0, sp);
    }

    /**
     * Return the current token, set by nextToken().
     */
    public RhoTokenType token() {
        return token;
    }

    /**
     * Sets the current token.
     */
    public void token(RhoTokenType token) {
        this.token = token;
    }

    /**
     * Return the current token's position: a 0-based
     * offset from beginning of the raw input stream
     * (before unicode translation)
     */
    public int pos() {
        return pos;
    }

    /**
     * Return the last character position of the current token.
     */
    public int endPos() {
        return endPos;
    }

    /**
     * Return the last character position of the previous token.
     */
    public int prevEndPos() {
        return prevEndPos;
    }

    /**
     * Return the position where a lexical error occurred;
     */
    public int errPos() {
        return errPos;
    }

    /**
     * Set the position where a lexical error occurred;
     */
    public void errPos(int pos) {
        errPos = pos;
    }

    /**
     * Return the name of an identifier or token for the current token.
     */
    public String name() {
        return name;
    }

    /**
     * Returns a copy of the input buffer, up to its inputLength.
     * Unicode escape sequences are not translated.
     */
    public char[] getRawCharacters() {
        char[] chars = new char[bufLen];
        System.arraycopy(buf, 0, chars, 0, bufLen);
        return chars;
    }

    /**
     * Returns a copy of a character array subset of the input buffer.
     * The returned array begins at the <code>beginIndex</code> and
     * extends to the character at index <code>endIndex - 1</code>.
     * Thus the length of the substring is <code>endIndex-beginIndex</code>.
     * This behavior is like
     * <code>String.substring(beginIndex, endIndex)</code>.
     * Unicode escape sequences are not translated.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @throws IndexOutOfBoundsException if either offset is outside of the
     *                                   array bounds
     */
    public char[] getRawCharacters(int beginIndex, int endIndex) {
        int length = endIndex - beginIndex;
        char[] chars = new char[length];
        System.arraycopy(buf, beginIndex, chars, 0, length);
        return chars;
    }

    /**
     * Called when a complete comment has been scanned. pos and endPos
     * will mark the comment boundary.
     */
    protected void processComment(CommentStyle style) {
        if (debug)
            System.out.println("    processComment(" + pos
                    + "," + endPos + "," + style + ")=|"
                    + new String(getRawCharacters(pos, endPos))
                    + "|");
    }

    /**
     * Called when a complete whitespace run has been scanned. pos and endPos
     * will mark the whitespace boundary.
     */
    protected void processWhiteSpace() {
        if (debug)
            System.out.println("    processWhitespace(" + pos
                    + "," + endPos + ")=|" +
                    new String(getRawCharacters(pos, endPos))
                    + "|");
    }

    /**
     * Called when a line terminator has been processed.
     */
    protected void processLineTerminator() {
        if (debug)
            System.out.println("    processTerminator(" + pos
                    + "," + endPos + ")=|" +
                    new String(getRawCharacters(pos, endPos))
                    + "|");
    }
}

class T {
    public static void main(String[] args) {
        char[] chars = Character.toChars(100_000);
        System.out.println(isHighSurrogate(chars[0]));
        System.out.println(isLowSurrogate(chars[1]));
    }
}