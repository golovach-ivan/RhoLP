package net.golovach.rholp.literal;

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

public class RhoLexer_err_int_with_L_suffix_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_0l() {
        assertThat(tokenize("0l", collector), is(asList(
                ERROR, EOF)));
        assertOneError("0l", collector.getDiagnostics(), 0);
    }

    @Test
    public void test_0L() {
        assertThat(tokenize("0L", collector), is(asList(
                ERROR, EOF)));
        assertOneError("0L", collector.getDiagnostics(), 0);
    }

    @Test
    public void test_42l() {
        assertThat(tokenize("42l", collector), is(asList(
                ERROR, EOF)));
        assertOneError("42l", collector.getDiagnostics(), 0);
    }

    @Test
    public void test_42L() {
        assertThat(tokenize("42L", collector), is(asList(
                ERROR, EOF)));
        assertOneError("42L", collector.getDiagnostics(), 0);
    }

    @Test
    public void test_neg_42l() {
        assertThat(tokenize("-42l", collector), is(asList(
                MINUS, ERROR, EOF)));
        assertOneError("42l", collector.getDiagnostics(), 1);
    }

    @Test
    public void test_neg_42L() {
        assertThat(tokenize("-42L", collector), is(asList(
                MINUS, ERROR, EOF)));
        assertOneError("42L", collector.getDiagnostics(), 1);
    }

    @Test
    public void test_42L_with_context() {
        assertThat(tokenize("100 + 42L + 100", collector), is(asList(
                LITERAL_INT, PLUS, ERROR, PLUS, LITERAL_INT, EOF)));
        assertOneError("42L", collector.getDiagnostics(), 6);
    }

    private void assertOneError(String actual, List<Diagnostic> collected, int pos) {
        assertThat(collected, hasSize(1));
        Diagnostic note = collected.get(0);

        assertThat(note.getKind(), is(Diagnostic.Kind.ERROR));
        assertThat(note.getCode(), is(ERROR_PREFIX + "non-existent.literal.int-with-L-suffix"));

        assertThat(note.getStartPosLine(), is(pos));
        assertThat(note.getLen(), is(actual.length()));

        assertThat(note.getLineNumber(), is(1));
        assertThat(note.getColumnNumber(), is(pos + 1));
    }
}
