package net.golovach.rholp.keyword;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Keyword_matches_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_matches() {

        List<RhoToken> tokens = tokenize("matches", collector);

        assertThat(tokens, is(asList(MATCHES.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_matches_() {

        List<RhoToken> tokens = tokenize("matches_", collector);

        assertThat(tokens, is(asList(IDENT.T("matches_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_matchesa() {

        List<RhoToken> tokens = tokenize("matchesa", collector);

        assertThat(tokens, is(asList(IDENT.T("matchesa"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_matches0() {

        List<RhoToken> tokens = tokenize("matches0", collector);

        assertThat(tokens, is(asList(IDENT.T("matches0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_matches_plus() {

        List<RhoToken> tokens = tokenize("matches+", collector);

        assertThat(tokens, is(asList(MATCHES.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_matches$() {

        List<RhoToken> tokens = tokenize("matches$", collector);

        assertThat(tokens, is(asList(MATCHES.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("matches$").row(1).col(8).len(1).offset(7));
    }
}

