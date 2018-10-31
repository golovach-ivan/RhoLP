package net.golovach.rholp.literal;

import net.golovach.rholp.log.DiagnosticCollector;
import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.assertSingleError;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_closed_single_quote_Test {
    static final String ERROR_CODE = "non-existent.literal.single-quote";

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_single_char() {
        assertThat(tokenize("'a'", collector), is(asList(
                ERROR, EOF)));
        assertSingleError(ERROR_CODE)
                .start(0).len("'a'").in(collector);
    }

    @Test
    public void test_many_chars() {
        assertThat(tokenize("'abc'", collector), is(asList(
                ERROR, EOF)));
        assertSingleError(ERROR_CODE)
                .start(0).len("'abc'").in(collector);
    }

    @Test
    public void test_json() {
        assertThat(tokenize("{\"name\": 'Mike'}", collector), is(asList(
                LBRACE, LITERAL_STRING, COLON, ERROR, RBRACE, EOF)));
        assertSingleError(ERROR_CODE)
                .start(9).len("'Mike'").in(collector);
    }

    @Test
    public void test_uri() {
        assertThat(tokenize("new stdout('rho:io:stdout') in {Nil}", collector), is(asList(
                NEW, IDENT, LPAREN, ERROR, RPAREN, IN, LBRACE, NIL, RBRACE, EOF)));
        assertSingleError(ERROR_CODE)
                .start(11).len("'rho:io:stdout'").in(collector);
    }
}
