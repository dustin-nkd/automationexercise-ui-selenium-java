package com.automationexercise.base;

import com.automationexercise.utils.ConfigReader;
import com.automationexercise.utils.DriverManager;
import com.automationexercise.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)
public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;

    // ===== SETUP =====
    @BeforeMethod
    public void setUp() {
        driver  = DriverManager.getDriver();
        baseUrl = ConfigReader.getBaseUrl();
        driver.get(baseUrl);
    }

    // ===== TEARDOWN =====
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtils.attachScreenshotOnFailure(driver);
            Allure.addAttachment("Failure URL", driver.getCurrentUrl());
        }
        DriverManager.quitDriver();
    }

    // ===== HELPER =====
    protected void navigateTo(String path) {
        driver.get(baseUrl + path);
    }
}