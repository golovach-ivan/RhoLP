package net.golovach.rholp.keyword;

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

public class Keyword_in_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_in() {

        List<RhoToken> tokens = tokenize("in", collector);

        assertThat(tokens, is(asList(IN.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_in_() {

        List<RhoToken> tokens = tokenize("in_", collector);

        assertThat(tokens, is(asList(IDENT.T("in_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ina() {

        List<RhoToken> tokens = tokenize("ina", collector);

        assertThat(tokens, is(asList(IDENT.T("ina"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_in0() {

        List<RhoToken> tokens = tokenize("in0", collector);

        assertThat(tokens, is(asList(IDENT.T("in0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_in_plus() {

        List<RhoToken> tokens = tokenize("in+", collector);

        assertThat(tokens, is(asList(IN.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_in$() {

        List<RhoToken> tokens = tokenize("in$", collector);

        assertThat(tokens, is(asList(IN.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("in$").row(1).col(3).len(1).offset(2));
    }
}

