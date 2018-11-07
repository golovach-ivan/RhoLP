package net.golovach.rholp.log.impl;

import net.golovach.rholp.log.LineMap;

import java.util.ArrayList;
import java.util.List;

public class LineMapImpl implements LineMap {
    // start position of each line
    private final String src;
    private final List<Integer> startPositions = new ArrayList<>();

    public LineMapImpl(String src) {
        this.src = src;

        int charIndex = 0;
        while (charIndex < src.length()) {
            startPositions.add(charIndex);
            do {
                char ch = src.charAt(charIndex);
                if (ch == '\r' || ch == '\n') {
                    if (ch == '\r' && (charIndex + 1) < src.length() && src.charAt(charIndex + 1) == '\n') {
                        charIndex += 2;
                    } else {
                        charIndex += 1;
                    }
                    break;
                }
            } while (++charIndex < src.length());
        }
    }

    @Override
    public int offsetToRow(int offset) {
        // todo: use standard binary search
        int low = 0;
        int high = startPositions.size() - 1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            int midVal = startPositions.get(mid);

            if (midVal < offset)
                low = mid + 1;
            else if (midVal > offset)
                high = mid - 1;
            else {
                return mid + 1;
            }
        }
        return low;
    }

    @Override
    public int offsetToCol(int offset) {
        return offset - startPositions.get(offsetToRow(offset) - FIRST_ROW) + FIRST_COL;
    }

    @Override
    public String offsetToSrcLine(int offset) {
        int rowNum = offsetToRow(offset);
        int start = startPositions.get(rowNum - FIRST_ROW);
        if (rowNum < startPositions.size()) {
            int end = startPositions.get(rowNum);
            return src.substring(start, end);
        } else {
            int end = src.length();
            return src.substring(start, end);
        }
    }
}
