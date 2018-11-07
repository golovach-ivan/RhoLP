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

public class Keyword_for_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_for() {

        List<RhoToken> tokens = tokenize("for", collector);

        assertThat(tokens, is(asList(FOR.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_for_() {

        List<RhoToken> tokens = tokenize("for_", collector);

        assertThat(tokens, is(asList(IDENT.T("for_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_fora() {

        List<RhoToken> tokens = tokenize("fora", collector);

        assertThat(tokens, is(asList(IDENT.T("fora"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_for0() {

        List<RhoToken> tokens = tokenize("for0", collector);

        assertThat(tokens, is(asList(IDENT.T("for0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_for_plus() {

        List<RhoToken> tokens = tokenize("for+", collector);

        assertThat(tokens, is(asList(FOR.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_for$() {

        List<RhoToken> tokens = tokenize("for$", collector);

        assertThat(tokens, is(asList(FOR.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("for$").row(1).col(4).len(1).offset(3));
    }
}

