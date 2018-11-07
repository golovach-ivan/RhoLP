# RhoLP
[RhoLang](https://github.com/rchain/rchain/tree/dev/rholang) Lexer/Parser, compiler/interpreter frontend with excellent error messages.

- [Demo](#demo)
- [Main classes](#main-classes)
  - [RhoTokenType, RhoToken, RhoLexer](#rhotokentype-rhotoken-rholexer)
  - [Diagnostic, DiagnosticListener](#diagnostic-diagnosticlistener)
- [Error types](#error-types)
  - [Numbers](#numbers)
  - [String-like](#string-like)
  - [Absent operators/keywords, misspelled keywords](#absent-operatorskeywords-misspelled-keywords)

## Demo
```java
import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.*;
import net.golovach.rholp.log.impl.*;

public class Demo {
    public static void main(String[] args) {
        String content =
                "type T = Functor[({ type λ[α] = Map[Int, α] })#λ]";
        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer(content, listener);

        lexer.readAll();
    }
}
```
```
NOTE
  Error code: lexer.note.identifier.like-absent-keyword
  Message:    identifier 'type' like absent keyword/type
  Line/Column: [1, 1], [1, 21], [1, 33]
  ----------
  type T = Functor[({ type λ[α] = Map[Int, α] })#λ]
  ^^^^                ^^^^        ^^^              
ERROR
  Error code: lexer.err.codepoint.illegal.unicode-1-char
  Message:    illegal 1-char unicode symbol: ''{0}'', hex = {1}, codepoint = {2}
  Line/Column: [1, 26], [1, 28], [1, 42], [1, 48]
  ----------
  type T = Functor[({ type λ[α] = Map[Int, α] })#λ]
                           ^ ^             ^     ^ 
ERROR
  Error code: lexer.err.operator.absent
  Message:    there is no '#' operator
  Line/Column: [1, 47]
  ----------
  type T = Functor[({ type λ[α] = Map[Int, α] })#λ]
```

## Main classes

### RhoTokenType, RhoToken, RhoLexer
```java
public enum RhoTokenType {
    IF, ELSE, MATCH, EOF, ERROR, IDENT, LITERAL_INT, LITERAL_STRING, LITERAL_URI, ...
    
    public final RhoToken T;
    public RhoToken T(String chars) {...}
}
```
```java
public class RhoToken {
    public final RhoTokenType type;
    public final String val;
    ...
}
```
```java
public class RhoLexer {
    public RhoLexer(String content) {...}
    public RhoLexer(String content, DiagnosticListener listener) {...}
    public RhoLexer(String content, DiagnosticListener listener, Properties messages) {...}
    
    public RhoToken readToken() {...}
    public List<RhoToken> readAll() {...}
}
```

**Example**
```java
List<RhoToken> tokens = asList(
        NEW.T, IDENT.T("chan"), LBRACE.T, NIL.T, RBRACE.T, EOF.T);
```
Is the same as
```java
RhoLexer lexer = new RhoLexer("new chan in { Nil }");
List<RhoToken> tokens = lexer.readAll();
```

### Diagnostic, DiagnosticListener
*Diagnostic* - plain ADT with error/warn/note info
```java
public class Diagnostic {
    public static enum Kind { NOTE, WARN, ERROR }
    
    public final Kind kind;
    public final String code;
    public final String msg;
    public final String msgTemplate;
    public final List<String> msgArgs;
    //
    public final String line;
    public final int col;
    public final int len;
    //
    public final int row;
    public final int offset;
    ...
}
```

*DiagnosticListener* - interface for receiving diagnostics from tools
```java
public interface DiagnosticListener {
    void report(Diagnostic diagnostic);
    void eof();
}
```

## Error types

### Numbers
```java
import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.*;
import net.golovach.rholp.log.impl.*;

public class Demo {
    public static void main(String[] args) {
        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer("[42L, 9999999999999999999, 1.1e-1f]", listener);
        lexer.readAll();
    }
}
```
```
ERROR
  Error code: lexer.err.literal.absent.int-L-suffix
  Message:    there is no '''l''' or '''L''' at the ent of Int literals
  Line/Column: [1, 2]
  ----------
  [42L, 9999999999999999999, 1.1e-1f]
   ^^^                               
ERROR
  Error code: lexer.err.literal.int-too-big
  Message:    too big Int literal, should be in range [-9223372036854775808, 9223372036854775807]
  Line/Column: [1, 7]
  ----------
  [42L, 9999999999999999999, 1.1e-1f]
        ^^^^^^^^^^^^^^^^^^^
ERROR
  Error code: lexer.err.literal.absent.floating-point
  Message:    there are no floating-point types
  Line/Column: [1, 28]
  ----------
  [42L, 9999999999999999999, 1.1e-1f]
                             ^^^^^^^         
```

### String-like
```java
import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.*;
import net.golovach.rholp.log.impl.*;

public class Demo {
    public static void main(String[] args) {
        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer("['A', α, `rho:io:stdout, ]", listener);

        lexer.readAll();
    }
}
```
```
ERROR
  Error code: lexer.err.literal.absent.single-quote
  Message:    there is no 'A' single-quote literal (char) only string ("...") and uri (`...`)
  Line/Column: [1, 2]
  ----------
  ['A', α, `rho:io:stdout, ]
   ^^^    
ERROR
  Error code: lexer.err.codepoint.illegal.unicode-1-char
  Message:    illegal 1-char unicode symbol: 'α'
  Line/Column: [1, 7]
  ----------
  ['A', α, `rho:io:stdout, ]
        ^                   
ERROR
  Error code: lexer.err.literal.uri.unclosed
  Message:    unclosed uri literal
  Line/Column: [1, 10]
  ----------
  ['A', α, `rho:io:stdout, ]
           ^^^^^^^^^^^^^^^^^
```

### Absent operators/keywords, misspelled keywords 
```java
import net.golovach.rholp.lexer.RhoLexer;
import net.golovach.rholp.log.*;
import net.golovach.rholp.log.impl.*;

public class Demo {
    public static void main(String[] args) {
        DiagnosticListener listener = new GroupedPrinter(System.out);
        RhoLexer lexer = new RhoLexer("type ~> null", listener);

        lexer.readAll();
    }
}
```
```
NOTE
  Error code: lexer.note.identifier.like-absent-keyword
  Message:    identifier 'type' like absent keyword/type
  Line/Column: [1, 1]
  ----------
  type ~> null
  ^^^^        
WARN
  Error code: lexer.warn.identifier.like-existing-keyword
  Message:    identifier 'null' like existing keyword/type 'Nil', maybe typo?
  Line/Column: [1, 9]
  ----------
  type ~> null
          ^^^^
ERROR
  Error code: lexer.err.operator.absent.arrow
  Message:    there is no arrow operator '~>' only ('<-', '<=', '=>')
  Line/Column: [1, 6]
  ----------
  type ~> null
       ^^               
```
