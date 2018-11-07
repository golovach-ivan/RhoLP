package net.golovach.rholp.operator;

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

public class Quote_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_quote() {

        List<RhoToken> tokens = tokenize("@", collector);

        assertThat(tokens, is(asList(QUOTE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_quote_quote() {

        List<RhoToken> tokens = tokenize("@@", collector);

        assertThat(tokens, is(asList(QUOTE.T, QUOTE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

