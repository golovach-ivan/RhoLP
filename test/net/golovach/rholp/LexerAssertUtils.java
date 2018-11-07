package net.golovach.rholp;

import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LexerAssertUtils {

    public static List<RhoToken> tokenize(String content, DiagnosticListener listener) {
        return new RhoLexer(content, listener).readAll();
    }

    public static DiagnosticBuilder.EqTo verify(List<Diagnostic> diagnostics) {
        return new DiagnosticBuilder.EqTo(diagnostics);
    }

    public static class DiagnosticBuilder {
        public static class EqTo {
            private final List<Diagnostic> diagnostics;

            public EqTo(List<Diagnostic> diagnostics) {
                this.diagnostics = diagnostics;
            }

            public void eqTo(DiagnosticBuilder... builders) {
                assertThat(diagnostics.size(), is(builders.length));
                for (int k = 0; k < builders.length; k++) {
                    Diagnostic actual = diagnostics.get(k);
                    DiagnosticBuilder expected = builders[k];

                    // kind
                    if (expected.kind != null) {
                        assertThat(actual.kind, is(expected.kind));
                    }

                    // code
                    if (expected.code != null) {
                        assertThat(actual.code, is(expected.code));
                    }

                    // msg
                    if (expected.msg != null) {
                        assertThat(actual.msg, is(expected.msg));
                    }

                    // line
                    if (expected.line != null) {
                        assertThat(actual.line, is(expected.line));
                    }

                    // row
                    if (expected.row != -1) {
                        assertThat(actual.row, is(expected.row));
                    }

                    // len
                    if (expected.len != -1) {
                        assertThat(actual.len, is(expected.len));
                    }

                    // col
                    if (expected.col != -1) {
                        assertThat(actual.col, is(expected.col));
                    }

                    // offset
                    if (expected.offset != -1) {
                        assertThat(actual.offset, is(expected.offset));
                    }
                }
            }
        }

        public Diagnostic.Kind kind;
        public String code;
        public String msg;
        public String[] msgArgs;
        //
        public String line;
        public int col = -1;
        public int len = -1;
        //
        public int row = -1;
        public int offset = -1;

        public static DiagnosticBuilder note(String code) {
            return new DiagnosticBuilder().kind(Diagnostic.Kind.NOTE).code(code);
        }

        public static DiagnosticBuilder warn(String code) {
            return new DiagnosticBuilder().kind(Diagnostic.Kind.WARN).code(code);
        }

        public static DiagnosticBuilder error(String code) {
            return new DiagnosticBuilder().kind(Diagnostic.Kind.ERROR).code(code);
        }

        public DiagnosticBuilder kind(Diagnostic.Kind kind) {
            this.kind = kind;
            return this;
        }

        public DiagnosticBuilder code(String code) {
            this.code = code;
            return this;
        }

        public DiagnosticBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public DiagnosticBuilder msgArgs(String... msgArgs) {
            this.msgArgs = msgArgs;
            return this;
        }

        public DiagnosticBuilder line(String line) {
            this.line = line;
            return this;
        }

        public DiagnosticBuilder col(int col) {
            this.col = col;
            return this;
        }

        public DiagnosticBuilder len(int len) {
            this.len = len;
            return this;
        }

        public DiagnosticBuilder len(String str) {
            return len(str.length());
        }

        public DiagnosticBuilder row(int row) {
            this.row = row;
            return this;
        }

        public DiagnosticBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }
    }
}
