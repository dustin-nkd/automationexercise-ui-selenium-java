package com.company.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(BrowserType browser) {

        return switch (browser) {

            case CHROME -> new ChromeDriver();

            case FIREFOX -> new FirefoxDriver();
        };
    }

    private static WebDriver createChromeDriver() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        return new FirefoxDriver(options);
    }
}