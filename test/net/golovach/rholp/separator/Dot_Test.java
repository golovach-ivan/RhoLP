package net.golovach.rholp.separator;

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

public class Dot_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test1() {

        List<RhoToken> tokens = tokenize(".", collector);

        assertThat(tokens, is(asList(DOT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test2() {

        List<RhoToken> tokens = tokenize("..", collector);

        assertThat(tokens, is(asList(ERROR.T(".."), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.dot-dot")
                        .line("..").row(1).col(1).len(2).offset(0)
        );
    }

    @Test
    public void test3() {

        List<RhoToken> tokens = tokenize("...", collector);

        assertThat(tokens, is(asList(ELLIPSIS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test4() {

        List<RhoToken> tokens = tokenize("....", collector);

        assertThat(tokens, is(asList(ELLIPSIS.T, DOT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test5() {

        List<RhoToken> tokens = tokenize(".....", collector);

        assertThat(tokens, is(asList(ELLIPSIS.T, ERROR.T(".."), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent.dot-dot")
                        .line(".....").row(1).col(4).len(2).offset(3));
    }
}

