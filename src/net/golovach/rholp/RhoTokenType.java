/*
 * Copyright (c) 1999, 2005, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package net.golovach.rholp;


import java.util.HashMap;
import java.util.Map;

/** An interface that defines codes for Java source tokens
 *  returned from lexical analysis.
 *
 *  https://github.com/rchain/rchain/blob/dev/rholang/src/main/bnfc/rholang_mercury.cf
 */
public enum RhoTokenType {
    EOF,
    ERROR,
    IDENT,
    LITERAL_INT,
    LITERAL_STRING,
    LITERAL_URI,    

    // === Keywords
    IF("if"),
    ELSE("else"),
    MATCH("match"),
    MATCHES("matches"),
    SELECT("select"),
    FOR("for"),
    NEW("new"),
    IN("in"),
    CONTRACT("contract"),

    NOT("not"),
    AND("and"),
    OR("or"),

    BUNDLE_READ_WRITE("bundle"),  //todo: test lexer
    BUNDLE_WRITE("bundle+"),
    BUNDLE_READ("bundle-"),
    BUNDLE_EQUIV("bundle0"),

    // === Literals
    TRUE("true"),
    FALSE("false"),
    NIL("Nil"),

    // === Simple Types
    BOOL("Bool"),
    INT("Int"),
    STRING("String"),
    URI("Uri"),
    BYTEARRAY("ByteArray"),

    // === Compound Types
    SET("Set"),

    // === Separators (punctuators)
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    COMMA(","),
    SEMI(";"),
    DOT("."),
    COLON(":"),
    ELLIPSIS("..."), // todo: separator or operator?

    // === Operators
    PERCENT_PERCENT("%%"),

    TILDE("~"),      // todo: PNegation?
    CONJUNCTION("/\\"),
    DISJUNCTION("\\/"),

    PLUS("+"),
    MINUS("-"),
    STAR("*"), // todo: PMult OR PEval
    DIV("/"),
    PLUS_PLUS("++"),
    MINUS_MINUS("--"),
    GT(">"),
    LT("<"),
    EQ("="),
    EQ_EQ("=="),
    NOT_EQ("!="),
    LTE("<="),
    GTE(">="),

    PAR("|"),
    WILDCARD("_"),
    QUOTE("@"),

    BIND_LINEAR("<-"),
    BIND_REPEATED("<="),
    SEND_SINGLE("!"),
    SEND_MULTIPLE("!!"),
    ARROW("=>"); // todo: BRANCH OR CASE

    RhoTokenType() {
        this(null);
    }
    RhoTokenType(String name) {
        this.name = name;
    }

    public final String name;
    
    private static final Map<String, RhoTokenType> map = new HashMap<>();
    static {
        for (RhoTokenType tokenType: values()) {
            if (tokenType.name != null) {
                map.put(tokenType.name, tokenType);
            }
        }
    }
    public static RhoTokenType select(String body) {
        if (map.containsKey(body)) {
            return map.get(body);
        } else {
            return IDENT; //todo:
        }
    }
}