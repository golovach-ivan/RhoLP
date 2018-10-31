package net.golovach.rholp.log;

public class DiagnosticPrinter implements DiagnosticListener {

    @Override
    public void report(Diagnostic d) {

        final String line = d.getLine();

        int first = 0;
        while ((first < line.length()) && (line.charAt(first) <= ' ')) {
            first++;
        }
        int last = line.length();
        while ((first <= last) && (line.charAt(last - 1) <= ' ')) {
            last--;
        }
        final String trimmedLine = line.substring(first, last);

        System.err.println(d.getKind());
        System.err.println("  Line: " + d.getLineNumber() + ", Column: " + d.getColumnNumber() + ", Code: " + d.getCode());
        System.err.println("  Message: " + d.getMessage());
        System.err.println("  " + trimmedLine);
        for (int k = 0; k < d.getStartPosLine() + 2 - first; k++) {
            System.err.print(' ');
        }
        for (int k = 0; k <= d.getLen(); k++) {
            System.err.print('^');
        }
        System.err.println();
    }

    @Override
    public void eof() {
        // NOP
    }
}
