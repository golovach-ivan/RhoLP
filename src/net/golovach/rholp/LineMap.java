package net.golovach.rholp;

/**
 * Provides methods to convert between character positions
 * and line numbers for a compilation unit.
 * <p/>
 * A class that defines source code positions as simple character
 * offsets from the beginning of the file. The first character
 * is at position 0.
 * <p/>
 * Support is also provided for (line,column) coordinates, but tab
 * expansion is optional and no Unicode excape translation is considered.
 * The first character is at location (1,1).
 * <p/>
 * <p/> Variation of {@link com.sun.source.tree.LineMap}
 */
public interface LineMap {

    /**
     * Find the start position of a line.
     *
     * @param line line number (beginning at 1)
     * @return position of first character in line
     * @throws IndexOutOfBoundsException if <tt>lineNumber < 1</tt>
     *                                   if <tt>lineNumber > no. of lines</tt>
     */
    int getStartPosition(int line);

    /**
     * Find the position corresponding to a (line,column).
     *
     * @param line   line number (beginning at 1)
     * @param column tab-expanded column number (beginning 1)
     * @return position of character
     * @throws IndexOutOfBoundsException if {@code line < 1}
     *                                   if {@code line > no. of lines}
     */
    int getPosition(int line, int column);

    /**
     * Find the line containing a position; a line termination
     * character is on the line it terminates.
     *
     * @param pos character offset of the position
     * @return the line number of pos (first line is 1)
     */
    int getLineNumber(int pos);

    /**
     * Find the column for a character position.
     * Tab characters preceding the position on the same line
     * will be expanded when calculating the column number.
     *
     * @param pos character offset of the position
     * @return the tab-expanded column number of pos (first column is 1)
     */
    int getColumnNumber(int pos);
    
    int getLineCount();
}
