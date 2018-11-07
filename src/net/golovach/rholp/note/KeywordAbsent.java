package net.golovach.rholp.note;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

//todo: rename, load from file
public class KeywordAbsent {
    private static final Set<String> absent;

    static {
        Set<String> mutableTmp = new HashSet<>();
        // 2 letters
        mutableTmp.addAll(asList("as", "of", "do", "eq"));
        mutableTmp.addAll(asList("As", "Of", "Do", "Eq"));
        mutableTmp.addAll(asList("AS", "OF", "DO", "EQ"));
        // 3 letters
        mutableTmp.addAll(asList("url", "div", "mul", "add", "sub", "pow", "mod", "neg", "xml", "def", "let", "end", "map"));
        mutableTmp.addAll(asList("Url", "Div", "Mul", "Add", "Sub", "Pow", "Mod", "Neg", "Xml", "Def", "Let", "End", "Map"));
        mutableTmp.addAll(asList("URL", "DIV", "MUL", "ADD", "SUB", "POW", "MOD", "NEG", "XML", "DEF", "let", "END", "MAP"));
        // 4 letters
        mutableTmp.addAll(asList("this", "self", "json", "type", "byte", "char", "case", "loop", "send", "list", "long"));
        mutableTmp.addAll(asList("This", "Self", "Json", "Type", "Byte", "Char", "Case", "Loop", "Send", "List", "Long"));
        mutableTmp.addAll(asList("THIS", "SELF", "JSON", "TYPE", "BYTE", "CHAR", "CASE", "LOOP", "SEND", "LIST", "LONG"));
        // 5 letters
        mutableTmp.addAll(asList("class", "throw", "super", "until", "yield", "where", "begin", "break", "short", "float", "array", "while"));
        mutableTmp.addAll(asList("Class", "Throw", "Super", "Until", "Yield", "Where", "Begin", "Break", "Short", "Float", "Array", "While"));
        mutableTmp.addAll(asList("CLASS", "THROW", "SUPER", "UNTIL", "YIELD", "WHERE", "BEGIN", "BREAK", "SHORT", "FLOAT", "ARRAY", "WHILE"));
        // 6 letters
        mutableTmp.addAll(asList("object", "public", "throws", "module", "scheme", "import", "double", "return", "forall"));
        mutableTmp.addAll(asList("Object", "Public", "Throws", "Module", "Scheme", "Import", "double", "Return", "Forall"));
        mutableTmp.addAll(asList("OBJECT", "PUBLIC", "THROWS", "MODULE", "SCHEME", "IMPORT", "double", "RETURN", "FORALL"));
        // 7 letters
        mutableTmp.addAll(asList("private", "default", "integer", "defined", "newtype", "receive", "extends", "boolean"));
        mutableTmp.addAll(asList("Private", "Default", "Integer", "Defined", "Newtype", "Receive", "Extends", "Boolean"));
        mutableTmp.addAll(asList("PRIVATE", "DEFAULT", "INTEGER", "DEFINED", "NEWTYPE", "RECEIVE", "EXTENDS", "BOOLEAN"));
        // 8 letters
        mutableTmp.addAll(asList("function", "property", "continue"));
        mutableTmp.addAll(asList("Function", "Property", "Continue"));
        mutableTmp.addAll(asList("FUNCTION", "PROPERTY", "CONTINUE"));
        // 9 letters
        mutableTmp.addAll(asList("protected", "character"));
        mutableTmp.addAll(asList("Protected", "Character"));
        mutableTmp.addAll(asList("PROTECTED", "CHARACTER"));
        // 10 letters
        mutableTmp.addAll(asList("instanceof", "implements"));
        mutableTmp.addAll(asList("Instanceof", "Implements"));
        mutableTmp.addAll(asList("INSTANCEOF", "IMPLEMENTS"));

        absent = unmodifiableSet(mutableTmp);
    }

    public static boolean contains(String actual) {
        return absent.contains(actual);
    }
}
