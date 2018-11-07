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
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class Floating_point_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0f"},
                {"0F"},
                {"0d"},
                {"0D"},
                //
                {"42f"},
                {"42F"},
                {"42d"},
                {"42D"},
                //
                {"0.0"},
                {"0.0f"},
                {"0.0F"},
                {"0.0d"},
                {"0.0D"},
                //
                {"42.42"},
                {"42.42f"},
                {"42.42F"},
                {"42.42d"},
                {"42.42D"},
                //
                {"1."},
                {"1.f"},
                {"1.F"},
                {"1.d"},
                {"1.D"},
                //
                {".1"},
                {".1f"},
                {".1F"},
                {".1d"},
                {".1D"},
                //
                {".42"},
                {".42f"},
                {".42F"},
                {".42d"},
                {".42D"},
                //
                {"42."},
                {"42.f"},
                {"42.F"},
                {"42.d"},
                {"42.D"},
                //
                {"1e1"},
                {"1e1f"},
                {"1e1F"},
                {"1e1d"},
                {"1e1D"},
                //
                {"1e+1"},
                {"1e+1f"},
                {"1e+1F"},
                {"1e+1d"},
                {"1e+1D"},
                //
                {"1e-1"},
                {"1e-1f"},
                {"1e-1F"},
                {"1e-1d"},
                {"1e-1D"},
                //
                {"1.1e1"},
                {"1.1e1f"},
                {"1.1e1F"},
                {"1.1e1d"},
                {"1.1e1D"},
                //
                {"1.1e+1"},
                {"1.1e+1f"},
                {"1.1e+1F"},
                {"1.1e+1d"},
                {"1.1e+1D"},
                //
                {"1.1e-1"},
                {"1.1e-1f"},
                {"1.1e-1F"},
                {"1.1e-1d"},
                {"1.1e-1D"},
                //
                {".1e1"},
                {".1e1f"},
                {".1e1F"},
                {".1e1d"},
                {".1e1D"},
                //
                {".1e+1"},
                {".1e+1f"},
                {".1e+1F"},
                {".1e+1d"},
                {".1e+1D"},
                //
                {".1e-1"},
                {".1e-1f"},
                {".1e-1F"},
                {".1e-1d"},
                {".1e-1D"},
                //
                {"1.e1"},
                {"1.e1f"},
                {"1.e1F"},
                {"1.e1d"},
                {"1.e1D"},
                //
                {"1.e+1"},
                {"1.e+1f"},
                {"1.e+1F"},
                {"1.e+1d"},
                {"1.e+1D"},
                //
                {"1.e-1"},
                {"1.e-1f"},
                {"1.e-1F"},
                {"1.e-1d"},
                {"1.e-1D"},
        });
    }

    @Parameterized.Parameter(0)
    public String input;

    @Test
    public void test() {

        List<RhoToken> tokens = tokenize(input, collector);
        assertThat(tokens, is(asList(ERROR.T(input), EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error("lexer.err.literal.absent.floating-point")
                        .line(input).row(1).col(1).len(input.length()).offset(0));
    }
}

