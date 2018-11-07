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

public class Keyword_or_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_or() {

        List<RhoToken> tokens = tokenize("or", collector);

        assertThat(tokens, is(asList(OR.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_or_() {

        List<RhoToken> tokens = tokenize("or_", collector);

        assertThat(tokens, is(asList(IDENT.T("or_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ora() {

        List<RhoToken> tokens = tokenize("ora", collector);

        assertThat(tokens, is(asList(IDENT.T("ora"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_or0() {

        List<RhoToken> tokens = tokenize("or0", collector);

        assertThat(tokens, is(asList(IDENT.T("or0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_or_plus() {

        List<RhoToken> tokens = tokenize("or+", collector);

        assertThat(tokens, is(asList(OR.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_or$() {

        List<RhoToken> tokens = tokenize("or$", collector);

        assertThat(tokens, is(asList(OR.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("or$").row(1).col(3).len(1).offset(2));
    }
}

