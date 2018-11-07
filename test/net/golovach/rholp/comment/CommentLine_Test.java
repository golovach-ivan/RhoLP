package net.golovach.rholp.comment;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.AssertUtils.tokenize;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CommentLine_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_div_div() {

        List<RhoToken> tokens = tokenize("//", collector);

        assertThat(tokens, is(asList(EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_div_div_a() {

        List<RhoToken> tokens = tokenize("//a", collector);

        assertThat(tokens, is(asList(EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div_div() {

        List<RhoToken> tokens = tokenize("a//", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div_div_a() {

        List<RhoToken> tokens = tokenize("a//b", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_div_div_a_LF_a_div_div_a() {

        List<RhoToken> tokens = tokenize("a//b\nc//d\r\n", collector);

        assertThat(tokens, is(asList(IDENT.T("a"), IDENT.T("c"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

