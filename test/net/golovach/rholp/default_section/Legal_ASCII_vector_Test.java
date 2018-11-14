package net.golovach.rholp.default_section;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

import static java.lang.Character.toChars;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class Legal_ASCII_vector_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Parameters
    public static Collection<Object[]> data() {
        return range(32, 127).boxed()
                .map(codepoint -> new Object[]{new String(toChars(codepoint))})
                .collect(toList());
    }

    @Parameter(0)
    public String input;

    @Test
    public void test() {

        List<RhoToken> tokens = tokenize(input, collector);

        assertThat(tokens, is(not((
                tokens.size() == 2)
                && (tokens.get(0).type == ERROR)
                && (collector.getDiagnostics().get(0).code.startsWith("lexer.err.codepoint.")))));
    }
}

