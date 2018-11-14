package net.golovach.rholp.lexer.demo;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.RhoTokenType;
import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.log.impl.GroupedPrinter;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;

public class RhoLexerDemo_scala {

    static final String content1 = "" +
            "char ch = scanChar();\n" +
            "switch (ch) {\n" +
            "    case 'A': return a();\n" +
            "    case 'A': return a();\n" +
            "    case 'A': return a();\n" +
            "    default: throw new Error();\n" +
            "}";

    static final String content2 = "" +
            "val seq = 'A' :: 'B' :: 'C' :: Nil\n" +
            "def toString(list: List[String]): String = list match {\n" +
            "    case s :: rest => s + \" \" + listToString(rest)\n" +
            "    case Nil => \"\"\n" +
            "}" +
            "println toString(seq)";

    static final String content3 = "" +
            "def liftF[F[_]](implicit I: Ast :<: F): Console[Free[F, ?]] =\n" +
            "    new Console[Free[F, ?]] {\n" +
            "        def read: Free[F, String] = Free.liftF(I.inj(Read()))\n" +
            "        def write(s: String): Free[F, Unit] = Free.liftF(I.inj(Write(s)))\n" +
            "    }";

    static final String content4
            = "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";

    static final String content5 = "" +
            "trait Wrap { type l[T] }\n" +
            "type CInt[T] = T =:= Int\n" +
            "class Curry[U] extends Wrap {type l[T] = T =:= U}\n" +
            "trait Apply[W <: Wrap, T]\n" +
            "implicit def lift[W <: Wrap, T](implicit ev : W#l[T]) = new Apply[W,T] {}";

    static final String content6 = "" +
            "object ~> {\n" +
            "    type Id[X] = X\n" +
            "    trait Const[A] { type Apply[B] = A }\n" +
            "    implicit def idEq : Id ~> Id = new (Id ~> Id) {\n" +
            "        def apply[T](a: T): T = a\n" +
            "    }\n" +
            "}";

//                "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";
//                "val f = λ[Vector ~> List](_.toList)";
//                "val first: List ~> Option = λ[List ~> Option](_.headOption)";
//                "type ~>[-F[_], +G[_]] = NaturalTransformation[F, G]";

    static final String content
//            = "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";
//            = "α";
            = "a\u1DE7c";

    public static void main(String[] args) {

        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer(content, listener);

        List<RhoToken> tokens = lexer.readAll();
    }
}