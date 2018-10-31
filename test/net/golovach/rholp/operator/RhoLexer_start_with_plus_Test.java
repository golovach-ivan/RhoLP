package net.golovach.rholp.operator;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoLexer.ERROR_PREFIX;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * '+'  - PLUS
 * '++' - PLUS_PLUS
 * '+=' - ERROR: non-existent
 */
public class RhoLexer_start_with_plus_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_plus() {
        assertThat(tokenize("+"), is(asList(
                PLUS, EOF)));
    }

    @Test
    public void test_plus_plus() {
        assertThat(tokenize("++"), is(asList(
                PLUS_PLUS, EOF)));
    }

    @Test
    public void test_plus_plus_plus() {
        assertThat(tokenize("+++"), is(asList(
                PLUS_PLUS, PLUS, EOF)));
    }

    @Test
    public void test_plus_plus_plus_plus() {
        assertThat(tokenize("++++"), is(asList(
                PLUS_PLUS, PLUS_PLUS, EOF)));
    }

    @Test
    public void test_plus_div() {
        assertThat(tokenize("+/"), is(asList(
                PLUS, DIV, EOF)));
    }

    @Test
    public void test_plus_quote() {
        assertThat(tokenize("+@"), is(asList(
                PLUS, QUOTE, EOF)));
    }

    @Test
    public void test_plus_div_and() {
        assertThat(tokenize("+/&"), is(asList(
                PLUS, DIV, ERROR, EOF)));
    }

    @Test
    public void test_plus_eq() {
        assertThat(tokenize("+=", collector), is(asList(
                ERROR, EOF)));
        assertError(0, "+=", collector.getDiagnostics());
    }

    @Test
    public void test_plus_eq_minus() {
        assertThat(tokenize("x+=-1", collector), is(asList(
                IDENT, ERROR, MINUS, LITERAL_INT, EOF)));
        assertError(1, "+=", collector.getDiagnostics());
    }

    @Test
    public void test_identifier_part() {
        assertThat(tokenize("x += 1", collector), is(asList(
                IDENT, ERROR, LITERAL_INT, EOF)));
        assertError(2, "+=", collector.getDiagnostics());
    }

    private void assertError(int offset, String incorrectVal, List<Diagnostic> collected) {
        assertThat(collected, hasSize(1));
        Diagnostic note = collected.get(0);

        assertThat(note.getKind(), is(Diagnostic.Kind.ERROR));
        assertThat(note.getCode(), is(ERROR_PREFIX + "non-existent.operator.compound-assignment"));

        assertThat(note.getStartPosLine(), is(offset));
        assertThat(note.getLen(), is(incorrectVal.length()));

        assertThat(note.getLineNumber(), is(1));
        assertThat(note.getColumnNumber(), is(offset + 1));
    }
}
