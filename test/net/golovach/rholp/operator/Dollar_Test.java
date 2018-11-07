package net.golovach.rholp.operator;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Dollar_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_$() {

        List<RhoToken> tokens = tokenize("$", collector);

        assertThat(tokens, is(asList(ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("$").row(1).col(1).len(1).offset(0));
    }

    @Test
    public void test_$_$() {

        List<RhoToken> tokens = tokenize("$$", collector);

        assertThat(tokens, is(asList(ERROR.T("$$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("$$").row(1).col(1).len(2).offset(0));
    }

    @Test
    public void test_$_$_$() {

        List<RhoToken> tokens = tokenize("$$$", collector);

        assertThat(tokens, is(asList(ERROR.T("$$"), ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("$$$").row(1).col(1).len(2).offset(0),
                error("lexer.err.operator.absent")
                        .line("$$$").row(1).col(3).len(1).offset(2)
        );
    }
}

