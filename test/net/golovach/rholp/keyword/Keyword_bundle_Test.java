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

public class Keyword_bundle_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_bundle() {

        List<RhoToken> tokens = tokenize("bundle", collector);

        assertThat(tokens, is(asList(BUNDLE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle0() {

        List<RhoToken> tokens = tokenize("bundle0", collector);

        assertThat(tokens, is(asList(BUNDLE_ZERO.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_plus() {

        List<RhoToken> tokens = tokenize("bundle+", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_minus() {

        List<RhoToken> tokens = tokenize("bundle-", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_() {

        List<RhoToken> tokens = tokenize("bundle_", collector);

        assertThat(tokens, is(asList(IDENT.T("bundle_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle0_() {

        List<RhoToken> tokens = tokenize("bundle0_", collector);

        assertThat(tokens, is(asList(IDENT.T("bundle0_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_plus_() {

        List<RhoToken> tokens = tokenize("bundle+_", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, WILDCARD.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_minus_() {

        List<RhoToken> tokens = tokenize("bundle-_", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, WILDCARD.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundlea() {

        List<RhoToken> tokens = tokenize("bundlea", collector);

        assertThat(tokens, is(asList(IDENT.T("bundlea"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle0a() {

        List<RhoToken> tokens = tokenize("bundle0a", collector);

        assertThat(tokens, is(asList(IDENT.T("bundle0a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_plus_a() {

        List<RhoToken> tokens = tokenize("bundle+a", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, IDENT.T("a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_minus_a() {

        List<RhoToken> tokens = tokenize("bundle-a", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, IDENT.T("a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle00() {

        List<RhoToken> tokens = tokenize("bundle00", collector);

        assertThat(tokens, is(asList(IDENT.T("bundle00"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_plus_0() {

        List<RhoToken> tokens = tokenize("bundle+0", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, LITERAL_INT.T("0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_minus_0() {

        List<RhoToken> tokens = tokenize("bundle-0", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, LITERAL_INT.T("0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_plus_plus() {

        List<RhoToken> tokens = tokenize("bundle++", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_minus_plus() {

        List<RhoToken> tokens = tokenize("bundle-+", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_plus_minus() {

        List<RhoToken> tokens = tokenize("bundle+-", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, MINUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle_minus_minus() {

        List<RhoToken> tokens = tokenize("bundle--", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, MINUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bundle$() {

        List<RhoToken> tokens = tokenize("bundle$", collector);

        assertThat(tokens, is(asList(BUNDLE.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("bundle$").row(1).col(7).len(1).offset(6));
    }

    @Test
    public void test_bundle0$() {

        List<RhoToken> tokens = tokenize("bundle0$", collector);

        assertThat(tokens, is(asList(BUNDLE_ZERO.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("bundle0$").row(1).col(8).len(1).offset(7));
    }

    @Test
    public void test_bundle_plus_$() {

        List<RhoToken> tokens = tokenize("bundle+$", collector);

        assertThat(tokens, is(asList(BUNDLE_PLUS.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("bundle+$").row(1).col(8).len(1).offset(7));
    }

    @Test
    public void test_bundle_minus_$() {

        List<RhoToken> tokens = tokenize("bundle-$", collector);

        assertThat(tokens, is(asList(BUNDLE_MINUS.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("bundle-$").row(1).col(8).len(1).offset(7));
    }
}

