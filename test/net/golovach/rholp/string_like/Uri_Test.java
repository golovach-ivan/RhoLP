package net.golovach.rholp.string_like;

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

public class Uri_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_quote() {

        List<RhoToken> tokens = tokenize("`", collector);

        assertThat(tokens, is(asList(ERROR.T("`"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.uri.unclosed")
                        .line("`").row(1).col(1).len(1).offset(0));
    }

    @Test
    public void test_a_quote() {

        List<RhoToken> tokens = tokenize("a`", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("`"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.uri.unclosed")
                        .line("a`").row(1).col(2).len(1).offset(1));
    }

    @Test
    public void test_quote_a() {

        List<RhoToken> tokens = tokenize("`a", collector);

        assertThat(tokens, is(asList(ERROR.T("`a"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.uri.unclosed")
                        .line("`a").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_a_quote_a() {

        List<RhoToken> tokens = tokenize("a`b", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("`b"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.uri.unclosed")
                        .line("a`b").row(1).col(2).len(2).offset(1));
    }

    @Test
    public void test_quote_quote() {

        List<RhoToken> tokens = tokenize("``", collector);

        assertThat(tokens, is(asList(LITERAL_URI.T(""), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_quote_a_quote() {

        List<RhoToken> tokens = tokenize("`b`", collector);

        assertThat(tokens, is(asList(LITERAL_URI.T("b"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_quote_a_quote_a() {

        List<RhoToken> tokens = tokenize("a`b`c", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), LITERAL_URI.T("b"), IDENT.T("c"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

