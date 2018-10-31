package net.golovach.rholp;

import net.golovach.rholp.log.DiagnosticCollapsedPrinter;
import net.golovach.rholp.log.DiagnosticListener;

import java.util.List;

public class RhoLexerDemoBounties0 {

    public static void main(String[] args) {

        String content =
                "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";

        DiagnosticListener listener = new DiagnosticCollapsedPrinter();
        RhoLexer lexer = new RhoLexer(content, listener);

        List<RhoTokenType> tokens = lexer.scanAll();
    }
}
