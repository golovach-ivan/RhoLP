package net.golovach.rholp.lexer;

import net.golovach.rholp.log.LineMap;
import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.MessageDB;

import static java.lang.String.format;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static net.golovach.rholp.log.Diagnostic.error;
import static net.golovach.rholp.log.Diagnostic.note;
import static net.golovach.rholp.log.Diagnostic.warn;

class LexerState {
    private final int offset;
    private final String content;
    private final String mem;
    //
    private final LineMap lineMap;
    private final DiagnosticListener listener;
    private final MessageDB messageDb;

    LexerState(String content, DiagnosticListener listener, LineMap lineMap, MessageDB messageDb) {
        this(0, content, "", listener, lineMap, messageDb);
    }

    LexerState(int offset,
               String content,
               String mem,
               DiagnosticListener listener,
               LineMap lineMap,
               MessageDB messageDb) {
        this.offset = offset;
        this.content = content;
        this.mem = mem;
        //
        this.listener = listener;
        this.lineMap = lineMap;
        this.messageDb = messageDb;
    }

    String mem() {
        return mem;
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

    boolean isOneOf(char ... chars) {
        for (char c : chars) {
            if (ch() == c) {
                return true;
            }
        }
        return false;
    }

    LexerState cleanMem() {
        return new LexerState(offset, content, "", listener, lineMap, messageDb);
    }

    LexerState nextClean() {
        return new LexerState(offset + 1, content.substring(1), "", listener, lineMap, messageDb);
    }

    LexerState nextMem() {
        return new LexerState(offset + 1, content.substring(1), mem + content.charAt(0), listener, lineMap, messageDb);
    }

    LexerState pushToMem(char c) {
        return new LexerState(offset, content, mem + c, listener, lineMap, messageDb);
    }

    public int offsetInFile() {
        return offset;
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

    public int offsetInFilePrev(int back) { //todo: remove?
        return offset - back;
    }

    public int rowPrev() { //todo: remove?
        return lineMap.offsetToRow(offset - 1);
    }

    public int colPrev(int back) { //todo: remove?
        return lineMap.offsetToCol(offset - back);
    }

    public String srcLinePrev(int back) { //todo: remove?
        return lineMap.offsetToSrcLine(offset - back);
    }

    RhoToken lexError(String key, String... args) {

        String[] args2 = new String[args.length + 1];
        args2[0] = mem;
        System.arraycopy(args, 0, args2, 1, args.length);

        this.listener.report(error(
                "lexer.err." + key,       // todo
                messageDb.msg("lexer.err." + key, args2),
                messageDb.msgTemplate("lexer.err." + key),
                args,
                //
                srcLinePrev(mem.length()),
                colPrev(mem.length()),
                mem.length(),
                //
                offsetInFilePrev(mem.length()),
                rowPrev()

        ));
        if (mem.length() == 1 && mem.charAt(0) < ' ') {
            return ERROR.token(format("\\u%04X", mem.charAt(0) + 0));
        } else {
            return ERROR.token(mem);
        }

    }

    RhoToken lexWarn(String key, String... args) {

        String[] args2 = new String[args.length + 1];
        args2[0] = mem;
        System.arraycopy(args, 0, args2, 1, args.length);

        this.listener.report(warn(
                "lexer.warn." + key,       // todo
                messageDb.msg("lexer.warn." + key, args2),
                messageDb.msgTemplate("lexer.warn." + key),
                args,
                //
                srcLinePrev(mem.length()),
                colPrev(mem.length()),
                mem.length(),
                //
                offsetInFilePrev(mem.length()),
                rowPrev()

        ));
        if (mem.length() == 1 && mem.charAt(0) < ' ') {
            return ERROR.token(format("\\u%04X", mem.charAt(0) + 0));
        } else {
            return ERROR.token(mem);
        }

    }

    RhoToken lexNote(String key, String... args) {

        String[] args2 = new String[args.length + 1];
        args2[0] = mem;
        System.arraycopy(args, 0, args2, 1, args.length);

        this.listener.report(note(
                "lexer.note." + key,       // todo
                messageDb.msg("lexer.note." + key, args2),
                messageDb.msgTemplate("lexer.note." + key),
                args,
                //
                srcLinePrev(mem.length()),
                colPrev(mem.length()),
                mem.length(),
                //
                offsetInFilePrev(mem.length()),
                rowPrev()

        ));
        if (mem.length() == 1 && mem.charAt(0) < ' ') {
            return ERROR.token(format("\\u%04X", mem.charAt(0) + 0));
        } else {
            return ERROR.token(mem);
        }

    }

    public LexerState revertMem() {
        return new LexerState(
                offset - mem().length(),
                mem + content,
                "",
                listener,
                lineMap,
                messageDb);
    }
}
