package net.golovach.rholp.default_section;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static java.lang.Character.toChars;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Codepoints_from_128_to_65535_Test {

    @Test
    public void test() {

        Random rnd = new Random(0);

        // 1000 random chars in range [128, 65.535]
        for (int k = 0; k < 1000; k ++) {
            int codepoint = (1 << 7) + rnd.nextInt((1 << 16) - (1 << 7));

            final DiagnosticCollector collector = new DiagnosticCollector();

            String content = new String(toChars(codepoint));
            List<RhoToken> tokens = tokenize(content, collector);

            assertThat(tokens, is(asList(ERROR.T(content), EOF.T)));
            if (Character.isDefined(codepoint)) {
                verify(collector.getDiagnostics()).eqTo(
                        error("lexer.err.codepoint.illegal.unicode-1-char")
                                .line(content).row(1).len(1).col(1).offset(0)
                );
            } else {
                verify(collector.getDiagnostics()).eqTo(
                        error("lexer.err.codepoint.illegal.undefined")
                                .line(content).row(1).len(1).col(1).offset(0)
                );
            }
        }
    }
}

