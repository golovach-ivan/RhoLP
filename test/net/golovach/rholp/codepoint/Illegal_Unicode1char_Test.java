package net.golovach.rholp.codepoint;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Illegal_Unicode1char_Test {
    static final String CODE = "lexer.err.codepoint.illegal.unicode-1-char";

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_1() {

        List<RhoToken> tokens = tokenize("α", collector);

        assertThat(tokens, is(asList(
                ERROR.token("α"), EOF.token())));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("α").row(1).len(1).col(1).offset(0)
        );
    }

    @Test
    public void test_2_close() {

        List<RhoToken> tokens = tokenize("αβ", collector);

        assertThat(tokens, is(asList(
                ERROR.token("α"), ERROR.token("β"), EOF.token())));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("αβ").row(1).len(1).col(1).offset(0),
                error(CODE).line("αβ").row(1).len(1).col(2).offset(1)
        );
    }

    @Test
    public void test_2_separated() {

        List<RhoToken> tokens = tokenize("α β", collector);

        assertThat(tokens, is(asList(
                ERROR.token("α"), ERROR.token("β"), EOF.token())));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("α β").row(1).len(1).col(1).offset(0),
                error(CODE).line("α β").row(1).len(1).col(3).offset(2)
        );
    }

    @Test
    public void test_6_multiLine() {

        List<RhoToken> tokens = tokenize("α\nβγ\r\nδϵζ\n\n", collector);

        assertThat(tokens, is(asList(
                ERROR.token("α"), ERROR.token("β"), ERROR.token("γ"),
                ERROR.token("δ"), ERROR.token("ϵ"), ERROR.token("ζ"),
                EOF.token())));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("α\n").row(1).len(1).col(1).offset(0),
                error(CODE).line("βγ\r\n").row(2).len(1).col(1).offset(2),
                error(CODE).line("βγ\r\n").row(2).len(1).col(2).offset(3),
                error(CODE).line("δϵζ\n").row(3).len(1).col(1).offset(6),
                error(CODE).line("δϵζ\n").row(3).len(1).col(2).offset(7),
                error(CODE).line("δϵζ\n").row(3).len(1).col(3).offset(8)
        );
    }
}

