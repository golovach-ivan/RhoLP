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
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class Int_L_suffix_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0l", "0l"},
                {"0L", "0L"},
                {"+0l", "0l"},
                {"+0L", "0L"},
                {"-0l", "0l"},
                {"-0L", "0L"},
                //
                {"42l", "42l"},
                {"42L", "42L"},
                {"+42l", "42l"},
                {"+42L", "42L"},
                {"-42l", "42l"},
                {"-42L", "42L"},
        });
    }

    @Parameterized.Parameter(0)
    public String input;

    @Parameterized.Parameter(1)
    public String error;

    @Test
    public void test() {

        List<RhoToken> tokens = tokenize(input, collector);

        final int offset;
        if (input.startsWith("-")) {
            offset = 1;
            assertThat(tokens, is(asList(MINUS.T, ERROR.T(error), EOF.T)));
        } else if (input.startsWith("+")) {
            offset = 1;
            assertThat(tokens, is(asList(PLUS.T, ERROR.T(error), EOF.T)));
        } else {
            offset = 0;
            assertThat(tokens, is(asList(ERROR.T(error), EOF.T)));
        }

        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.int-L-suffix")
                        .line(input).row(1).col(offset + 1).len(error.length()).offset(offset));
    }
}

