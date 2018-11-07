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

public class Keyword_true_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_true() {

        List<RhoToken> tokens = tokenize("true", collector);

        assertThat(tokens, is(asList(TRUE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_true_() {

        List<RhoToken> tokens = tokenize("true_", collector);

        assertThat(tokens, is(asList(IDENT.T("true_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_truea() {

        List<RhoToken> tokens = tokenize("truea", collector);

        assertThat(tokens, is(asList(IDENT.T("truea"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_true0() {

        List<RhoToken> tokens = tokenize("true0", collector);

        assertThat(tokens, is(asList(IDENT.T("true0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_true_plus() {

        List<RhoToken> tokens = tokenize("true+", collector);

        assertThat(tokens, is(asList(TRUE.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_true$() {

        List<RhoToken> tokens = tokenize("true$", collector);

        assertThat(tokens, is(asList(TRUE.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("true$").row(1).col(5).len(1).offset(4));
    }
}

