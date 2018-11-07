package net.golovach.rholp.log;

public interface MessageDB {
    
    String[] msg(String key, String ... args);

    String msgTemplate(String key);
}
