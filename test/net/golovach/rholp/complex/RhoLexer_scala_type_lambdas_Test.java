package net.golovach.rholp.complex;

import net.golovach.rholp.log.DiagnosticCollector;
import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_scala_type_lambdas_Test {
    final DiagnosticCollector collector
            = new DiagnosticCollector();
    final String content
            = "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";

    @Test
    public void test() {
        assertThat(tokenize(content, collector), is(asList(
                ERROR, EOF)));
    }
}
