package net.golovach.rholp.default_section;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.ERROR;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.error;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Illegal_ASCII_Test {
    static final String CODE = "lexer.err.codepoint.illegal.ascii";

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Test
    public void test_1() {

        List<RhoToken> tokens = tokenize("\u0000", collector);

        assertThat(tokens, is(asList(
                ERROR.T("\u0000"), EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("\u0000").row(1).len(1).col(1).offset(0)
        );
    }

    @Test
    public void test_2_close() {

        List<RhoToken> tokens = tokenize("\u0000\u0001", collector);

        assertThat(tokens, is(asList(
                ERROR.T("\u0000"), ERROR.T("\u0001"), EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("\u0000\u0001").row(1).len(1).col(1).offset(0),
                error(CODE).line("\u0000\u0001").row(1).len(1).col(2).offset(1)
        );
    }

    @Test
    public void test_2_separated() {

        List<RhoToken> tokens = tokenize("\u0000 \u0001", collector);

        assertThat(tokens, is(asList(
                ERROR.T("\u0000"), ERROR.T("\u0001"), EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).line("\u0000 \u0001").row(1).len(1).col(1).offset(0),
                error(CODE).line("\u0000 \u0001").row(1).len(1).col(3).offset(2)
        );
    }

    @Test
    public void test_all() {

        // without: \\u0009 == \t
        // without: \\u000A == \n
        // without: \\u000C == \f
        // without: \\u000D == \r

        List<RhoToken> tokens = tokenize("" +
                "\u0000\u0001\u0002\u0003" +
                "\u0004\u0005\u0006\u0007" +
                "\u0008" +
//                "\t" +
//                "\n" +
                "\u000B" +
//                "\f" +
//                "\r" +
                "\u000E\u000F" +
                "\u0010\u0011\u0012\u0013" +
                "\u0014\u0015\u0016\u0017" +
                "\u0018\u0019\u001A\u001B" +
                "\u001C\u001D\u001E\u001F" +
                "", collector);

        assertThat(tokens, is(asList(
                ERROR.T("\u0000"), ERROR.T("\u0001"),
                ERROR.T("\u0002"), ERROR.T("\u0003"),
                ERROR.T("\u0004"), ERROR.T("\u0005"),
                ERROR.T("\u0006"), ERROR.T("\u0007"),
                ERROR.T("\u0008"),
//                ERROR.T("\t"),
//                ERROR.T("\n"),
                ERROR.T("\u000B"),
//                ERROR.T("\f"),
//                ERROR.T("\r"),
                ERROR.T("\u000E"), ERROR.T("\u000F"),
                ERROR.T("\u0010"), ERROR.T("\u0011"),
                ERROR.T("\u0012"), ERROR.T("\u0013"),
                ERROR.T("\u0014"), ERROR.T("\u0015"),
                ERROR.T("\u0016"), ERROR.T("\u0017"),
                ERROR.T("\u0018"), ERROR.T("\u0019"),
                ERROR.T("\u001A"), ERROR.T("\u001B"),
                ERROR.T("\u001C"), ERROR.T("\u001D"),
                ERROR.T("\u001E"), ERROR.T("\u001F"),
                EOF.T)));

        verify(collector.getDiagnostics()).eqTo(
                error(CODE).row(1).len(1).col(1).offset(0),
                error(CODE).row(1).len(1).col(2).offset(1),
                error(CODE).row(1).len(1).col(3).offset(2),
                error(CODE).row(1).len(1).col(4).offset(3),
                error(CODE).row(1).len(1).col(5).offset(4),
                error(CODE).row(1).len(1).col(6).offset(5),
                error(CODE).row(1).len(1).col(7).offset(6),
                error(CODE).row(1).len(1).col(8).offset(7),
                error(CODE).row(1).len(1).col(9).offset(8),
                error(CODE).row(1).len(1).col(10).offset(9),
                error(CODE).row(1).len(1).col(11).offset(10),
                error(CODE).row(1).len(1).col(12).offset(11),
                error(CODE).row(1).len(1).col(13).offset(12),
                error(CODE).row(1).len(1).col(14).offset(13),
                error(CODE).row(1).len(1).col(15).offset(14),
                error(CODE).row(1).len(1).col(16).offset(15),
                error(CODE).row(1).len(1).col(17).offset(16),
                error(CODE).row(1).len(1).col(18).offset(17),
                error(CODE).row(1).len(1).col(19).offset(18),
                error(CODE).row(1).len(1).col(20).offset(19),
                error(CODE).row(1).len(1).col(21).offset(20),
                error(CODE).row(1).len(1).col(22).offset(21),
                error(CODE).row(1).len(1).col(23).offset(22),
                error(CODE).row(1).len(1).col(24).offset(23),
                error(CODE).row(1).len(1).col(25).offset(24),
                error(CODE).row(1).len(1).col(26).offset(25),
                error(CODE).row(1).len(1).col(27).offset(26),
                error(CODE).row(1).len(1).col(28).offset(27)
        );
    }
}

