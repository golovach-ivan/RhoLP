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

public class Keyword_new_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_new() {

        List<RhoToken> tokens = tokenize("new", collector);

        assertThat(tokens, is(asList(NEW.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_new_() {

        List<RhoToken> tokens = tokenize("new_", collector);

        assertThat(tokens, is(asList(IDENT.T("new_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_newa() {

        List<RhoToken> tokens = tokenize("newa", collector);

        assertThat(tokens, is(asList(IDENT.T("newa"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_new0() {

        List<RhoToken> tokens = tokenize("new0", collector);

        assertThat(tokens, is(asList(IDENT.T("new0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_new_plus() {

        List<RhoToken> tokens = tokenize("new+", collector);

        assertThat(tokens, is(asList(NEW.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_new$() {

        List<RhoToken> tokens = tokenize("new$", collector);

        assertThat(tokens, is(asList(NEW.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("new$").row(1).col(4).len(1).offset(3));
    }
}

