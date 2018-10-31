package net.golovach.rholp.note;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticCollector;
import org.hamcrest.core.StringContains;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.AssertUtil.tokenize;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.IDENT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class RhoLexer_typo_in_keyword_Test {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
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
                {"Bundle", "bundle'/'bundle+'/'bundle-'/'bundle0"},
                {"BUNDLE", "bundle'/'bundle+'/'bundle-'/'bundle0"},
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
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
//                {"?", "?"},
        });
    }

    @Parameter(0)
    public String actualInput;

    @Parameter(1)
    public String maybeInput;

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test() {
        assertThat(tokenize(actualInput, collector), is(asList(
                IDENT, EOF)));
        assertNote(actualInput, maybeInput, collector.getDiagnostics());
    }

    private void assertNote(String actual, String maybe, List<Diagnostic> collected) {
        assertThat(collected, hasSize(1));
        Diagnostic note = collected.get(0);

        assertThat(note.getKind(), is(Diagnostic.Kind.NOTE));
        assertThat(note.getCode(), is("ident-like-keyword"));

        assertThat(note.getStartPosLine(), is(0));
        assertThat(note.getLen(), is(actual.length()));

        assertThat(note.getLineNumber(), is(1));
        assertThat(note.getColumnNumber(), is(1));

        assertThat(note.getMessage(), StringContains.containsString("'" + actual + "'"));
        assertThat(note.getMessage(), StringContains.containsString("'" + maybe + "'"));
    }
}
