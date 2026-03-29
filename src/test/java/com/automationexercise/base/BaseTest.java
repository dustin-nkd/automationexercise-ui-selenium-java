package com.automationexercise.base;

import com.automationexercise.App;
import com.automationexercise.driver.Drivermanager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for all test classes.
 * Handles WebDriver lifecycle and Allure screenshot attachment on failure.
 * <p>
 * All test classes MUST extend this class.
 * <p>
 * Lifecycle per test method:
 *  &#064;BeforeMethod  -> initDriver()
 *  &#064;Test          -> test logic via app.open() -> Page Objects
 *  &#064;AfterMethod   -> screenshot on fail -> quitDriver()
 */
public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    // Application entry point - available to all test classes via inheritance
    protected App app;

    /**
     * Initializes WebDriver before each test method.
     * Each test gets a fresh browser instance - no state leakage between tests.
     */
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        log.info("====== Starting test: {} =====", Thread.currentThread().getName());
        Drivermanager.initDriver();
        app = new App();
    }

    /**
     * Captures screenshot on failure, then quits WebDriver after each test method.
     *
     * @param result ITestResult injected by TestNG - contains test status and metadata
     * <p>
     * alwaysRun = true ensures teardown runs even if @BeforeMethod failed,
     * preventing orphaned browser processes.
     */
    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {
        // Capture screenshot only on failure - keeps report clean
        if (result.getStatus() == ITestResult.FAILURE) {
            log.warn("Test FAILED: {} - capturing screenshot", result.getName());
            captureScreenshot(result.getName());
        }

        log.info("===== Finished test: {} | Status: {} =====",
                result.getName(), getStatusLabel(result.getStatus()));

        Drivermanager.quitDriver();
    }

    // ==================== SCREENSHOT ====================
    /**
     * Takes a screenshot and attaches it to the Allure report.
     * &#064;Attachment  tells Allure to embed the byte array as a PNG image in the report.
     *
     * @param testName used as the attachment label in Allure
     * @return screenshot bytes, or empty array if driver is unavailable
     */
    @Attachment(value = "Screenshot on failure: {testName}", type = "image/png")
    private byte[] captureScreenshot(String testName) {
        try {
            WebDriver driver = Drivermanager.getDriver();
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to capture screenshot for test: {}", testName, e);
            return new byte[0];
        }
    }

    // ==================== UTILITY ====================
    /**
     * Converts TestNG status code to a human-readable label for logging.
     *
     * @param status ITestResult status integer
     * @return readable status string
     */
    private String getStatusLabel(int status) {
        return switch (status) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "UNKNOWN";
        };
    }
}