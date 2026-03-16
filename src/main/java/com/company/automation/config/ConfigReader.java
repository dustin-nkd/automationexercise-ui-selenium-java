package com.company.automation.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

  private static final Properties properties = new Properties();

  static {
    String env = System.getProperty("env", "dev");
    String fileName = "config-" + env + ".properties";

    try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
      properties.load(input);
    } catch (Exception e) {
      throw new RuntimeException("Failed to load config file: " + fileName, e);
    }
  }

  private ConfigReader() {}

  public static String get(String key) {
    return properties.getProperty(key);
  }
}
