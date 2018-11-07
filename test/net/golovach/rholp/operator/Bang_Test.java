package net.golovach.rholp.operator;

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

public class Bang_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_bang() {

        List<RhoToken> tokens = tokenize("!", collector);

        assertThat(tokens, is(asList(SEND_SINGLE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bang_bang() {

        List<RhoToken> tokens = tokenize("!!", collector);

        assertThat(tokens, is(asList(SEND_MULTIPLE.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_bang_eq() {

        List<RhoToken> tokens = tokenize("!=", collector);

        assertThat(tokens, is(asList(NOT_EQ.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

