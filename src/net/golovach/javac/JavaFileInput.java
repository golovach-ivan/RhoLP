package net.golovach.javac;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class JavaFileInput extends SimpleJavaFileObject {
    private String contents = null;

    public JavaFileInput(String fileName, String src) {
        super(createUri(fileName), Kind.SOURCE);
        this.contents = src;
    }

    private static URI createUri(String fileName) {
        try {
            return new URI(fileName); //todo
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }
}
