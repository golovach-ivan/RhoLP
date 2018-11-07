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

public class Keyword_not_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_not() {

        List<RhoToken> tokens = tokenize("not", collector);

        assertThat(tokens, is(asList(NOT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_not_() {

        List<RhoToken> tokens = tokenize("not_", collector);

        assertThat(tokens, is(asList(IDENT.T("not_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_nota() {

        List<RhoToken> tokens = tokenize("nota", collector);

        assertThat(tokens, is(asList(IDENT.T("nota"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_not0() {

        List<RhoToken> tokens = tokenize("not0", collector);

        assertThat(tokens, is(asList(IDENT.T("not0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_not_plus() {

        List<RhoToken> tokens = tokenize("not+", collector);

        assertThat(tokens, is(asList(NOT.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_not$() {

        List<RhoToken> tokens = tokenize("not$", collector);

        assertThat(tokens, is(asList(NOT.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("not$").row(1).col(4).len(1).offset(3));
    }
}

