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

public class Keyword_Int_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_Int() {

        List<RhoToken> tokens = tokenize("Int", collector);

        assertThat(tokens, is(asList(INT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Int_() {

        List<RhoToken> tokens = tokenize("Int_", collector);

        assertThat(tokens, is(asList(IDENT.T("Int_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Inta() {

        List<RhoToken> tokens = tokenize("Inta", collector);

        assertThat(tokens, is(asList(IDENT.T("Inta"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Int0() {

        List<RhoToken> tokens = tokenize("Int0", collector);

        assertThat(tokens, is(asList(IDENT.T("Int0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Int_plus() {

        List<RhoToken> tokens = tokenize("Int+", collector);

        assertThat(tokens, is(asList(INT.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Int$() {

        List<RhoToken> tokens = tokenize("Int$", collector);

        assertThat(tokens, is(asList(INT.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("Int$").row(1).col(4).len(1).offset(3));
    }
}

