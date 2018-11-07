package net.golovach.rholp.number;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class Int_hex_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

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

    @Parameterized.Parameter(0)
    public String input;

    @Test
    public void test() {

        List<RhoToken> tokens = tokenize(input, collector);
        assertThat(tokens, is(asList(ERROR.T(input), EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.int-hex-format")
                        .line(input).row(1).col(1).len(input.length()).offset(0));
    }
}

