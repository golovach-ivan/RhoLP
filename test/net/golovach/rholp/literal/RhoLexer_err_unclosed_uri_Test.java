package net.golovach.rholp.literal;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.stringVals;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_unclosed_uri_Test {

    @Test
    public void test_without_env_len_0() {
        String content = "`";
        assertThat(tokenize(content), is(asList(
                ERROR, EOF)));
        assertThat(stringVals(content).get(0), is(equalTo("`")));
    }

    @Test
    public void test_without_env_len_1() {
        String content = "`a";
        assertThat(tokenize(content), is(asList(
                ERROR, EOF)));
        assertThat(stringVals(content).get(0), is(equalTo("`")));
    }

    @Test
    public void test_without_env_len_3() {
        String content = "`abc";
        assertThat(tokenize(content), is(asList(
                ERROR, EOF)));
        assertThat(stringVals(content).get(0), is(equalTo("`")));
    }

    @Test
    public void test_with_env_len_0() {
        String content = "true + ` / 123";
        assertThat(tokenize(content), is(asList(
                TRUE, PLUS, ERROR, EOF)));
        assertThat(stringVals(content).get(2), is(equalTo("`")));
    }

    @Test
    public void test_with_env_len_1() {
        String content = "true + `a / 123";
        assertThat(tokenize(content), is(asList(
                TRUE, PLUS, ERROR, EOF)));
        assertThat(stringVals(content).get(2), is(equalTo("`")));
    }

    @Test
    public void test_with_env_len_3() {
        String content = "true + `abc / 123";
        assertThat(tokenize(content), is(asList(
                TRUE, PLUS, ERROR, EOF)));
        assertThat(stringVals(content).get(2), is(equalTo("`")));
    }

    @Test
    public void test_json() {
        String content = "{`name: `Mike`}";
        assertThat(tokenize(content), is(asList(
                LBRACE, LITERAL_URI, IDENT, ERROR, EOF)));
        assertThat(stringVals(content).get(1), is(equalTo("name: ")));
        assertThat(stringVals(content).get(3), is(equalTo("`")));
    }

    @Test
    public void test_uri() {
        String content = "new stdout(`rho:io:stdout) in {Nil}";
        assertThat(tokenize(content), is(asList(
                NEW, IDENT, LPAREN, ERROR, EOF)));
        assertThat(stringVals(content).get(3), is(equalTo("`")));
    }

    @Test
    public void test_multi_line() {
        String content = "" +
                "new foo in {\n" +
                "  new stdout(`rho:io:stdout) in {Nil}\n" +
                "}";
        assertThat(tokenize(content), is(asList(
                NEW, IDENT, IN, LBRACE, NEW, IDENT, LPAREN, ERROR, RBRACE, EOF)));
        assertThat(stringVals(content).get(7), is(equalTo("`")));
    }
}