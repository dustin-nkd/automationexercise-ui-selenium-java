package com.automationexercise.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Centralized configuration manager.
 * Loads properties from config file and supports runtime overrides via system properties.
 * <p>
 * Priority (highest to lowest):
 *  1. System properties (-Dbrowser=firefox passed via Maven/CLI)
 *  2. config.properties (default values in file)
 * <p>
 * Usage: ConfigManager.get("browser")
 */
public class ConfigManager {

    private static final Logger log  = LoggerFactory.getLogger(ConfigManager.class);

    // Path for the config file relative to project root
    private static final String CONFIG_FILE_PATH = "config/config.properties";

    // Holds the loaded properties - initialized once (Singleton-like behavior)
    private static final Properties properties = new Properties();

    // Static block: load config file once when class is first accessed
    static {
        loadProperties();
    }

    /**
     * Loads properties from config file into memory.
     * Called once via static intializer block.
     */
    private static void loadProperties() {
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
            log.info("Configuration loaded successfully from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            log.error("Failed to load configuration file: {}", CONFIG_FILE_PATH, e);
            throw new RuntimeException("Cannot load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    /**
     * Retrieves a config value by key.
     * System property takes priority over config file value - enables CI/CD overrides.
     *
     * @param key the property key (e.g., "browser", "base.url")
     * @return the resolved value
     * @throws RuntimeException if the key is not found in either source
     */
    public static String get(String key) {
        // System property overrides file property (useful for CI: -Dbrowser=firefox)
        String value = System.getProperty(key, properties.getProperty(key));

        if (value == null || value.isBlank()) {
            throw new RuntimeException("Configuration key not found: '" + key + "'");
        }

        return value.trim();
    }

    /**
     * Retrieves a config value as integer.
     *
     * @param key the property key (e.g., "explicit.wait")
     * @return the integer value
     */
    public static int getInt(String key) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Configuration key '" + key + "' is not a valid integer", e);
        }
    }

    /**
     * Retrieves a config value as boolean.
     *
     * @param key the property key (e.g., "headless")
     * @return the boolean value
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}