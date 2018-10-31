package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Dot_Test {

    @Test
    public void test_one() {
        assertThat(tokenize("."), is(asList(
                DOT, EOF)));
    }

    @Test
    public void test_two() {
        assertThat(tokenize(".."), is(asList(
                ERROR, EOF)));
    }

    @Test
    public void test_three() {
        assertThat(tokenize("..."), is(asList(
                ELLIPSIS, EOF)));
    }

    @Test
    public void test_four() {
        assertThat(tokenize("...."), is(asList(
                ELLIPSIS, DOT, EOF)));
    }

    @Test
    public void test_five() {
        assertThat(tokenize("....."), is(asList(
                ELLIPSIS, ERROR, EOF)));
    }

    @Test
    public void test_six() {
        assertThat(tokenize("......"), is(asList(
                ELLIPSIS, ELLIPSIS, EOF)));
    }
}
