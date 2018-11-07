package net.golovach.rholp.log.impl;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;

import java.io.PrintStream;
import java.util.*;

import static java.util.Arrays.asList;

public class CollapsedPrinter implements DiagnosticListener {
    
    static class Key {
        final int lineNum;
        final Diagnostic.Kind kind;
        final String errorCode;

        Key(Diagnostic d) {
            this.lineNum = d.getRowNum();
            this.kind = d.getKind();
            this.errorCode = d.getCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (lineNum != key.lineNum) return false;
            if (errorCode != null ? !errorCode.equals(key.errorCode) : key.errorCode != null) return false;
            if (kind != key.kind) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = lineNum;
            result = 31 * result + (kind != null ? kind.hashCode() : 0);
            result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
            return result;
        }
    }

    private final PrintStream out;
    private final LinkedHashMap<Key, List<Diagnostic>> map = new LinkedHashMap<>();

    public CollapsedPrinter() {
        this(System.err);
    }    
    
    public CollapsedPrinter(PrintStream out) {
        this.out = out;
    }

    @Override
    public void report(Diagnostic d) {
        Key key = new Key(d);
        if (map.containsKey(key)) {
            map.get(key).add(d);            
        } else {
            map.put(key, new ArrayList<>(asList(d)));
        }
    }

    @Override
    public void eof() {
        for (Map.Entry<Key, List<Diagnostic>> e : map.entrySet()) {
            Key key = e.getKey();
            List<Diagnostic> diagnostics = e.getValue();

            out.println(key.kind);
            out.println("  Error code: " + key.errorCode);

            List<String> messages = uniqueMessages(diagnostics);
            if (messages.size() == 1) {
                out.println("  Message:    " + messages.get(0));
            } else {
                out.println("  Messages:");
                for (String msg: messages) {
                    out.println("    " + msg);
                }
            }
            
            out.print("  Line/Column: ");
            
            for (int k = 0; k < diagnostics.size(); k++) {
                Diagnostic d = diagnostics.get(k);
                out.print("[" + d.getRowNum() + ", " + d.getColNum() + "]");
                if (k < diagnostics.size() - 1) {
                    out.print(", ");
                }
            }
            out.println();
            out.println("  ----------");

            // === trimmed line
            final String line = diagnostics.get(0).getLine();
            int first = 0;
            while ((first < line.length()) && ((line.charAt(first) == ' ') || (line.charAt(first) == '\t'))) {
                first++;
            }
            int last = line.length();
            while ((first <= last) && ((line.charAt(last - 1) == '\n') || (line.charAt(last - 1) == '\r'))) {
                last--;
            }
            // todo: fail on "\u0000\n\u001D"
            final String trimmedLine = line.substring(first, last);
            out.println("  " + trimmedLine);

            errorPointers(diagnostics, first);
        }
    }
    
    private static List<String> uniqueMessages(List<Diagnostic> ds) {
        LinkedHashSet<String> ms = new LinkedHashSet<>();
        for (Diagnostic d : ds) {
            ms.add(d.getMessage()[0]);
        }
        return new ArrayList<>(ms);
    }

    private void errorPointers(List<Diagnostic> ds, int first) {

//        out.println(("  " + errorPointers(diagnostics)).substring(first));

        String line = ds.get(0).getLine();

        char[] buffer = new char[line.length()];

        // init buffer with spaces ' '
        for (int k = 0; k < buffer.length; k++) {
            buffer[k] = ' ';
        }

        // add pointers
        for (Diagnostic d: ds) {
            for (int k = 0; k < d.getLen(); k++) {
                buffer[d.getColNum() - LineMapImpl.FIRST_COL + k] = '^';
            }
        }

        out.println(("  " + new String(buffer)).substring(first));
    }    
}
