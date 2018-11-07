package net.golovach.rholp.lexer;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.RhoTokenType;
import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.LineMap;

import java.util.Properties;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.golovach.rholp.log.Diagnostic.Kind.*;

public class LexerState {
    public static final String NOTE_PREFIX = "lexer.note.";
    public static final String WARN_PREFIX = "lexer.warn.";
    public static final String ERROR_PREFIX = "lexer.err.";

    public final int offset;
    public final String content;
    public final String mem;
    //
    private final LineMap lineMap;
    private final DiagnosticListener listener;
    private final Properties messages;

    public LexerState(String content, DiagnosticListener listener, LineMap lineMap, Properties messages) {
        this(0, content, "", listener, lineMap, messages);
    }

    LexerState(int offset,
               String content,
               String mem,
               DiagnosticListener listener,
               LineMap lineMap,
               Properties messages) {
        this.offset = offset;
        this.content = content;
        this.mem = mem;
        //
        this.listener = listener;
        this.lineMap = lineMap;
        this.messages = messages;
    }

    public int row() {
        return lineMap.offsetToRow(offset);
    }

    public int col() {
        return lineMap.offsetToCol(offset);
    }

    public String srcLine() {
        return lineMap.offsetToSrcLine(offset);
    }

    char ch() {
        return content.charAt(0);
    }

    boolean isEOF() {
        return content.isEmpty();
    }

    boolean isCRLF() {
        return ch() == '\r' || ch() == '\n';
    }

    boolean isLetter() {
        return ('a' <= ch() && ch() <= 'z') || ('A' <= ch() && ch() <= 'Z');
    }

    boolean isDigit() {
        return ('0' <= ch() && ch() <= '9');
    }

    boolean isOneOf(char... chars) {
        for (char c : chars) {
            if (ch() == c) {
                return true;
            }
        }
        return false;
    }

    LexerState cleanMem() {
        return new LexerState(offset, content, "", listener, lineMap, messages);
    }

    LexerState skipChar() {
        return new LexerState(offset + 1, content.substring(1), mem, listener, lineMap, messages);
    }

    LexerState memChar() {
        return new LexerState(offset + 1, content.substring(1), mem + content.charAt(0), listener, lineMap, messages);
    }

    public RhoToken lexNote(String code, String... args) {
        return lexMsg(NOTE, NOTE_PREFIX + code, args);
    }

    public RhoToken lexWarn(String code, String... args) {
        return lexMsg(WARN, WARN_PREFIX + code, args);
    }

    public RhoToken lexError(String code, String... args) {
        return lexMsg(ERROR, ERROR_PREFIX + code, args);
    }

    private RhoToken lexMsg(Diagnostic.Kind kind, String code, String... args) {

        String[] args2 = new String[args.length + 1];
        args2[0] = mem;
        System.arraycopy(args, 0, args2, 1, args.length);

        this.listener.report(new Diagnostic(
                kind,
                code,
                format(messages.getProperty(code), (Object[]) args2),
                messages.getProperty(code),
                asList(args),
                //
                lineMap.offsetToSrcLine(offset - mem.length()),
                lineMap.offsetToCol(offset - mem.length()),
                mem.length(),
                //
                offset - mem.length(),
                lineMap.offsetToRow(offset - mem.length())));

        if (mem.length() == 1 && mem.charAt(0) < ' ') {
            return RhoTokenType.ERROR.token(format("\\u%04X", (int) mem.charAt(0)));
        } else {
            return RhoTokenType.ERROR.token(mem);
        }
    }
}
