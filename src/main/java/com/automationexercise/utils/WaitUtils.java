package com.automationexercise.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private WaitUtils() {}

    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean waitForTextPresent(WebDriver driver, By locator, String text) {
        return getWait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForUrlContains(WebDriver driver, String url) {
        return getWait(driver).until(ExpectedConditions.urlContains(url));
    }

    public static boolean waitForElementInvisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForPresent(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}