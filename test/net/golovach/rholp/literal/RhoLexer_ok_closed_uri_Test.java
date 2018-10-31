package net.golovach.rholp.literal;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.stringVals;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RhoLexer_ok_closed_uri_Test {

    @Test
    public void test_without_env_len_0() {
        String content = "``";
        assertThat(tokenize(content), is(asList(
                LITERAL_URI, EOF)));
        assertThat(stringVals(content).get(0), is(equalTo("")));
    }

    @Test
    public void test_without_env_len_1() {
        String content = "`a`";
        assertThat(tokenize(content), is(asList(
                LITERAL_URI, EOF)));
        assertThat(stringVals(content).get(0), is(equalTo("a")));
    }

    @Test
    public void test_without_env_len_3() {
        String content = "`abc`";
        assertThat(tokenize(content), is(asList(
                LITERAL_URI, EOF)));
        assertThat(stringVals(content).get(0), is(equalTo("abc")));
    }

    @Test
    public void test_with_env_len_0() {
        String content = "true + `` / 123";
        assertThat(tokenize(content), is(asList(
                TRUE, PLUS, LITERAL_URI, DIV, LITERAL_INT, EOF)));
        assertThat(stringVals(content).get(2), is(equalTo("")));
    }

    @Test
    public void test_with_env_len_1() {
        String content = "true + `a` / 123";
        assertThat(tokenize(content), is(asList(
                TRUE, PLUS, LITERAL_URI, DIV, LITERAL_INT, EOF)));
        assertThat(stringVals(content).get(2), is(equalTo("a")));
    }

    @Test
    public void test_with_env_len_3() {
        String content = "true + `abc` / 123";
        assertThat(tokenize(content), is(asList(
                TRUE, PLUS, LITERAL_URI, DIV, LITERAL_INT, EOF)));
        assertThat(stringVals(content).get(2), is(equalTo("abc")));
    }

    @Test
    public void test_json() {
        String content = "{`name`: `Mike`}";
        assertThat(tokenize(content), is(asList(
                LBRACE, LITERAL_URI, COLON, LITERAL_URI, RBRACE, EOF)));
        assertThat(stringVals(content).get(1), is(equalTo("name")));
        assertThat(stringVals(content).get(3), is(equalTo("Mike")));
    }

    @Test
    public void test_uri() {
        String content = "new stdout(`rho:io:stdout`) in {Nil}";
        assertThat(tokenize(content), is(asList(
                NEW, IDENT, LPAREN, LITERAL_URI, RPAREN, IN, LBRACE, NIL, RBRACE, EOF)));
        assertThat(stringVals(content).get(3), is(equalTo("rho:io:stdout")));
    }
}
