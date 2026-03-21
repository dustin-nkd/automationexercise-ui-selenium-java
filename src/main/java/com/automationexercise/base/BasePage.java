package com.automationexercise.base;

import com.automationexercise.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public abstract class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Navigate to URL: {url}")
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
    @Step("Click Element")
    protected void click(WebElement element) {
        WaitUtils.waitForClickable(driver, element).click();
    }

    @Step("Type '{text}' into field")
    protected void type(WebElement element, String text) {
        WaitUtils.waitForClickable(driver, element).clear();
        element.sendKeys(text);
    }

    @Step("Get text of element")
    protected String getText(WebElement element) {
        return WaitUtils.waitForVisible(driver, element).getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected void selectByVisibleText(WebElement element, String text) {
        new Select(element).selectByVisibleText(text);
    }

    // ===== SCROLL =====
    @Step("Scroll to element")
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Step("Scroll to bottom of page")
    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Step("Scroll to top of page")
    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    // ===== JAVASCRIPT =====
    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    // ===== ACTIONS =====
    protected void hoverElement(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    // ===== ALERT =====
    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    protected String getAlertText() {
        return driver.switchTo().alert().getText();
    }
}