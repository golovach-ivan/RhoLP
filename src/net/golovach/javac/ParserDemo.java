package net.golovach.javac;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.*;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.util.*;

import java.io.*;

// MyClass.java:1: integer number too large: 9999999999
// class MyClass {int x = 9999999999}
//                        ^
// MyClass.java:1: ';' expected
// class MyClass {int x = 9999999999}
//                                  ^
//
// class MyClass {
//     int x = (ERROR);
// }
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
