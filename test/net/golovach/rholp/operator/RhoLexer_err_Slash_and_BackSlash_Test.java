package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Slash_and_BackSlash_Test {

    @Test
    public void test_slash() {
        assertThat(tokenize("/"), is(asList(
                DIV, EOF)));
    }

    @Test
    public void test_backslash() {
        assertThat(tokenize("\\"), is(asList(
                ERROR, EOF)));
    }

    @Test
    public void test_conjunction() {
        assertThat(tokenize("/\\"), is(asList(
                CONJUNCTION, EOF)));
    }

    @Test
    public void test_disjunction() {
        assertThat(tokenize("\\/"), is(asList(
                DISJUNCTION, EOF)));
    }

    @Test
    public void test_complex() {
        assertThat(tokenize("/\\/\\\\/\\\\///\\"), is(asList(
                CONJUNCTION, CONJUNCTION, DISJUNCTION, ERROR, DISJUNCTION, EOF)));
    }
//
//    @Test
//    public void test_0() {
//        assertThat(tokenize("\\"), is(equalTo(asList(ERROR, EOF))));
//    }
}
