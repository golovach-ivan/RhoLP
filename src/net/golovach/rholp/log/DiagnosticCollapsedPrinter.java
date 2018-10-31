package net.golovach.rholp.log;

import java.io.PrintStream;
import java.util.*;

import static java.util.Arrays.asList;

public class DiagnosticCollapsedPrinter implements DiagnosticListener {

    static class Key {
        final int lineNum;
        final Diagnostic.Kind kind;
        final String errorCode;

        Key(Diagnostic d) {
            this(d.getLineNumber(), d.getKind(), d.getCode());
        }
        
        Key(int lineNum, Diagnostic.Kind kind, String errorCode) {
            this.lineNum = lineNum;
            this.kind = kind;
            this.errorCode = errorCode;
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
    
    private final LinkedHashMap<Key, List<Diagnostic>> map = new LinkedHashMap<>();
    
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
        PrintStream out = System.err;
        
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
                out.print("[" + d.getLineNumber() + ", " + d.getColumnNumber() + "]");
                if (k < diagnostics.size() - 1) {
                    out.print(", ");
                }
            }
            out.println();
            out.println("  ----------");

            // === trimmed line
            final String line = diagnostics.get(0).getLine();
            int first = 0;
            while ((first < line.length()) && (line.charAt(first) <= ' ')) {
                first++;
            }
            int last = line.length();
            while ((first <= last) && (line.charAt(last - 1) <= ' ')) {
                last--;
            }
            final String trimmedLine = line.substring(first, last);
            out.println("  " + trimmedLine);

            // === error pointers
            out.println(("  " + errorPointers(diagnostics)).substring(first));
        }
    }
    
    private static List<String> uniqueMessages(List<Diagnostic> ds) {
        LinkedHashSet<String> ms = new LinkedHashSet<>();
        for (Diagnostic d : ds) {
            ms.add(d.getMessage());            
        }
        return new ArrayList<>(ms);
    }

    private static String errorPointers(List<Diagnostic> ds) {
        String line = ds.get(0).getLine();

        // init buffer with spaces ' '
        char[] buffer = new char[line.length()];
        for (int k = 0; k < buffer.length; k++) {
            buffer[k] = ' ';
        }

        // add pointers
        for (Diagnostic d: ds) {
            for (int k = 0; k <= d.getLen(); k++) {
                buffer[d.getStartPosLine() + k] = '^';
            }
        }

        return new String(buffer);
    }    
}
