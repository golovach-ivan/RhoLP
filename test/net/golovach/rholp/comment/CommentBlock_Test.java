package net.golovach.rholp.comment;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static net.golovach.rholp.RhoTokenType.IDENT;
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CommentBlock_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_div_star() {

        List<RhoToken> tokens = tokenize("/*", collector);

        assertThat(tokens, is(asList(ERROR.T("/*"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.comment.unclosed")
                        .line("/*").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_a_div_star() {

        List<RhoToken> tokens = tokenize("a/*", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("/*"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.comment.unclosed")
                        .line("a/*").row(1).col(2).len(2).offset(1));
    }

    @Test
    public void test_a_div_star_a_LF_a_div_star_a() {

        List<RhoToken> tokens = tokenize("a/*b\nc/*d", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("/*b"), IDENT.T("c"), ERROR.T("/*d"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.comment.unclosed")
                        .line("a/*b\n").row(1).col(2).len(3).offset(1),
                error("lexer.err.comment.unclosed")
                        .line("c/*d").row(2).col(2).len(3).offset(6)
        );
    }

    @Test
    public void test_div_star_star() {

        List<RhoToken> tokens = tokenize("/**", collector);

        assertThat(tokens, is(asList(ERROR.T("/**"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.comment.unclosed")
                        .line("/**").row(1).col(1).len(3).offset(0));
    }

    @Test
    public void test_a_div_star_star() {

        List<RhoToken> tokens = tokenize("a/**", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), ERROR.T("/**"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.comment.unclosed")
                        .line("a/**").row(1).col(2).len(3).offset(1));
    }

    @Test
    public void test_div_star_star_div() {

        List<RhoToken> tokens = tokenize("/**/", collector);

        assertThat(tokens, is(asList(EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div_star_a_star_div_a() {

        List<RhoToken> tokens = tokenize("a/*b*/c", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), IDENT.T("c"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

//todo: a/*b\nc/*d