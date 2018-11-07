package net.golovach.rholp.warn;

import java.util.HashMap;
import java.util.Map;

//todo: rename, load from file
public class KeywordTypo {
    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("nil", "Nil");
        map.put("NIL", "Nil");
        map.put("nill", "Nil");
        map.put("Nill", "Nil");
        map.put("NILL", "Nil");
        map.put("null", "Nil");
        map.put("Null", "Nil");
        map.put("NULL", "Nil");
        map.put("nul", "Nil");
        map.put("Nul", "Nil");
        map.put("NUL", "Nil");
        //
        map.put("Bundle", "bundle");
        map.put("BUNDLE", "bundle");
        map.put("Bundle0", "bundle0");
        map.put("BUNDLE0", "bundle0");
        //
        map.put("For", "for");
        map.put("FOR", "FOR");
        //
        map.put("New", "new");
        map.put("NEW", "new");
        //
        map.put("In", "in");
        map.put("IN", "in");
        //
        map.put("Contract", "contract");
        map.put("CONTRACT", "contract");
        //
        map.put("If", "if");
        map.put("IF", "if");
        //
        map.put("Else", "else");
        map.put("ELSE", "else");
        //
        map.put("True", "true");
        map.put("TRUE", "true");
        //
        map.put("False", "false");
        map.put("FALSE", "false");
        //
        map.put("Not", "not");
        map.put("NOT", "not");
        //
        map.put("And", "and");
        map.put("AND", "and");
        //
        map.put("Or", "or");
        map.put("OR", "or");
        //
        map.put("Match", "match");
        map.put("MATCH", "match");
        //
        map.put("Matches", "matches");
        map.put("MATCHES", "matches");
        //
        map.put("Select", "select");
        map.put("SELECT", "select");
        //
        map.put("bool", "Bool");
        map.put("BOOL", "Bool");
        //
        map.put("int", "Int");
        map.put("INT", "Int");
        //
        map.put("string", "String");
        map.put("STRING", "String");
        //
        map.put("uri", "Uri");
        map.put("URI", "Uri");
        //
        map.put("byteArray", "ByteArray");
        map.put("Bytearray", "ByteArray");
        map.put("bytearray", "ByteArray");
        map.put("BYTEARRAY", "ByteArray");
        //
        map.put("Byte_Array", "ByteArray");
        map.put("byte_Array", "ByteArray");
        map.put("Byte_array", "ByteArray");
        map.put("byte_array", "ByteArray");
        map.put("BYTE_ARRAY", "ByteArray");
        //
        map.put("set", "Set");
        map.put("SET", "Set");
    }

    public static String tryCorrect(String actual) {
        return map.get(actual);
    }
}
