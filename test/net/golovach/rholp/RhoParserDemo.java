package net.golovach.rholp;

import net.golovach.rholp.log.DiagnosticCollector;

import static net.golovach.rholp.tree.RhoTree.RhoProcessTree;

public class RhoParserDemo {

    // todo: ")Nil"
    // todo: "(Nil"
    // todo: "Nil)"
    // todo: "(Nil))"
    static final String content = "(((Nil))";

    public static void main(String[] args) {
        final DiagnosticCollector collector = new DiagnosticCollector();

        RhoLexer lexer = new RhoLexer(content, collector);
        RhoParser parser = new RhoParser(lexer, collector);

        RhoProcessTree process = parser.process();

        System.out.println(process);

        int x = 0;
    }
}
