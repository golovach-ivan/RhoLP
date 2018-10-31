# RhoLP
[RhoLang](https://github.com/rchain/rchain/tree/dev/rholang) Lexer/Parser, copiler/interpreter frontend with excellent error messages.

Based on OpenJDk [javac 6](http://hg.openjdk.java.net/jdk6/jdk6/langtools/file/72a2f02b7355/src/share/classes/com/sun) deep refactoring.

### Example/Demo
```java
import net.golovach.rholp.*;
import net.golovach.rholp.log.*;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        String content =
                "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";
        DiagnosticListener listener = new DiagnosticCollapsedPrinter();
        RhoLexer lexer = new RhoLexer(content, listener);
        List<RhoTokenType> tokens = lexer.scanAll();
    }
}
```
```
NOTE
  Error code: lexer.note.identifier-like-absent-keyword
  Message: identifier 'type' like absent keyword, may cause confusion
  Line/Column: [1, 1]
  ----------
  type T = Functor[({ type λ[α] = Map[Int, α] })#λ]
  ^^^^

ERROR
  Error code: lexer.err.non-existent.unicode.identifiers
  Messages:
    there is no Unicode support: 'λ', codepoint = 955, char[] = '\u03BB'
    there is no Unicode support: 'α', codepoint = 945, char[] = '\u03B1'
  Line/Column: [1, 26], [1, 28], [1, 42], [1, 48]
  ----------
  type T = Functor[({ type λ[α] = Map[Int, α] })#λ]
                           ^ ^             ^     ^ 
ERROR
  Error code: lexer.err.non-existent.operator
  Message:    there is no operator '#'
  Line/Column: [1, 47]
  ----------
  type T = Functor[({ type λ[α] = Map[Int, α] })#λ]
                                                ^ 
```

### Lexer/Scanner (javac 6)
```java
import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.Token;
import com.sun.tools.javac.util.Context;

public class LexerDemo {

    static final String content = "class MyClass {int x = 9999999999}";

    public static void main(String[] args) {

        Context context = new Context();
        Scanner scanner = Scanner.Factory.instance(context).newScanner(content);

        do {
            scanner.nextToken();
            System.out.println(scanner.token() + " / " + scanner.stringVal());
        } while (scanner.token() != Token.EOF);
    }
}
```
```
>> CLASS / class
>> IDENTIFIER / MyClass
>> LBRACE / 
>> INT / int
>> IDENTIFIER / x
>> EQ / =
>> INTLITERAL / 9999999999
>> RBRACE / 
>> EOF / 
```

### Parser (javac 6)
```java
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;

import java.io.*;

public class ParserDemo {

    static final String fileName = "MyClass.java";
    static final String content = "class MyClass {int x = 9999999999}";

    public static void main(String[] args) throws IOException {

        Context context = new Context();
        JavacFileManager.preRegister(context);

        Scanner scanner = Scanner.Factory.instance(context)
                .newScanner(content);
        Parser parser = Parser.Factory.instance(context)
                .newParser(scanner, false, false);
        context.get(Log.logKey)
                .useSource(new JavaFileInput(fileName, content));

        JCTree.JCCompilationUnit unit = parser.compilationUnit();

        StringWriter s = new StringWriter();
        new Pretty(s, false).printExpr(unit);
        System.out.println(s.toString());
    }
}
```
```
>> MyClass.java:1: integer number too large: 9999999999
>> class MyClass {int x = 9999999999}
>>                        ^
>> MyClass.java:1: ';' expected
>> class MyClass {int x = 9999999999}
>>                                  ^
>> class MyClass {
>>     int x = (ERROR);
>> }
```
