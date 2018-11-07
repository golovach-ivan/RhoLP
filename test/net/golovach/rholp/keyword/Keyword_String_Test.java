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

public class Keyword_String_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_String() {

        List<RhoToken> tokens = tokenize("String", collector);

        assertThat(tokens, is(asList(STRING.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_String_() {

        List<RhoToken> tokens = tokenize("String_", collector);

        assertThat(tokens, is(asList(IDENT.T("String_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_Stringa() {

        List<RhoToken> tokens = tokenize("Stringa", collector);

        assertThat(tokens, is(asList(IDENT.T("Stringa"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_String0() {

        List<RhoToken> tokens = tokenize("String0", collector);

        assertThat(tokens, is(asList(IDENT.T("String0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_String_plus() {

        List<RhoToken> tokens = tokenize("String+", collector);

        assertThat(tokens, is(asList(STRING.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_String$() {

        List<RhoToken> tokens = tokenize("String$", collector);

        assertThat(tokens, is(asList(STRING.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("String$").row(1).col(7).len(1).offset(6));
    }
}

