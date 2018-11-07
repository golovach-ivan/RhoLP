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

public class Keyword_and_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_and() {

        List<RhoToken> tokens = tokenize("and", collector);

        assertThat(tokens, is(asList(AND.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_and_() {

        List<RhoToken> tokens = tokenize("and_", collector);

        assertThat(tokens, is(asList(IDENT.T("and_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_anda() {

        List<RhoToken> tokens = tokenize("anda", collector);

        assertThat(tokens, is(asList(IDENT.T("anda"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_and0() {

        List<RhoToken> tokens = tokenize("and0", collector);

        assertThat(tokens, is(asList(IDENT.T("and0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_and_plus() {

        List<RhoToken> tokens = tokenize("and+", collector);

        assertThat(tokens, is(asList(AND.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_and$() {

        List<RhoToken> tokens = tokenize("and$", collector);

        assertThat(tokens, is(asList(AND.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("and$").row(1).col(4).len(1).offset(3));
    }
}

