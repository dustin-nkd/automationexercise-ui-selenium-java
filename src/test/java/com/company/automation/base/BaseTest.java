package com.company.automation.base;

import com.company.automation.driver.BrowserType;
import com.company.automation.driver.DriverFactory;
import com.company.automation.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverFactory.createDriver(BrowserType.CHROME);
        driver.manage().window().maximize();
        DriverManager.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}