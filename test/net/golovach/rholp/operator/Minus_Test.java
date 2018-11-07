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

public class Minus_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_minus() {

        List<RhoToken> tokens = tokenize("-", collector);

        assertThat(tokens, is(asList(MINUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_minus_minus() {

        List<RhoToken> tokens = tokenize("--", collector);

        assertThat(tokens, is(asList(MINUS_MINUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_minus_eq() {

        List<RhoToken> tokens = tokenize("-=", collector);

        assertThat(tokens, is(asList(ERROR.T("-="), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.compound-assignment")
                        .line("-=").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_minus_gt() {

        List<RhoToken> tokens = tokenize("->", collector);

        assertThat(tokens, is(asList(ERROR.T("->"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.arrow")
                        .line("->").row(1).col(1).len(2).offset(0));
    }
}

