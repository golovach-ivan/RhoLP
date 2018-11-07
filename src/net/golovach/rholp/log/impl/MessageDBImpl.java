package net.golovach.rholp.log.impl;

import net.golovach.rholp.log.MessageDB;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

public class MessageDBImpl implements MessageDB {
    private final ResourceBundle msgBundle;

    public MessageDBImpl(ResourceBundle msgBundle) {
        this.msgBundle = msgBundle;
    }

    // todo: add msg array
    @Override
    public String[] msg(String key, String... args) { //todo: args?
        List<String> result = new ArrayList<>();
        String msgPath = "";
        for (String component : key.split("\\.")) {
            msgPath += "." + component;
            try {
                String msg = format(msgBundle.getString(msgPath), args);
                result.add(0, msg);
            } catch (MissingResourceException ignore) { /*NOP*/ }
        }
        
//        return result.toArray(new String[result.size()]);

        return new String[] {format(msgTemplate(key), args)};
    }

    @Override
    public String msgTemplate(String key) {
        return msgBundle.getString(key);
    }
}
