package com.company.automation.base;

import com.company.automation.config.ConfigReader;
import com.company.automation.driver.BrowserType;
import com.company.automation.driver.DriverFactory;
import com.company.automation.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

  @BeforeMethod
  public void setUp() {
    String browser = ConfigReader.get("browser");
    WebDriver driver = DriverFactory.createDriver(BrowserType.valueOf(browser.toUpperCase()));

    DriverManager.setDriver(driver);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    DriverManager.quitDriver();
  }
}
