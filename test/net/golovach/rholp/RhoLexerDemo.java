package net.golovach.rholp;

import net.golovach.rholp.log.DiagnosticCollector;

//> CLASS / class
//> IDENT / MyClass
//> LBRACE /
//> INT / int
//> IDENT / x
//> EQ / =
//> LITERAL_INT / 9999999999
//> RBRACE /
//> EOF /
public class RhoLexerDemo {

    static final String content
            = "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";
//            = "λ";

    public static void main(String[] args) throws InterruptedException {

        RhoLexer lexer = new RhoLexer(content, new DiagnosticCollector());

        do {
            lexer.nextToken();
            String val = lexer.stringVal();
            if (val != null && !val.isEmpty()) {
                System.out.println(lexer.token() + ": '" + lexer.stringVal() + "'");
            } else {
                System.out.println(lexer.token());
            }
        } while (lexer.token() != RhoTokenType.EOF);

        System.out.println();

//        ((DiagnosticCollector) lexer.listener).dump(System.out);
    }
}