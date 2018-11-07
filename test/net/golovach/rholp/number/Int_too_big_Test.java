package net.golovach.rholp.number;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Int_too_big_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_MAX_VALUE() {

        String content = "" + Long.MAX_VALUE;
        List<RhoToken> tokens = tokenize(content, collector);

        assertThat(tokens, is(asList(LITERAL_INT.T(content), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_MIN_VALUE() {

        String content = "" + Long.MIN_VALUE;
        List<RhoToken> tokens = tokenize(content, collector);

        assertThat(tokens, is(asList(MINUS.T, LITERAL_INT.T(content.substring(1)), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_MAX_VALUE_add_one() {

        String content = valueOf(Long.MAX_VALUE).add(ONE).toString();
        List<RhoToken> tokens = tokenize(content, collector);

        assertThat(tokens, is(asList(ERROR.T(content), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.int-too-big")
                        .line(content).row(1).col(1).len(content).offset(0));
    }

    @Test
    public void test_MIN_VALUE_sub_one() {

        String content = valueOf(Long.MIN_VALUE).subtract(ONE).toString();
        List<RhoToken> tokens = tokenize(content, collector);

        assertThat(tokens, is(asList(MINUS.T, ERROR.T(content.substring(1)), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.int-too-big")
                        .line(content).row(1).col(2).len(content.substring(1)).offset(1));
    }
}