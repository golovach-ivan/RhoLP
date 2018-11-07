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

public class Keyword_false_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_false() {

        List<RhoToken> tokens = tokenize("false", collector);

        assertThat(tokens, is(asList(FALSE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_false_() {

        List<RhoToken> tokens = tokenize("false_", collector);

        assertThat(tokens, is(asList(IDENT.T("false_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_falsea() {

        List<RhoToken> tokens = tokenize("falsea", collector);

        assertThat(tokens, is(asList(IDENT.T("falsea"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_false0() {

        List<RhoToken> tokens = tokenize("false0", collector);

        assertThat(tokens, is(asList(IDENT.T("false0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_false_plus() {

        List<RhoToken> tokens = tokenize("false+", collector);

        assertThat(tokens, is(asList(FALSE.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_false$() {

        List<RhoToken> tokens = tokenize("false$", collector);

        assertThat(tokens, is(asList(FALSE.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("false$").row(1).col(6).len(1).offset(5));
    }
}

