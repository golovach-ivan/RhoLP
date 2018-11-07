package net.golovach.rholp.msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.ofNullable;

public class MisspelledKeywords {
    private static final Map<String, String> map = new HashMap<>();

    static {
        try {
            Properties props = new Properties();
            props.load(AbsentKeywords.class.getResourceAsStream("/lexer-misspelled-keywords.properties"));

            for (String key : props.stringPropertyNames()) {
                map.put(key, props.getProperty(key));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> tryCorrectMisspelled(String actual) {
        return ofNullable(map.get(actual));
    }
}
