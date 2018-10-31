package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Dollar_Test {

    @Test
    public void test_one() {
        assertThat(tokenize("$"), is(asList(
                ERROR, EOF)));
    }

    @Test
    public void test_two() {
        assertThat(tokenize("$$"), is(asList(
                ERROR, ERROR, EOF)));
    }

    @Test
    public void test_identifier_part() {
        assertThat(tokenize("$some$name"), is(asList(
                ERROR, IDENT, ERROR, IDENT, EOF)));
    }
}
