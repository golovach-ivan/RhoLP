package net.golovach.rholp.default_section;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class Illegal_ASCII_vector_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"\u0000"},
                {"\u0001"},
                {"\u0002"},
                {"\u0003"},
                {"\u0004"},
                {"\u0005"},
                {"\u0006"},
                {"\u0007"},
                {"\u0008"},
//                {"\t"},
//                {"\n"},
                {"\u000B"},
//                {"\f"},
//                {"\r"},
                {"\u000E"},
                {"\u000F"},
                //
                {"\u0010"},
                {"\u0011"},
                {"\u0012"},
                {"\u0013"},
                {"\u0014"},
                {"\u0015"},
                {"\u0016"},
                {"\u0017"},
                {"\u0018"},
                {"\u0019"},
                {"\u001A"},
                {"\u001B"},
                {"\u001C"},
                {"\u001D"},
                {"\u001E"},
                {"\u001F"},
                //
                {"\u007F"},
        });
    }

    @Parameter(0)
    public String input;

    @Test
    public void test() {

        List<RhoToken> tokens = tokenize(input, collector);
        assertThat(tokens, is(asList(ERROR.T(input), EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.codepoint.illegal.ascii")
                        .line(input).row(1).col(1).len(1).offset(0));
    }
}

