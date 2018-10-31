package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Wildcard_Test {

    @Test
    public void test_linear_bind() {
        assertThat(tokenize("for (_ <- _chan) {Nil}"), is(asList(
                FOR, LPAREN, WILDCARD, BIND_LINEAR, IDENT, RPAREN, LBRACE, NIL, RBRACE, EOF)));
    }

    @Test
    public void test_one() {
        assertThat(tokenize("_"), is(asList(
                WILDCARD, EOF)));
    }

    @Test
    public void test_two() {
        assertThat(tokenize("__"), is(asList(
                IDENT, EOF)));
    }

    @Test
    public void test_three() {
        assertThat(tokenize("___"), is(asList(
                IDENT, EOF)));
    }

    @Test
    public void test_identifier_start_with_wildcard() {
        assertThat(tokenize("_a"), is(asList(
                IDENT, EOF)));
    }
}
