package net.golovach.javac;

import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.Token;
import com.sun.tools.javac.util.Context;

//> CLASS / class
//> IDENTIFIER / MyClass
//> LBRACE /
//> INT / int
//> IDENTIFIER / x
//> EQ / =
//> INTLITERAL / 9999999999
//> RBRACE /
//> EOF /
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
