package net.golovach.rholp.comment;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Div_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_div() {

        List<RhoToken> tokens = tokenize("/", collector);

        assertThat(tokens, is(asList(DIV.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_div_bs() {

        List<RhoToken> tokens = tokenize("/\\", collector);

        assertThat(tokens, is(asList(CONJUNCTION.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div_bs_a() {

        List<RhoToken> tokens = tokenize("a/\\b", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), CONJUNCTION.T, IDENT.T("b"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_div_a() {

        List<RhoToken> tokens = tokenize("/a", collector);

        assertThat(tokens, is(asList(DIV.T, IDENT.T("a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div() {

        List<RhoToken> tokens = tokenize("a/", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), DIV.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div_a() {

        List<RhoToken> tokens = tokenize("a/b", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), DIV.T, IDENT.T("b"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

