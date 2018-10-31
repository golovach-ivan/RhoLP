package net.golovach.rholp.complex;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_complex_Test {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
                {"" +
                        "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]"},
                {"" +
                        "char ch = scanChar();\n" +
                        "switch (ch) {\n" +
                        "    case 'A': return a();\n" +
                        "    case 'A': return a();\n" +
                        "    case 'A': return a();\n" +
                        "    default: throw new Error();\n" +
                        "}"},
                {"" +
                        "val seq = 'A' :: 'B' :: 'C' :: Nil\n" +
                        "def toString(list: List[String]): String = list match {\n" +
                        "    case s :: rest => s + \" \" + listToString(rest)\n" +
                        "    case Nil => \"\"\n" +
                        "}" +
                        "println toString(seq)"},
                {"" +
                        "def liftF[F[_]](implicit I: Ast :<: F): Console[Free[F, ?]] =\n" +
                        "    new Console[Free[F, ?]] {\n" +
                        "        def read: Free[F, String] = Free.liftF(I.inj(Read()))\n" +
                        "        def write(s: String): Free[F, Unit] = Free.liftF(I.inj(Write(s)))\n" +
                        "    }"},
                // |@|, ~>, ?, :<:
        });
    }

    @Test
    public void test_one() {
        assertThat(tokenize("$"), is(asList(
                ERROR, EOF)));
    }
}
