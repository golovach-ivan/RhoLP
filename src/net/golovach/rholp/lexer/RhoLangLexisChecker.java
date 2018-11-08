package net.golovach.rholp.lexer;

import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.GroupedPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RhoLangLexisChecker {

    public static void main(String[] args) throws IOException {

        if (args == null || args.length != 1 ) {
            System.err.println("tool need one arg: path to RhoLang source");
            System.exit(1);
        } else {
            String content = new String(Files.readAllBytes(Paths.get(args[0])));

            DiagnosticListener listener = new GroupedPrinter(System.out);
            RhoLexer lexer = new RhoLexer(content, listener);

            lexer.readAll();
        }
    }
}
