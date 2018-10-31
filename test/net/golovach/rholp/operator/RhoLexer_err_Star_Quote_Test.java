package net.golovach.rholp.operator;

import org.junit.Test;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RhoLexer_err_Star_Quote_Test {

    @Test
    public void test() {
        assertThat(tokenize("*@*@*@0"), is(asList(
                STAR, QUOTE, STAR, QUOTE, STAR, QUOTE, LITERAL_INT, EOF)));
    }
}
