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

public class Keyword_Nil_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_Nil() {

        List<RhoToken> tokens = tokenize("Nil", collector);

        assertThat(tokens, is(asList(NIL.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Nil_() {

        List<RhoToken> tokens = tokenize("Nil_", collector);

        assertThat(tokens, is(asList(IDENT.T("Nil_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Nila() {

        List<RhoToken> tokens = tokenize("Nila", collector);

        assertThat(tokens, is(asList(IDENT.T("Nila"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Nil0() {

        List<RhoToken> tokens = tokenize("Nil0", collector);

        assertThat(tokens, is(asList(IDENT.T("Nil0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Nil_plus() {

        List<RhoToken> tokens = tokenize("Nil+", collector);

        assertThat(tokens, is(asList(NIL.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Nil$() {

        List<RhoToken> tokens = tokenize("Nil$", collector);

        assertThat(tokens, is(asList(NIL.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("Nil$").row(1).col(4).len(1).offset(3));
    }
}

