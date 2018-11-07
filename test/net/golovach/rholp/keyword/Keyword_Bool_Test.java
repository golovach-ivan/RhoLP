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

public class Keyword_Bool_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_Bool() {

        List<RhoToken> tokens = tokenize("Bool", collector);

        assertThat(tokens, is(asList(BOOL.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Bool_() {

        List<RhoToken> tokens = tokenize("Bool_", collector);

        assertThat(tokens, is(asList(IDENT.T("Bool_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Boola() {

        List<RhoToken> tokens = tokenize("Boola", collector);

        assertThat(tokens, is(asList(IDENT.T("Boola"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Bool0() {

        List<RhoToken> tokens = tokenize("Bool0", collector);

        assertThat(tokens, is(asList(IDENT.T("Bool0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Bool_plus() {

        List<RhoToken> tokens = tokenize("Bool+", collector);

        assertThat(tokens, is(asList(BOOL.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Bool$() {

        List<RhoToken> tokens = tokenize("Bool$", collector);

        assertThat(tokens, is(asList(BOOL.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("Bool$").row(1).col(5).len(1).offset(4));
    }
}

