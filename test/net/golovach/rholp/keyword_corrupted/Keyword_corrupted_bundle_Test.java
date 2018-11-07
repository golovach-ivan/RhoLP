package net.golovach.rholp.keyword_corrupted;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.warn;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Keyword_corrupted_bundle_Test {

    static final String ERROR_CODE = "lexer.warn.identifier.like-existing-keyword";

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_Bundle() {

        List<RhoToken> tokens = tokenize("Bundle", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE() {

        List<RhoToken> tokens = tokenize("BUNDLE", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_zero() {

        List<RhoToken> tokens = tokenize("Bundle0", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle0"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle0").row(1).col(1).len(7).offset(0)
                        .msgArgs("Bundle0", "bundle0"));
    }

    @Test
    public void test_BUNDLE_zero() {

        List<RhoToken> tokens = tokenize("BUNDLE0", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE0"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE0").row(1).col(1).len(7).offset(0)
                        .msgArgs("BUNDLE0", "bundle0"));
    }

    @Test
    public void test_Bundle_plus() {

        List<RhoToken> tokens = tokenize("Bundle+", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle+").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_plus() {

        List<RhoToken> tokens = tokenize("BUNDLE+", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE+").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_minus() {

        List<RhoToken> tokens = tokenize("Bundle-", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle-").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_minus() {

        List<RhoToken> tokens = tokenize("BUNDLE-", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE-").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_zero_zero() {

        List<RhoToken> tokens = tokenize("Bundle00", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle00"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_BUNDLE_zero_zero() {

        List<RhoToken> tokens = tokenize("BUNDLE00", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE00"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Bundle_zero_plus() {

        List<RhoToken> tokens = tokenize("Bundle0+", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle0"), PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle0+").row(1).col(1).len(7).offset(0)
                        .msgArgs("Bundle0", "bundle0"));
    }

    @Test
    public void test_BUNDLE_zero_plus() {

        List<RhoToken> tokens = tokenize("BUNDLE0+", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE0"), PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE0+").row(1).col(1).len(7).offset(0)
                        .msgArgs("BUNDLE0", "bundle0"));
    }

    @Test
    public void test_Bundle_zero_minus() {

        List<RhoToken> tokens = tokenize("Bundle0-", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle0"), MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle0-").row(1).col(1).len(7).offset(0)
                        .msgArgs("Bundle0", "bundle0"));
    }

    @Test
    public void test_BUNDLE_zero_minus() {

        List<RhoToken> tokens = tokenize("BUNDLE0-", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE0"), MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE0-").row(1).col(1).len(7).offset(0)
                        .msgArgs("BUNDLE0", "bundle0"));
    }

    @Test
    public void test_Bundle_plus_zero() {

        List<RhoToken> tokens = tokenize("Bundle+0", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), PLUS.T, LITERAL_INT.T("0"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle+0").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_plus_zero() {

        List<RhoToken> tokens = tokenize("BUNDLE+0", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), PLUS.T, LITERAL_INT.T("0"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE+0").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_plus_plus() {

        List<RhoToken> tokens = tokenize("Bundle++", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), PLUS_PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle++").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_plus_plus() {

        List<RhoToken> tokens = tokenize("BUNDLE++", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), PLUS_PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE++").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_plus_minus() {

        List<RhoToken> tokens = tokenize("Bundle+-", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), PLUS.T, MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle+-").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_plus_minus() {

        List<RhoToken> tokens = tokenize("BUNDLE+-", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), PLUS.T, MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE+-").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_minus_zero() {

        List<RhoToken> tokens = tokenize("Bundle-0", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), MINUS.T, LITERAL_INT.T("0"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle-0").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_minus_zero() {

        List<RhoToken> tokens = tokenize("BUNDLE-0", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), MINUS.T, LITERAL_INT.T("0"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE-0").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_minus_plus() {

        List<RhoToken> tokens = tokenize("Bundle-+", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), MINUS.T, PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle-+").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_minus_plus() {

        List<RhoToken> tokens = tokenize("BUNDLE-+", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), MINUS.T, PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE-+").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }

    @Test
    public void test_Bundle_minus_minus() {

        List<RhoToken> tokens = tokenize("Bundle--", collector);

        assertThat(tokens, is(asList(IDENT.T("Bundle"), MINUS_MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("Bundle--").row(1).col(1).len(6).offset(0)
                        .msgArgs("Bundle", "bundle"));
    }

    @Test
    public void test_BUNDLE_minus_minus() {

        List<RhoToken> tokens = tokenize("BUNDLE--", collector);

        assertThat(tokens, is(asList(IDENT.T("BUNDLE"), MINUS_MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn(ERROR_CODE)
                        .line("BUNDLE--").row(1).col(1).len(6).offset(0)
                        .msgArgs("BUNDLE", "bundle"));
    }
}

