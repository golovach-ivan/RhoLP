package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Percent_Test {

    @Test
    public void test_one() {
        assertThat(tokenize("%"), is(asList(
                ERROR, EOF)));
    }

    @Test
    public void test_two() {
        assertThat(tokenize("%%"), is(asList(
                PERCENT_PERCENT, EOF)));
    }

    @Test
    public void test_three() {
        assertThat(tokenize("%%%"), is(asList(
                PERCENT_PERCENT, ERROR, EOF)));
    }

    @Test
    public void test_four() {
        assertThat(tokenize("%%%%"), is(asList(
                PERCENT_PERCENT, PERCENT_PERCENT, EOF)));
    }

    @Test
    public void test_five() {
        assertThat(tokenize("%%%%%"), is(asList(
                PERCENT_PERCENT, PERCENT_PERCENT, ERROR, EOF)));
    }

    @Test
    public void test_complex() {
        assertThat(tokenize("7 % 3"), is(asList(
                LITERAL_INT, ERROR, LITERAL_INT, EOF)));
    }
}
