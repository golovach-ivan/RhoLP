package net.golovach.rholp.keyword;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class Keyword_contract_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_contract() {

        List<RhoToken> tokens = tokenize("contract", collector);

        assertThat(tokens, is(asList(CONTRACT.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_contract_() {

        List<RhoToken> tokens = tokenize("contract_", collector);

        assertThat(tokens, is(asList(IDENT.T("contract_"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_contracta() {

        List<RhoToken> tokens = tokenize("contracta", collector);

        assertThat(tokens, is(asList(IDENT.T("contracta"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_contract0() {

        List<RhoToken> tokens = tokenize("contract0", collector);

        assertThat(tokens, is(asList(IDENT.T("contract0"), EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_contract_plus() {

        List<RhoToken> tokens = tokenize("contract+", collector);

        assertThat(tokens, is(asList(CONTRACT.T, PLUS.T, EOF.T)));
        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }

    @Test
    public void test_contract$() {

        List<RhoToken> tokens = tokenize("contract$", collector);

        assertThat(tokens, is(asList(CONTRACT.T, ERROR.T("$"), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.operator.absent")
                        .line("contract$").row(1).col(9).len(1).offset(8));
    }
}

