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

public class Keyword_else_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_else() {

        List<RhoToken> tokens = tokenize("else", collector);

        assertThat(tokens, is(asList(ELSE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_else_() {

        List<RhoToken> tokens = tokenize("else_", collector);

        assertThat(tokens, is(asList(IDENT.T("else_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_elsea() {

        List<RhoToken> tokens = tokenize("elsea", collector);

        assertThat(tokens, is(asList(IDENT.T("elsea"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_else0() {

        List<RhoToken> tokens = tokenize("else0", collector);

        assertThat(tokens, is(asList(IDENT.T("else0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_else_plus() {

        List<RhoToken> tokens = tokenize("else+", collector);

        assertThat(tokens, is(asList(ELSE.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_else$() {

        List<RhoToken> tokens = tokenize("else$", collector);

        assertThat(tokens, is(asList(ELSE.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("else$").row(1).col(5).len(1).offset(4));
    }
}

