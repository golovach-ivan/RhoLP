package net.golovach.rholp.literal;

import net.golovach.rholp.RhoTokenType;
import net.golovach.rholp.log.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.assertSingleError;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_int_in_hex_format_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test() {
        List<RhoTokenType> tokens = tokenize("123 + 0x456 + 789", collector);

        assertThat(tokens, is(asList(
                LITERAL_INT, PLUS, ERROR, PLUS, LITERAL_INT, EOF)));
        assertSingleError("non-existent.literal.int-in-hex-format")
                .start(6).len("0x456")
                .in(collector);
    }
}

