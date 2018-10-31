package net.golovach.rholp;

import net.golovach.rholp.log.*;

import java.util.List;

//> CLASS / class
//> IDENT / MyClass
//> LBRACE /
//> INT / int
//> IDENT / x
//> EQ / =
//> LITERAL_INT / 9999999999
//> RBRACE /
//> EOF /
public class RhoLexerDemoBounties {

    public static void main(String[] args) throws InterruptedException {

        String content0 =
                "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";

        String content1 = "" +
                "char ch = scanChar();" +
                "switch (ch) {\n" +
                "    case 'A': return a();\n" +
                "    case 'B': return b();\n" +
                "    case 'C': return c();\n" +
                "    default: throw new Error();\n" +
                "}";

        String content2 = "" +
                "val seq = 'A' :: 'B' :: 'C' :: Nil\n" +
                "def toString(list: List[String]): String = list match {\n" +
                "    case s :: rest => s + \" \" + listToString(rest)\n" +
                "    case Nil => \"\"\n" +
                "}" +
                "println toString(seq)";

        String content3 = "" +
                "def liftF[F[_]](implicit I: Ast :<: F): Console[Free[F, ?]] =\n" +
                "    new Console[Free[F, ?]] {\n" +
                "        def read: Free[F, String] = Free.liftF(I.inj(Read()))\n" +
                "        def write(s: String): Free[F, Unit] = Free.liftF(I.inj(Write(s)))\n" +
                "    }";

        DiagnosticListener listener = new DiagnosticCollapsedPrinter();
        RhoLexer lexer = new RhoLexer(content3, listener);

        List<RhoTokenType> tokens = lexer.scanAll();
//        System.out.println(tokens);
    }
}
