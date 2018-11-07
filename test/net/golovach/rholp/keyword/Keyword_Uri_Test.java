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

public class Keyword_Uri_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_Uri() {

        List<RhoToken> tokens = tokenize("Uri", collector);

        assertThat(tokens, is(asList(URI.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Uri_() {

        List<RhoToken> tokens = tokenize("Uri_", collector);

        assertThat(tokens, is(asList(IDENT.T("Uri_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Uria() {

        List<RhoToken> tokens = tokenize("Uria", collector);

        assertThat(tokens, is(asList(IDENT.T("Uria"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Uri0() {

        List<RhoToken> tokens = tokenize("Uri0", collector);

        assertThat(tokens, is(asList(IDENT.T("Uri0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Uri_plus() {

        List<RhoToken> tokens = tokenize("Uri+", collector);

        assertThat(tokens, is(asList(URI.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Uri$() {

        List<RhoToken> tokens = tokenize("Uri$", collector);

        assertThat(tokens, is(asList(URI.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("Uri$").row(1).col(4).len(1).offset(3));
    }
}

