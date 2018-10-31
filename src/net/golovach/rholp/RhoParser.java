package net.golovach.rholp;

import net.golovach.rholp.log.Diagnostic;
import net.golovach.rholp.log.DiagnosticListener;
import net.golovach.rholp.tree.RhoTree.*;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;

public class RhoParser {
    private final RhoLexer lexer;
    private final DiagnosticListener listener;

    private final ResourceBundle msgBundle;

    public RhoParser(RhoLexer lexer, DiagnosticListener listener) {
        this.lexer = lexer;
        this.listener = listener;

        this.msgBundle = getBundle("parser");
    }

    public RhoProcessTree process() {
        lexer.nextToken();
        RhoTokenType token = lexer.token();

        switch (token) {
            case LPAREN:
                RhoProcessTree internal = process();
                lexer.nextToken();
                if (lexer.token() == RhoTokenType.RPAREN) {
                    return new RhoParensProcessTree(internal);
                } else {
                    syntaxErr("unclosed-parens");
                    return new RhoErroneousProcessTree();
                }
            case NIL:
                // todo: no! greedy!
                return new RhoNilTree();
            default:
                throw new Error("<<unknown>>");
        }

    }

    public RhoChannelTree channel() {
        throw new Error();
    }

    /**
     * Report an error at the current token position using the provided
     * arguments.
     */
    private void syntaxErr(String key, Object... args) {

        if (this.listener != null) {
            this.listener.report(new Diagnostic(
                    Diagnostic.Kind.ERROR,
                    null,
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    key,
                    MessageFormat.format(msgBundle.getString("compiler.err." + key), args)
            ));
        }
    }
}
