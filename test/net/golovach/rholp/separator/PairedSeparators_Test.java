package net.golovach.rholp.separator;

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

// todo: rename
public class PairedSeparators_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test() {

        List<RhoToken> tokens = tokenize("()[]{},;", collector);

        assertThat(tokens, is(asList(
                LPAREN.T, RPAREN.T,
                LBRACKET.T, RBRACKET.T,
                LBRACE.T, RBRACE.T,
                COMMA.T,
                SEMI.T,
                EOF.T)));

        assertThat(collector.getDiagnostics(), hasSize(is(0)));
    }
}

