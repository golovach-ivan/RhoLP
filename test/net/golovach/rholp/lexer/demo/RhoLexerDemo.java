package net.golovach.rholp.lexer.demo;

import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.GroupedPrinter;

public class RhoLexerDemo {

    public static void main(String[] args) {

        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer(".0", listener);

        lexer.readAll();
    }
}