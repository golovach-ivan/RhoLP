package net.golovach.rholp.operator;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Percent_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_percent() {

        List<RhoToken> tokens = tokenize("%", collector);

        assertThat(tokens, is(asList(ERROR.T("%"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.arithmetic")
                        .line("%").row(1).col(1).len(1).offset(0));
    }

    @Test
    public void test_percent_percent() {

        List<RhoToken> tokens = tokenize("%%", collector);

        assertThat(tokens, is(asList(PERCENT_PERCENT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

