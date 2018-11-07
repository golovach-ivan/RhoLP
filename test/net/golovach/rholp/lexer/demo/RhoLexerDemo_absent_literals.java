package net.golovach.rholp.lexer.demo;

import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.MessageDB;
import net.golovach.rholp.log.impl.CollapsedPrinter;
import net.golovach.rholp.log.impl.MessageDBImpl;

import static java.util.ResourceBundle.getBundle;

public class RhoLexerDemo_absent_literals {

    static final String content = "" +
            "new chan in {" +
            "  chan!(['A', 42L, 42.42e-42f, 9999999999999999999])" +
            "}";

    public static void main(String[] args) {

        DiagnosticListener listener = new CollapsedPrinter(System.out);
        RhoLexer lexer = new RhoLexer(content, listener);

        lexer.readAll();
    }
}