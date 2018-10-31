package net.golovach.rholp.literal;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoLexer.ERROR_PREFIX;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class RhoLexer_err_int_in_hex_format_vectorized_Test {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0x0"},
                {"0x9"},
                {"0xa"},
                {"0xb"},
                {"0xc"},
                {"0xd"},
                {"0xe"},
                {"0xf"},
                {"0xA"},
                {"0xB"},
                {"0xC"},
                {"0xD"},
                {"0xE"},
                {"0xF"},
                {"0x123456789abcdef"},
                {"0x123456789ABCDEF"},
                //
                {"0X0"},
                {"0X9"},
                {"0Xa"},
                {"0Xb"},
                {"0Xc"},
                {"0Xd"},
                {"0Xe"},
                {"0Xf"},
                {"0XA"},
                {"0XB"},
                {"0XC"},
                {"0XD"},
                {"0XE"},
                {"0XF"},
                {"0X123456789abcdef"},
                {"0X123456789ABCDEF"},
        });
    }

    @Parameter(0)
    public String input;

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test() {
        assertThat(tokenize(input, collector), is(asList(
                ERROR, EOF)));
        assertError(input, collector.getDiagnostics(), 0);
    }

    private void assertError(String actual, List<Diagnostic> collected, int pos) {
        assertThat(collected, hasSize(1));
        Diagnostic note = collected.get(0);

        assertThat(note.getKind(), is(Diagnostic.Kind.ERROR));
        assertThat(note.getCode(), is(ERROR_PREFIX + "non-existent.literal.int-in-hex-format"));

        assertThat(note.getStartPosLine(), is(pos));
        assertThat(note.getLen(), is(actual.length()));

        assertThat(note.getLineNumber(), is(1));
        assertThat(note.getColumnNumber(), is(pos + 1));
    }
}

