package net.golovach.rholp.number;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static java.util.Arrays.asList;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Floating_point_fail_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_0() {

        List<RhoToken> tokens = tokenize(".0e", collector);

        assertThat(tokens, is(asList(ERROR.T(".0"), IDENT.T("e"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line(".0e").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_1() {

        List<RhoToken> tokens = tokenize("0.e", collector);

        assertThat(tokens, is(asList(ERROR.T("0."), IDENT.T("e"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line("0.e").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_2() {

        List<RhoToken> tokens = tokenize("0.eD", collector);

        assertThat(tokens, is(asList(ERROR.T("0."), IDENT.T("eD"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line("0.eD").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_3() {

        List<RhoToken> tokens = tokenize("0.e+", collector);

        assertThat(tokens, is(asList(ERROR.T("0."), IDENT.T("e"), PLUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line("0.e+").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_4() {

        List<RhoToken> tokens = tokenize("0.e-", collector);

        assertThat(tokens, is(asList(ERROR.T("0."), IDENT.T("e"), MINUS.T, EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line("0.e-").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_5() {

        List<RhoToken> tokens = tokenize("0.e+D", collector);

        assertThat(tokens, is(asList(
                ERROR.T("0."), IDENT.T("e"), PLUS.T, IDENT.T("D"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line("0.e+D").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_6() {

        List<RhoToken> tokens = tokenize("0.e-D", collector);

        assertThat(tokens, is(asList(
                ERROR.T("0."), IDENT.T("e"), MINUS.T, IDENT.T("D"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line("0.e-D").row(1).col(1).len(2).offset(0));
    }
}