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

    static final String ERR_CODE_URI_UNCLOSED = "lexer.err.literal.uri.unclosed";

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_quote() {

        List<RhoToken> tokens = tokenize("`", collector);

        assertThat(tokens, is(asList(ERROR.T("`"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error(ERR_CODE_URI_UNCLOSED)
                        .line("`").row(1).col(1).len(1).offset(0));
    }

    @Test
    public void test_a_quote() {

        List<RhoToken> tokens = tokenize("a`", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("`"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error(ERR_CODE_URI_UNCLOSED)
                        .line("a`").row(1).col(2).len(1).offset(1));
    }

    @Test
    public void test_quote_a() {

        List<RhoToken> tokens = tokenize("`a", collector);

        assertThat(tokens, is(asList(ERROR.T("`a"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error(ERR_CODE_URI_UNCLOSED)
                        .line("`a").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_a_quote_a() {

        List<RhoToken> tokens = tokenize("a`b", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("`b"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error(ERR_CODE_URI_UNCLOSED)
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

    @Test
    public void test_a_quote_a_LF_a_quote_CR_LF() {

        List<RhoToken> tokens = tokenize("a`b\nc`d\r\n", collector);

        assertThat(tokens, is(asList(
                IDENT.T("a"), ERROR.T("`b"), IDENT.T("c"), ERROR.T("`d"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error(ERR_CODE_URI_UNCLOSED)
                        .line("a`b\n").row(1).col(2).len(2).offset(1),
                error(ERR_CODE_URI_UNCLOSED)
                        .line("c`d\r\n").row(2).col(2).len(2).offset(5)
        );
    }

    @Test
    public void test_a_quote_a_CR_LF_a_quote_LF() {

        List<RhoToken> tokens = tokenize("a`b\r\nc`d\n", collector);

        assertThat(tokens, is(asList(
                IDENT.T("a"), ERROR.T("`b"), IDENT.T("c"), ERROR.T("`d"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error(ERR_CODE_URI_UNCLOSED)
                        .line("a`b\r\n").row(1).col(2).len(2).offset(1),
                error(ERR_CODE_URI_UNCLOSED)
                        .line("c`d\n").row(2).col(2).len(2).offset(6)
        );
    }
}

