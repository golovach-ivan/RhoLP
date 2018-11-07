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

public class Keyword_Set_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_Set() {

        List<RhoToken> tokens = tokenize("Set", collector);

        assertThat(tokens, is(asList(SET.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Set_() {

        List<RhoToken> tokens = tokenize("Set_", collector);

        assertThat(tokens, is(asList(IDENT.T("Set_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Seta() {

        List<RhoToken> tokens = tokenize("Seta", collector);

        assertThat(tokens, is(asList(IDENT.T("Seta"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Set0() {

        List<RhoToken> tokens = tokenize("Set0", collector);

        assertThat(tokens, is(asList(IDENT.T("Set0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Set_plus() {

        List<RhoToken> tokens = tokenize("Set+", collector);

        assertThat(tokens, is(asList(SET.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Set$() {

        List<RhoToken> tokens = tokenize("Set$", collector);

        assertThat(tokens, is(asList(SET.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("Set$").row(1).col(4).len(1).offset(3));
    }
}

