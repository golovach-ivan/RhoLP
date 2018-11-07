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

public class Keyword_select_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_select() {

        List<RhoToken> tokens = tokenize("select", collector);

        assertThat(tokens, is(asList(SELECT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_select_() {

        List<RhoToken> tokens = tokenize("select_", collector);

        assertThat(tokens, is(asList(IDENT.T("select_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_selecta() {

        List<RhoToken> tokens = tokenize("selecta", collector);

        assertThat(tokens, is(asList(IDENT.T("selecta"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_select0() {

        List<RhoToken> tokens = tokenize("select0", collector);

        assertThat(tokens, is(asList(IDENT.T("select0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_select_plus() {

        List<RhoToken> tokens = tokenize("select+", collector);

        assertThat(tokens, is(asList(SELECT.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_select$() {

        List<RhoToken> tokens = tokenize("select$", collector);

        assertThat(tokens, is(asList(SELECT.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("select$").row(1).col(7).len(1).offset(6));
    }
}

