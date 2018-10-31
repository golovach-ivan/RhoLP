package net.golovach.rholp;

/**
 * @see com.sun.tools.javac.util.Position.LineMap
 */
public class LineMapImpl implements LineMap {
    public static final int FIRST_POS     = 0;
    public static final int FIRST_LINE    = 1;
    public static final int FIRST_COLUMN  = 1;

    protected int[] startPosition; // start position of each line

    public LineMapImpl(char[] src, int max) {
        int c = 0;
        int i = 0;
        int[] lineBuf = new int[max];
        while (i < max) {
            lineBuf[c++] = i;
            do {
                char ch = src[i];
                if (ch == '\r' || ch == '\n') {
                    if (ch == '\r' && (i+1) < max && src[i+1] == '\n')
                        i += 2;
                    else
                        ++i;
                    break;
                }
            } while (++i < max);
        }
        this.startPosition = new int[c];
        System.arraycopy(lineBuf, 0, startPosition, 0, c);
    }

    @Override
    public int getStartPosition(int line) {
        return startPosition[line - FIRST_LINE];
    }

    @Override
    public int getPosition(int line, int column) {
        return startPosition[line - FIRST_LINE] + column - FIRST_COLUMN;
    }

    @Override
    public int getLineNumber(int pos) {
        // todo: use standard binary search
        int low = 0;
        int high = startPosition.length-1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            int midVal = startPosition[mid];

            if (midVal < pos)
                low = mid + 1;
            else if (midVal > pos)
                high = mid - 1;
            else {
                return mid + 1;
            }
        }
        return low;
    }

    @Override
    public int getColumnNumber(int pos) {
        return pos - startPosition[getLineNumber(pos) - FIRST_LINE] + FIRST_COLUMN;
    }

    @Override
    public int getLineCount() {
        return startPosition.length;
    }
}
