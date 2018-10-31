package net.golovach.rholp.tree;

import net.golovach.rholp.tree.RhoTree.*;

/**
 * A visitor of trees, in the style of the visitor design pattern.
 * Classes implementing this interface are used to operate
 * on a tree when the kind of tree is unknown at compile time.
 * When a visitor is passed to an tree's {@link net.golovach.rholp.tree.RhoTree#accept
 * accept} method, the <tt>visit<i>XYZ</i></tt> method most applicable
 * to that tree is invoked.

 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter. 
 */
public interface RhoTreeVisitor<R,P> {
    R visitErroneous(RhoErroneousProcessTree node, P p);
    // (...)
    R visitParens(RhoParensProcessTree node, P p);

    // Nil
    R visitNil(RhoTree.RhoNilTree node, P p);

    // '|'
    R visitPar(RhoTree.RhoParTree node, P p);

    // new
    R visitNew(RhoNewTree node, P p);

    R visitEval(RhoEvalTree node, P p);
    R visitQuote(RhoQuoteTree node, P p);

    // Send: !, !!
    R visitSingleSend(RhoSingleSendTree node, P p);
    R visitMultipleSend(RhoMultipleSendTree node, P p);

    // Simple literals
    R visitIntLiteral(RhoIntLiteralTree node, P p);
    R visitStringLiteral(RhoStringLiteralTree node, P p);
    R visitUriLiteral(RhoUriLiteralTree node, P p);
    R visitByteArrayLiteral(RhoByteArrayLiteralTree node, P p);

    // Collection literals
    R visitTupleLiteral(RhoTupleLiteralTree node, P p);
    R visitListLiteral(RhoListLiteralTree node, P p);
    R visitSetLiteral(RhoSetLiteralTree node, P p);
    R visitMapLiteral(RhoMapLiteralTree node, P p);

    // Arithmetic
    R visitAdd(RhoAddTree node, P p);
    R visitSub(RhoSubTree node, P p);
    R visitMul(RhoMulTree node, P p);
    R visitDiv(RhoDivTree node, P p);

    // Boolean
    R visitAnd(RhoAndTree node, P p);
    R visitOr(RhoOrTree node, P p);
    R visitNot(RhoNotTree node, P p);
}
