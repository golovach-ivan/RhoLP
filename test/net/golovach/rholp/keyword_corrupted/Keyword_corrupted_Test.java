package net.golovach.rholp.keyword_corrupted;

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

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.*;
import static net.golovach.rholp.AssertUtils.DiagnosticBuilder.warn;
import static net.golovach.rholp.AssertUtils.tokenize;
import static net.golovach.rholp.AssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class Keyword_corrupted_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
                {"nil", "Nil"},
                {"NIL", "Nil"},
                {"nill", "Nil"},
                {"Nill", "Nil"},
                {"NILL", "Nil"},
                {"null", "Nil"},
                {"Null", "Nil"},
                {"NULL", "Nil"},
                {"nul", "Nil"},
                {"Nul", "Nil"},
                {"NUL", "Nil"},
                //
                {"For", "for"},
                {"FOR", "FOR"},
                //
                {"New", "new"},
                {"NEW", "new"},
                //
                {"In", "in"},
                {"IN", "in"},
                //
                {"Contract", "contract"},
                {"CONTRACT", "contract"},
                //
                {"If", "if"},
                {"IF", "if"},
                //
                {"Else", "else"},
                {"ELSE", "else"},
                //
                {"True", "true"},
                {"TRUE", "true"},
                //
                {"False", "false"},
                {"FALSE", "false"},
                //
                {"Not", "not"},
                {"NOT", "not"},
                //
                {"And", "and"},
                {"AND", "and"},
                //
                {"Or", "or"},
                {"OR", "or"},
                //
                {"Match", "match"},
                {"MATCH", "match"},
                //
                {"Matches", "matches"},
                {"MATCHES", "matches"},
                //
                {"Select", "select"},
                {"SELECT", "select"},
                //
                {"bool", "Bool"},
                {"BOOL", "Bool"},
                //
                {"int", "Int"},
                {"INT", "Int"},
                //
                {"string", "String"},
                {"STRING", "String"},
                //
                {"uri", "Uri"},
                {"URI", "Uri"},
                //
                {"byteArray", "ByteArray"},
                {"Bytearray", "ByteArray"},
                {"bytearray", "ByteArray"},
                {"BYTEARRAY", "ByteArray"},
                //
                {"Byte_Array", "ByteArray"},
                {"byte_Array", "ByteArray"},
                {"Byte_array", "ByteArray"},
                {"byte_array", "ByteArray"},
                {"BYTE_ARRAY", "ByteArray"},
                //
                {"set", "Set"},
                {"SET", "Set"},
        });
    }

    @Parameter(0)
    public String actualInput;

    @Parameter(1)
    public String correctedInput;

    @Test
    public void test_plus_eq() {

        List<RhoToken> tokens = tokenize(actualInput, collector);

        assertThat(tokens, is(asList(IDENT.T(actualInput), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                warn("lexer.warn.identifier.like-existing-keyword")
                        .line(actualInput).row(1).col(1).len(actualInput).offset(0)
                        .msgArgs(actualInput, correctedInput));
    }
}

