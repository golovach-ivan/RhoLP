package net.golovach.rholp.lexer.demo;

import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.GroupedPrinter;

import static java.util.ResourceBundle.getBundle;

public class RhoLexerDemo_absent_types {

    static final String content = "" +
            "Map{0 -> `zero`, 1 -> List(Set(0, 1))};\n" +
            "char c = new (long)Array[Char]{int 42}";

    public static void main(String[] args) {

        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer(content, listener);

        lexer.readAll();
    }
}