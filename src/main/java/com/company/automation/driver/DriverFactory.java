package com.company.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static WebDriver createDriver(BrowserType browser) {
        return switch (browser) {
            case FIREFOX -> new FirefoxDriver();
            case CHROME -> new ChromeDriver();
        };
    }
}