package net.golovach.rholp;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticCollector;
import net.golovach.rholp.log.DiagnosticListener;

import java.util.ArrayList;
import java.util.List;

import static net.golovach.rholp.RhoLexer.*;
import static net.golovach.rholp.log.Diagnostic.Kind.ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * <pre>
 *  import static AssertUtil.tokenize;
 *  import static AssertUtil.assertSingleError;
 *     
 *  DiagnosticCollector collector = new DiagnosticCollector();
 *    ...
 *  List<RhoToken> tokens = tokenize("123 + 0x456 + 789", collector);
 *
 *  assertSingleError("non-existent.literal.int-in-hex-format")
 *      .start(6).len("0x456")
 *      .in(collector);
 * </pre>
 */
public class AssertUtil {

    public static Builder assertSingleError(String code) {
        return new Builder().kind(ERROR).code(code).diagnosticsCount(1);
    }

    public static List<RhoTokenType> tokenize(String content) {
        return tokenize(new RhoLexer(content));
    }

    public static List<RhoTokenType> tokenize(String content, DiagnosticListener listener) {
        return tokenize(new RhoLexer(content, listener));
    }

    private static List<RhoTokenType> tokenize(RhoLexer lexer) {
        List<RhoTokenType> result = new ArrayList<RhoTokenType>();
        do {
            lexer.nextToken();
            result.add(lexer.token());
        } while (lexer.token() != RhoTokenType.EOF);
        return result;
    }

    public static List<String> stringVals(String content) { // todo: rename
        RhoLexer lexer = new RhoLexer(content);
        List<String> result = new ArrayList<String>();
        do {
            lexer.nextToken();
            result.add(lexer.stringVal());
        } while (lexer.token() != RhoTokenType.EOF);
        return result;
    }

    public static class Builder {
        private int diagnosticsCount = -1;
        //
        private Diagnostic.Kind kind;
        private String line;
        private int startPosition = -1;
        private int length = -1;
        private int lineNumber = -1;
        private int columnNumber = -1;
        private String code;
        private String message;

        public Builder diagnosticsCount(int diagnosticsCount) {
            this.diagnosticsCount = diagnosticsCount;
            return this;
        }

        public Builder kind(Diagnostic.Kind kind) {
            this.kind = kind;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder line(String line) {
            this.line = line;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder start(int startPosition) {
            this.startPosition = startPosition;
            return this;
        }

        public Builder len(String str) {
            this.length = str.length();
            return this;
        }

        public Builder column(int columnNumber) {
            this.columnNumber = columnNumber;
            return this;
        }

        public void in(DiagnosticCollector collector) {
            assertEq(collector, 0);
        }

        public void assertEq(DiagnosticCollector collector, int index) {
            Diagnostic diagnostic = collector.getDiagnostics().get(index);

            if (diagnosticsCount == -1) {
                throw new IllegalStateException();
            }

            if (kind == null || code == null || startPosition == -1 || length == -1) {
                throw new IllegalStateException();
            }

            // diagnosticsCount
            assertThat(collector.getDiagnostics().size(), is(diagnosticsCount));

            // kind
            assertThat(diagnostic.getKind(), is(kind));

            // code
            switch (kind) {
                case ERROR:
                    assertThat(diagnostic.getCode(), is(ERROR_PREFIX + code));
                    break;
                case WARN:
                    assertThat(diagnostic.getCode(), is(WARN_PREFIX + code));
                    break;
                case NOTE:
                    assertThat(diagnostic.getCode(), is(NOTE_PREFIX + code));
                    break;
            }

            // startPos
            assertThat(diagnostic.getStartPosLine(), is(startPosition));

            // endPos
            assertThat(diagnostic.getLen(), is(length));


            if (line != null) {
                assertThat(diagnostic.getLine(), is(line));
            }

            if (message != null) {
                assertThat(diagnostic.getMessage(), is(message));
            }
            if (lineNumber != -1) {
                assertThat(diagnostic.getLineNumber(), is(lineNumber));
            }
            if (columnNumber != -1) {
                assertThat(diagnostic.getColumnNumber(), is(columnNumber));
            }
        }
    }
}

