package net.golovach.rholp.tree;

public interface RhoTree {

    /**
     * Accept method used to implement the visitor pattern.  The
     * visitor pattern is used to implement operations on trees.
     *
     * @param <R> result type of this operation.
     * @param <D> type of additional data.
     */
    <R,D> R accept(RhoTreeVisitor<R,D> visitor, D data);

    public interface RhoProcessTree extends RhoTree {}

    public class RhoErroneousProcessTree implements RhoProcessTree {

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitErroneous(this, data);
        }

        public String toString() {
            return "<ERROR>";
        }
    }

    // (...process...)
    public class RhoParensProcessTree implements RhoProcessTree {
        private final RhoProcessTree tree;

        public RhoParensProcessTree(RhoProcessTree tree) {
            this.tree = tree;
        }

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitParens(this, data);
        }

        public String toString() {
            return '(' + tree.toString() +')';
        }
    }

    public interface RhoChannelTree extends RhoTree {}

    // Nil
    public class RhoNilTree implements RhoProcessTree {

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitNil(this, data);
        }
        
        public String toString() {
            return "Nil";
        }
    }

    // new ?, ?, ? in {...}
    public class RhoNewTree implements RhoProcessTree {
//    private final List<RhoChannelTree> channels; //todo:

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitNew(this, data);
        }
    }

    // @
    public class RhoQuoteTree implements RhoChannelTree {

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitQuote(this, data);
        }
    }

    // *
    public class RhoEvalTree implements RhoProcessTree {

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitEval(this, data);
        }
    }

    public interface RhoSendTree extends RhoProcessTree {}

    // !
    public class RhoSingleSendTree implements RhoSendTree {

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitSingleSend(this, data);
        }
    }

    // !!
    public class RhoMultipleSendTree implements RhoSendTree {

        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitMultipleSend(this, data);
        }
    }

    public interface RhoReceiveTree extends RhoProcessTree {}

// todo: receive

    // (Int | String | Uri | ByteArray) | (Tuple | Array | Set | Map)
    public interface RhoDataProcessTree extends RhoProcessTree {}

    // Int | String | Uri | ByteArray
    public interface RhoSimpleDataProcessTree extends RhoDataProcessTree {}

    // Int
    public class RhoIntLiteralTree implements RhoSimpleDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitIntLiteral(this, data);
        }
    }

    // String
    public class RhoStringLiteralTree implements RhoSimpleDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitStringLiteral(this, data);
        }
    }

    // Uri
    public class RhoUriLiteralTree implements RhoSimpleDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitUriLiteral(this, data);
        }
    }

    // ByteArray
    public class RhoByteArrayLiteralTree implements RhoSimpleDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitByteArrayLiteral(this, data);
        }
    }

    // (Tuple | Array | Set | Map)
    public interface RhoCollectionDataProcessTree extends RhoDataProcessTree {}

    // Tuple
    public class RhoTupleLiteralTree implements RhoCollectionDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitTupleLiteral(this, data);
        }
    }

    // List
    public class RhoListLiteralTree implements RhoCollectionDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitListLiteral(this, data);
        }
    }

    // Set
    public class RhoSetLiteralTree implements RhoCollectionDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitSetLiteral(this, data);
        }
    }

    // Map
    public class RhoMapLiteralTree implements RhoCollectionDataProcessTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitMapLiteral(this, data);
        }
    }

    // Binary operations: '|' and arithmetic('+', '-', ..) and
    public interface RhoBinaryTree extends RhoProcessTree {}

    // Parallel composition: '|'
    public class RhoParTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitPar(this, data);
        }
    }

    // Arithmetic: '+'
    public class RhoAddTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitAdd(this, data);
        }
    }

    // Arithmetic: '-'
    public class RhoSubTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitSub(this, data);
        }
    }

    // Arithmetic: '*'
    public class RhoMulTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitMul(this, data);
        }
    }

    // Arithmetic: '/'
    public class RhoDivTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitDiv(this, data);
        }
    }

    // Boolean: 'and'
    public class RhoAndTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitAnd(this, data);
        }
    }

    // Boolean: 'or'
    public class RhoOrTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitOr(this, data);
        }
    }

    // Boolean: 'not'
    public class RhoNotTree implements RhoBinaryTree {
        @Override
        public <R, D> R accept(RhoTreeVisitor<R, D> visitor, D data) {
            return visitor.visitNot(this, data);
        }
    }

// unary '-'

// if

// match

// matches

// String interpolation
}

