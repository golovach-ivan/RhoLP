package net.golovach.rholp;

import java.util.HashMap;
import java.util.Map;

// todo: move to txt-file
public class RhoLexerNoteUtil {

    private static final String PREFIX = "lexer.note";

    private static Map<String, String> typoMap = new HashMap<>();
    static {
        typoMap.put("nil", "Nil");
        typoMap.put("NIL", "Nil");
        typoMap.put("nill", "Nil");
        typoMap.put("Nill", "Nil");
        typoMap.put("NILL", "Nil");
        typoMap.put("null", "Nil");
        typoMap.put("Null", "Nil");
        typoMap.put("NULL", "Nil");
        typoMap.put("nul", "Nil");
        typoMap.put("Nul", "Nil");
        typoMap.put("NUL", "Nil");
        //
        typoMap.put("Bundle", "bundle'/'bundle+'/'bundle-'/'bundle0");
        typoMap.put("BUNDLE", "bundle'/'bundle+'/'bundle-'/'bundle0");
        //
        typoMap.put("For", "for");
        typoMap.put("FOR", "FOR");
        //
        typoMap.put("New", "new");
        typoMap.put("NEW", "new");
        //
        typoMap.put("In", "in");
        typoMap.put("IN", "in");
        //
        typoMap.put("Contract", "contract");
        typoMap.put("CONTRACT", "contract");
        //
        typoMap.put("If", "if");
        typoMap.put("IF", "if");
        //
        typoMap.put("Else", "else");
        typoMap.put("ELSE", "else");
        //
        typoMap.put("True", "true");
        typoMap.put("TRUE", "true");
        //
        typoMap.put("False", "false");
        typoMap.put("FALSE", "false");
        //
        typoMap.put("Not", "not");
        typoMap.put("NOT", "not");
        //
        typoMap.put("And", "and");
        typoMap.put("AND", "and");
        //
        typoMap.put("Or", "or");
        typoMap.put("OR", "or");
        //
        typoMap.put("Match", "match");
        typoMap.put("MATCH", "match");
        //
        typoMap.put("Matches", "matches");
        typoMap.put("MATCHES", "matches");
        //
        typoMap.put("Select", "select");
        typoMap.put("SELECT", "select");
    }

//    public static void checkTypoInKeyword(String ident,
//                                          DiagnosticListener<? super RhoFileObject> diagnosticListener,
//                                          char[] buf,
//                                          int bufLen,
//                                          int pos,
//                                          int sp,
//                                          RhoPosition.LineMap lineMap,
//                                          ResourceBundle msgBundle) {
//
//        if (typoMap.containsKey(ident)) {
//            diagnosticListener.report(new RhoDiagnostic(
//                    Diagnostic.Kind.NOTE,
//                    new RhoFileObject(buf, bufLen),
//                    pos,
//                    pos, // todo
//                    pos + sp, // todo
//                    lineMap.getLineNumber(pos),
//                    lineMap.getColumnNumber(pos),
//                    "ident-like-keyword",
//                    MessageFormat.format(msgBundle.getString(PREFIX + "." + "ident-like-keyword"), ident, typoMap.get(ident))
//            ));
//        }
//    }
}
