package net.golovach.rholp.lexer.demo;

import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.GroupedPrinter;

import static java.util.ResourceBundle.getBundle;

public class RhoLexerDemo_absent_literals {

    static final String content = "" +
            "new chan in {" +
            "  chan!(['A', 42L, 42.42e-42f, 9999999999999999999])" +
            "}";

    public static void main(String[] args) {

        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer(content, listener);

        lexer.readAll();
    }
}