package net.golovach.rholp.under;

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

public class Under_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_() {

        List<RhoToken> tokens = tokenize("_", collector);

        assertThat(tokens, is(asList(WILDCARD.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test__() {

        List<RhoToken> tokens = tokenize("__", collector);

        assertThat(tokens, is(asList(IDENT.T("__"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a() {

        List<RhoToken> tokens = tokenize("_a", collector);

        assertThat(tokens, is(asList(IDENT.T("_a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_a_a() {

        List<RhoToken> tokens = tokenize("a_a", collector);

        assertThat(tokens, is(asList(IDENT.T("a_a"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

