package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 */
public class PropertiesUtil {
    private static final Properties properties;

    static {
        try {
            properties = new Properties();
            ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("application.properties");
            properties.load(is);
            is.close();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static Properties get() {
        return properties;
    }
}
