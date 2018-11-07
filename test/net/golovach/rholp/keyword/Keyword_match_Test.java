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

public class Keyword_match_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_match() {

        List<RhoToken> tokens = tokenize("match", collector);

        assertThat(tokens, is(asList(MATCH.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_match_() {

        List<RhoToken> tokens = tokenize("match_", collector);

        assertThat(tokens, is(asList(IDENT.T("match_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_matcha() {

        List<RhoToken> tokens = tokenize("matcha", collector);

        assertThat(tokens, is(asList(IDENT.T("matcha"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_match0() {

        List<RhoToken> tokens = tokenize("match0", collector);

        assertThat(tokens, is(asList(IDENT.T("match0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_match_plus() {

        List<RhoToken> tokens = tokenize("match+", collector);

        assertThat(tokens, is(asList(MATCH.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_match$() {

        List<RhoToken> tokens = tokenize("match$", collector);

        assertThat(tokens, is(asList(MATCH.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("match$").row(1).col(6).len(1).offset(5));
    }
}

