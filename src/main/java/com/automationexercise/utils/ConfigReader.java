package com.automationexercise.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";
    private static final String USER_DATA_PATH = "src/test/resources/testdata/user.properties";

    static {
        loadProperties();
    }

    private ConfigReader() {}

    private static void loadProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_PATH));
            properties.load(new FileInputStream(USER_DATA_PATH));
        } catch (IOException e) {
            throw new RuntimeException("❌ Cannot load properties file: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("❌ Key not found in properties: " + key);
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(get("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(get("explicit.wait"));
    }

    public static int getPageLoadTimeOut() {
        return Integer.parseInt(get("page.load.timeout"));
    }
}