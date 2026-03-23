package com.automationexercise.base;

import com.automationexercise.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public abstract class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // ===== FIND ELEMENT =====
    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected WebElement findClickable(By locator) {
        return WaitUtils.waitForClickable(driver, locator);
    }

    protected WebElement findVisible(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    // ===== NAVIGATION =====
    @Step("Navigate to: {path}")
    protected void navigateTo(String baseUrl, String path) {
        driver.get(baseUrl + path);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // ===== ELEMENT INTERACTIONS =====
    @Step("Click element: {locator}")
    protected void click(By locator) {
        findClickable(locator).click();
    }

    @Step("Type '{text}' into: {locator}")
    protected void type(By locator, String text) {
        WebElement element = findVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Get text of: {locator}")
    protected String getText(By locator) {
        return findVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        new Select(find(locator)).selectByVisibleText(text);
    }

    // ===== SCROLL =====
    @Step("Scroll to element: {locator}")
    protected void scrollToElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", find(locator));
    }

    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    // ===== JAVASCRIPT =====
    public void jsClick(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", find(locator));
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    // ===== ACTIONS =====
    protected void hoverElement(By locator) {
        new Actions(driver).moveToElement(find(locator)).perform();
    }

    // ===== ALERT =====
    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    protected String getAlertText() {
        return driver.switchTo().alert().getText();
    }
}