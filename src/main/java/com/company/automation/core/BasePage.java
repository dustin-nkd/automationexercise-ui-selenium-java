package com.company.automation.core;

import com.company.automation.config.ConfigReader;
import com.company.automation.driver.DriverManager;
import com.company.automation.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

  protected WebDriver driver;
  protected WebDriverWait wait;

  protected BasePage() {
    this.driver = DriverManager.getDriver();
    int timeout = Integer.parseInt(ConfigReader.get("timeout"));
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
  }

  protected void openUrl(String url) {
    driver.get(url);
  }

  protected WebElement waitForVisible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  protected WebElement waitForClickable(By locator) {
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
  }

  protected void click(By locator) {
    Log.info("Clicking element: " + locator);

    waitForClickable(locator).click();
  }

  protected void type(By locator, String text) {
    WebElement element = waitForVisible(locator);

    element.clear();
    element.sendKeys(text);
  }

  protected boolean isDisplayed(By locator) {
    return waitForVisible(locator).isDisplayed();
  }

  protected String getText(By locator) {
    return waitForVisible(locator).getText();
  }
}
