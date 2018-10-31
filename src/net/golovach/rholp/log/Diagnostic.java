package net.golovach.rholp.log;

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
    private final Kind kind;
    private final String line;
    private final int startPosFile;
    private final int startPosLine;
    private final int len;
    private final int lineNumber;
    private final int columnNumber;
    private final String code;
    private final String message;

    public Diagnostic(Kind kind,
                      String line,
                      int startPosFile,
                      int startPosLine,
                      int len,
                      int lineNumber,
                      int columnNumber,
                      String code,
                      String message) {
        this.kind = kind;
        this.line = line;
        this.startPosFile = startPosFile;
        this.startPosLine = startPosLine;
        this.len = len;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.code = code;
        this.message = message;
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

    /**
     * Gets the source code line with problem.
     *
     * @return source code line
     */
    public String getLine() {
        return line;
    }

    public int getStartPosFile() {
        return startPosFile;
    }

    public int getStartPosLine() {
        return startPosLine;
    }

    public int getLen() {
        return len;
    }

    /**
     * Gets the line number of the character offset returned by
     * {@linkplain #getStartPosition()}. Indexing start with 1.
     *
     * @return a line number of problem
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Gets the column number of the character offset returned by
     * {@linkplain #getStartPosition()}. Indexing start with 1.
     *
     * @return a column number of problem
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * Gets a diagnostic code indicating the type of diagnostic.
     *
     * @return a diagnostic code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets a diagnostic message.
     *
     * @return a diagnostic message
     */
    public String getMessage() {
        return message;
    }
}
