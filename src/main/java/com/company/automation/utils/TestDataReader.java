package com.company.automation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class TestDataReader {

  public static Object[][] getJsonData(String fileName, Class<?> clazz) {

    try {
      ObjectMapper mapper = new ObjectMapper();
      InputStream input = TestDataReader.class.getClassLoader().getResourceAsStream(fileName);
      Object[] data = mapper.readValue(input, mapper.getTypeFactory().constructArrayType(clazz));
      Object[][] result = new Object[data.length][1];

      for (int i = 0; i < data.length; i++) {
        result[i][0] = data[i];
      }

      return result;
    } catch (Exception e) {
      throw new RuntimeException("Failed to read test data", e);
    }
  }
}
