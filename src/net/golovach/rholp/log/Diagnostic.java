package net.golovach.rholp.log;

import static net.golovach.rholp.log.Diagnostic.Kind.ERROR;
import static net.golovach.rholp.log.Diagnostic.Kind.NOTE;
import static net.golovach.rholp.log.Diagnostic.Kind.WARN;

/**
 * Class for diagnostics from lexer/parser. A diagnostic reports
 * a problem at a specific position in a source file.
 * <p/>
 * <p>A position is a zero-based character offset from the beginning of
 * a file.  Negative values are not valid positions.
 * <p/>
 * <p>Line and column numbers begin at 1.  Negative values and 0
 * are not valid line or column numbers.
 * <p/>
 * <p> Variation of {@link javax.tools.Diagnostic}.
 */
public class Diagnostic {
    // todo: implement scala API (case class) + javadoc: convert java->scala collections
    private final Kind kind;
    private final String code;
    private final String[] messages;
    private final String messageTemplate;
    private final String[] messageArgs;
    //
    private final String line;
    private final int colNum;
    private final int len;
    //
    private final int rowNum;
    private final int offset;


    public Diagnostic(Kind kind,
                      String code,
                      String[] messages,
                      String messageTemplate,
                      String[] messageArgs,
                      //
                      String line,
                      int colNum,
                      int len,
                      //
                      int offset,
                      int rowNum
    ) {
        this.kind = kind;
        this.code = code;
        this.messages = messages;
        this.messageTemplate = messageTemplate;
        this.messageArgs = messageArgs;
        //
        this.line = line;
        this.colNum = colNum;
        this.len = len;
        //
        this.offset = offset;
        this.rowNum = rowNum;

    }

    public static Diagnostic note(String code,
                                  String[] messages,
                                  String messageTemplate,
                                  String[] messageArgs,
                                  //
                                  String line,
                                  int colNum,
                                  int len,
                                  //
                                  int offset,
                                  int rowNum) {
        return new Diagnostic(
                NOTE,
                code,
                messages,
                messageTemplate,
                messageArgs,
                //
                line,
                colNum,
                len,
                //
                offset,
                rowNum);
    }

    public static Diagnostic warn(String code,
                                   String[] messages,
                                   String messageTemplate,
                                   String[] messageArgs,
                                   //
                                   String line,
                                   int colNum,
                                   int len,
                                   //
                                   int offset,
                                   int rowNum) {
        return new Diagnostic(
                WARN,
                code,
                messages,
                messageTemplate,
                messageArgs,
                //
                line,
                colNum,
                len,
                //
                offset,
                rowNum);
    }

    public static Diagnostic error(String code,
                                   String[] messages,
                                   String messageTemplate,
                                   String[] messageArgs,
                                   //
                                   String line,
                                   int colNum,
                                   int len,
                                   //
                                   int offset,
                                   int rowNum) {
        return new Diagnostic(
                ERROR,
                code,
                messages,
                messageTemplate,
                messageArgs,
                //
                line,
                colNum,
                len,
                //
                offset,
                rowNum);
    }

    /**
     * Kinds of diagnostics: ERROR, WARN, NOTE.
     */
    public static enum Kind {
        /**
         * Problem which prevents the tool's normal completion.
         */
        ERROR,
        /**
         * Problem which does not usually prevent the tool from
         * completing normally.
         */
        WARN,
        /**
         * Informative message from the tool.
         */
        NOTE
    }

    /**
     * Gets the kind of this diagnostic, for example, error or
     * warning.
     *
     * @return the kind of this diagnostic
     */
    public Kind getKind() {
        return kind;
    }

    public String getCode() {
        return code;
    }

    // todo: message[s] ?
    public String[] getMessage() {
        return messages;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public String[] getMessageArgs() {
        return messageArgs;
    }

    /**
     * Gets the source code line with problem.
     *
     * @return source code line
     */
    public String getLine() {
        return line;
    }

    public int getColNum() {
        return colNum;
    }

    public int getLen() {
        return len;
    }

    public int getOffset() {
        return offset;
    }

    public int getRowNum() {
        return rowNum;
    }
}
