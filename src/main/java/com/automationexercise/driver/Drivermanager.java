package com.automationexercise.driver;

import com.automationexercise.config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Manages WebDriver lifecycle using ThreadLocal for thread-safe parallel execution.
 * <p>
 * ThreadLocal ensures each test thread gets its own WebDriver instance,
 * preventing interference between parallel tests.
 * <p>
 * Selenium Manager (built-in since Selenium 4.6) automatically handles
 * driver binary resolution - no manual driver setup needed.
 * <p>
 * Usage:
 *  DriverManager.initDriver(); // create driver for current thread
 *  DriverManager.getDriver();  // ger driver for current thread
 *  DriverManage.quitDriver();  // quit and clean up
 */
public class Drivermanager {

    private static final Logger log = LoggerFactory.getLogger(Drivermanager.class);

    // ThreadLocal ensures each thread has its own isolated WebDriver instance
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Private constructor - prevent instantiation (utility class)
    private Drivermanager() {}

    /**
     * Initializes a WebDriver instance for the current thread.
     * Browser type is read from config (supports system property override).
     * Should be called once per test (in @BeforeMethod or @BeforeClass).
     */
    public static void initDriver() {
        String browser = ConfigManager.get("browser").toLowerCase().trim();
        boolean headless = ConfigManager.getBoolean("headless");

        log.info("Initializing '{}' driver | headless: {}", browser, headless );

        WebDriver driver = switch (browser) {
            case "chrome"  -> createChromeDriver(headless);
            case "firefox" -> createFirefoxDriver(headless);
            case "edge"    -> createEdgeDriver(headless);
            default -> throw new RuntimeException(
                    "Unsupported browser: '" + browser + "'. Supported: chrome, firefox, edge"
            );
        };

        configureTimeouts(driver);
        driverThreadLocal.set(driver);

        log.info("Driver initialized successfully for thread: {}", Thread.currentThread().getName());
    }

    /**
     * Returns the WebDriver instance for the current thread.
     * Throws exception if driver was not initialized - prevents silent null pointer errors.
     *
     * @return WebDriver instance bound to the current thread
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized for thread: " + Thread.currentThread().getName()
                    + ". Call DriverManager.initDriver() before using getDriver()."
            );
        }

        return driver;
    }

    /**
     * Quits the WebDriver and removes it from ThreadLocal.
     * MUST be called after each test to prevent browser process leaks.
     * Should be called in @AfterMethod or @AfterClass.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();

        if (driver != null) {
            try {
                driver.quit();
                log.info("Driver quit successfully for thread: {}", Thread.currentThread().getName());
            } catch (Exception e) {
                log.warn("Error while quitting driver: {}", e.getMessage());
            } finally {
                // Always remove from ThreadLocal to prevent memory leaks
                driverThreadLocal.remove();
            }
        }
    }

    // ==================== PRIVATE FACTORY METHODS ====================

    /**
     * Creates a ChromeDriver with recommended options.
     *
     * @param headless true to run without browser UI (for CI/CD)
     * @return configured ChromeDriver instance
     */
    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        if (headless) {
            // Use new headlss mode (available since Chrome 112)
            options.addArguments("--headless=new");
        }

        options.addArguments("--disalbe-infobars");
        options.addArguments("--disalbe-extensions");
        options.addArguments("--disalbe-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    /**
     * Creates a FirefoxDriver with recommended options.
     *
     * @param headless true to run without browser UI
     * @return configured FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    /**
     * Creates a EdgeDriver with recommended options.
     *
     * @param headless true to run without browser UI
     * @return configured EdgeDriver instance
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--start-maximized");

        return new EdgeDriver(options);
    }

    /**
     * Applies global timeout configurations to the driver.
     * Reads values from config.properties.
     *
     * @param driver the WebDriver instance to configure.
     */
    private static void configureTimeouts(WebDriver driver) {
        int pageLoadTimeout = ConfigManager.getInt("page.load.timeout");
        int implicitWait    = ConfigManager.getInt("implicit.wait");

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Maximize windows for consistent layout rendering across test runs
        driver.manage().window().maximize();

        log.debug("Timeouts configured - pageLoad: {}s | implicit {}s",
                pageLoadTimeout, implicitWait);
    }
}