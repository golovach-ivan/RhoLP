package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Question_Test {

    @Test
    public void test_one() {
        assertThat(tokenize("?"), is(asList(
                ERROR, EOF)));
    }

    @Test
    public void test_two() {
        assertThat(tokenize("??"), is(asList(
                ERROR, ERROR, EOF)));
    }

    @Test
    public void test_ternary_operator() {
        assertThat(tokenize("1 > 0 ? true : false"), is(asList(
                LITERAL_INT, GT, LITERAL_INT, ERROR, TRUE, COLON, FALSE, EOF)));
    }
}
