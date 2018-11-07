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

public class Keyword_if_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_if() {

        List<RhoToken> tokens = tokenize("if", collector);

        assertThat(tokens, is(asList(IF.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_if_() {

        List<RhoToken> tokens = tokenize("if_", collector);

        assertThat(tokens, is(asList(IDENT.T("if_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ifa() {

        List<RhoToken> tokens = tokenize("ifa", collector);

        assertThat(tokens, is(asList(IDENT.T("ifa"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_if0() {

        List<RhoToken> tokens = tokenize("if0", collector);

        assertThat(tokens, is(asList(IDENT.T("if0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_if_plus() {

        List<RhoToken> tokens = tokenize("if+", collector);

        assertThat(tokens, is(asList(IF.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_if$() {

        List<RhoToken> tokens = tokenize("if$", collector);

        assertThat(tokens, is(asList(IF.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("if$").row(1).col(3).len(1).offset(2));
    }
}

