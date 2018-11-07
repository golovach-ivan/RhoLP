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

public class Keyword_ByteArray_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_ByteArray() {

        List<RhoToken> tokens = tokenize("ByteArray", collector);

        assertThat(tokens, is(asList(BYTE_ARRAY.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ByteArray_() {

        List<RhoToken> tokens = tokenize("ByteArray_", collector);

        assertThat(tokens, is(asList(IDENT.T("ByteArray_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ByteArraya() {

        List<RhoToken> tokens = tokenize("ByteArraya", collector);

        assertThat(tokens, is(asList(IDENT.T("ByteArraya"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ByteArray0() {

        List<RhoToken> tokens = tokenize("ByteArray0", collector);

        assertThat(tokens, is(asList(IDENT.T("ByteArray0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ByteArray_plus() {

        List<RhoToken> tokens = tokenize("ByteArray+", collector);

        assertThat(tokens, is(asList(BYTE_ARRAY.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_ByteArray$() {

        List<RhoToken> tokens = tokenize("ByteArray$", collector);

        assertThat(tokens, is(asList(BYTE_ARRAY.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("ByteArray$").row(1).col(10).len(1).offset(9));
    }
}

