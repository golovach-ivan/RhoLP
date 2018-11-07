package net.golovach.rholp.operator;

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

public class BackSlash_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_bs() {

        List<RhoToken> tokens = tokenize("\\", collector);

        assertThat(tokens, is(asList(ERROR.T("\\"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.back-slash")
                        .line("\\").row(1).col(1).len(1).offset(0));
    }

    @Test
    public void test_bs_a() {

        List<RhoToken> tokens = tokenize("\\a", collector);

        assertThat(tokens, is(asList(ERROR.T("\\"), IDENT.T("a"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.back-slash")
                        .line("\\a").row(1).col(1).len(1).offset(0));
    }

    @Test
    public void test_bs_bs() {

        List<RhoToken> tokens = tokenize("\\\\", collector);

        assertThat(tokens, is(asList(ERROR.T("\\"), ERROR.T("\\"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.back-slash")
                        .line("\\\\").row(1).col(1).len(1).offset(0),
                error("lexer.err.operator.absent.back-slash")
                        .line("\\\\").row(1).col(2).len(1).offset(1)
        );
    }

    @Test
    public void test_bs_bs_a() {

        List<RhoToken> tokens = tokenize("\\\\a", collector);

        assertThat(tokens, is(asList(ERROR.T("\\"), ERROR.T("\\"), IDENT.T("a"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.back-slash")
                        .line("\\\\a").row(1).col(1).len(1).offset(0),
                error("lexer.err.operator.absent.back-slash")
                        .line("\\\\a").row(1).col(2).len(1).offset(1)
        );
    }

    @Test
    public void test_disjunction() {

        List<RhoToken> tokens = tokenize("\\/", collector);

        assertThat(tokens, is(asList(DISJUNCTION.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_disjunction_a() {

        List<RhoToken> tokens = tokenize("\\/a", collector);

        assertThat(tokens, is(asList(DISJUNCTION.T, IDENT.T("a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

