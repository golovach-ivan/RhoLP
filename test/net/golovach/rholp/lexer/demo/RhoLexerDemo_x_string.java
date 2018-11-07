package net.golovach.rholp.lexer.demo;

import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.GroupedPrinter;

public class RhoLexerDemo_x_string {

    public static void main(String[] args) {

        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer("['A', α, `rho:io:stdout, ]", listener);

        lexer.readAll();
    }
}