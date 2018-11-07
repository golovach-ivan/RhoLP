package net.golovach.rholp.keyword_absent;

import net.golovach.rholp.RhoToken;
import net.golovach.rholp.log.impl.DiagnosticCollector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static net.golovach.rholp.RhoTokenType.EOF;
import static net.golovach.rholp.RhoTokenType.IDENT;
import static net.golovach.rholp.LexerAssertUtils.DiagnosticBuilder.note;
import static net.golovach.rholp.LexerAssertUtils.tokenize;
import static net.golovach.rholp.LexerAssertUtils.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class Keyword_absent_Test {

    final DiagnosticCollector collector = new DiagnosticCollector();

    @Parameters
    public static Collection<Object[]> data() {
        List<String> mutableTmp = new ArrayList<>();

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

        return asList(mutableTmp.stream().map(s -> new String[]{s}).toArray(Object[][]::new));
    }

    @Parameter(0)
    public String identifier;

    @Test
    public void test_plus_eq() {

        List<RhoToken> tokens = tokenize(identifier, collector);

        assertThat(tokens, is(asList(IDENT.T(identifier), EOF.T)));
        verify(collector.getDiagnostics()).eqTo(
                note("lexer.note.identifier.like-absent-keyword")
                        .line(identifier).row(1).col(1).len(identifier).offset(0)
                        .msgArgs(identifier));
    }
}

