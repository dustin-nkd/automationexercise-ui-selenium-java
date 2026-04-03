package com.automationexercise.pages;

import com.automationexercise.config.ConfigManager;
import com.automationexercise.driver.Drivermanager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all Page Object classes.
 * Use By locators directly - avoids PageFactory caching issues (StaleElementReferenceException).
 * <p>
 * Design decisions:
 * - No PageFactory: elements are located fresh on every interaction
 * - By locators defined as constants in each Page class (static final)
 * - Dynamic locators built via helper method: buildLocator (template, param)
 * <p>
 * All Page classes MUST extend this class.
 */
public abstract class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Constructor - initializes driver and explicit wait from config.
     * No PageFactory - elements resolved fresh on every call.
     */
    public BasePage() {
        this.driver = Drivermanager.getDriver();
        this.wait   = new WebDriverWait(driver,
                Duration.ofSeconds(ConfigManager.getInt("explicit.wait")));
    }

    // ==================== NAVIGATION ====================

    /**
     * Navigates to the base URL defined in config.properties.
     */
    @Step("Navigate to base URL")
    public void openBaseUrl() {
        String url = ConfigManager.get("base.url");
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    /**
     * Navigates to a specific URL.
     *
     * @param url the full URL to navigate to
     */
    @Step("Navigate to URL: {url}")
    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    /**
     * Returns the current page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Returns the current page URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ==================== EXPLICIT WAIT HELPERS ====================

    /**
     * Waits until element is visible in the viewport.
     *
     * @param locator the By locator
     * @return the visible WebElement - always fresh, never cached
     */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until element is present in the DOM (may not be visible).
     *
     * @param locator the By locator
     * @return the present WebElement
     */
    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until element is clickable (visible + enable).
     *
     * @param locator the By locator
     * @return the clickable WebElement
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until element is invisible or removed from DOM.
     *
     * @param locator the By locator
     * @return true when is no longer visible
     */
    protected boolean waitForInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits until the page title contains the expected substring.
     *
     * @param titleFragment partial or full title to wait for
     */
    protected void waitForTitleContains(String titleFragment) {
        wait.until(ExpectedConditions.titleContains(titleFragment));
    }

    /**
     * Waits until the current URL contains the expected substring.
     *
     * @param urlFragment partial URL to wait for
     */
    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    // ==================== CORE INTERACTIONS ====================

    /**
     * Returns all elements matching the given locator.
     * Always resolves fresh - no caching, consistent with single element approach.
     *
     * @param locator the By locator
     * @return list of matching WebElements, empty list if none found
     */
    protected List<WebElement> findAll(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Returns the count of elements matching the given locator.
     * Useful for verifying list sizes without exposing WebElement list to caller.
     *
     * @param locator the By locator
     * @return number of matching elements
     */
    protected int countElements(By locator) {
        return findAll(locator).size();
    }

    /**
     * Clicks on a web element after waiting for it to be clickable.
     *
     * @param locator the By locator of the element
     */
    @Step("Click on the element: {locator}")
    protected void click(By locator) {
        log.debug("Clicking on element: {}", locator);
        waitForClickable(locator).click();
    }

    /**
     * Clears existing value and types text into an input field.
     *
     * @param locator the By locator of the input element
     * @param text   the text to type
     */
    @Step("Type '{text}' into element: {locator}")
    protected void type(By locator, String text) {
        log.debug("Typing '{} into: {}", text, locator);
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Returns the visible text of an element,
     *
     * @param locator the By locator of the element
     * @return trimmed text content
     */
    protected String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    /**
     * Returns the value of a specified attribute for an element.
     *
     * @param locator   the By locator of the element
     * @param attribute the attribute name (e.g., "value", "href", "class")
     */
    protected String getAttribute(By locator, String attribute) {
        return waitForVisible(locator).getAttribute(attribute);
    }

    /**
     * Checks whether an element is displayed on the page.
     * Returns false instead of throwing - safe or negative assertions.
     *
     * @param locator the By locator of the element
     * @return true if element is visible, false otherwise
     */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            log.debug("Element not displayed: {}", locator);
            return false;
        }
    }

    /**
     * Checks whether an element is present in the DOM (not necessarily visible).
     *
     * @param locator the By locator of the element
     * @return true if element exists in DOM, false otherwise
     */
    protected boolean isPresent(By locator) {
        try {
            waitForPresence(locator);
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks whether a checkbox is currently checked.
     *
     * @param locator the By locator of the checkbox
     * @return true if checkbox is checked
     */
    protected boolean isCheckboxChecked(By locator) {
        try {
            return waitForPresence(locator).isSelected();
        } catch (TimeoutException | NoSuchElementException e) {
            return true;
        }
    }

    /**
     * Selects a dropdown option bt its visible text.
     *
     * @param locator the By locator of the <select> element
     * @param text    the visible option text to select
     */
    @Step("Select '{text}' from dropdown: {locator")
    protected void selectByVisibleText(By locator, String text) {
        log.debug("Selecting '{}' from dropdown: {}", text, locator);
        new Select(waitForVisible(locator)).selectByVisibleText(text);
    }

    /** Scrolls to page until the element is in the viewport.
     *
     * @param locator the By locator of the element to scroll to
     */
    protected void scrollToElement(By locator) {
        WebElement element = waitForPresence(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'center'});",
                element
        );
        log.debug("Scrolled to element: {}", locator);
    }

    /**
     * Scrolls to the bottom of the page using JavaScript.
     */
    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );
    }

    /**
     * Clicks an element using JavaScript - fallback when normal click is intercepted.
     *
     * @param locator the By locator of the element
     */
    protected void jsClick(By locator) {
        WebElement element = waitForPresence(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        log.debug("JS click on element: {}", locator);
    }

    /**
     * Accepts a browser native alert dialog (clicks OK).
     * Waits for alert to be present before accepting.
     */
    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        log.debug("Alert accepted");
    }

    /**
     * Uploads a file by sending the absolute file path to an input[type='file'] element.
     * Does not click the element - sendKeys triggers file selection directly.
     *
     * @param locator  the By locator of input[type='file'] element
     * @param filePath absolute path to the file to upload
     */
    protected void uploadFile(By locator, String filePath) {
        log.debug("Uploading file: {}", filePath);
        waitForPresence(locator).sendKeys(filePath);
    }

    // ==================== DYNAMIC LOCATOR HELPER ====================

    /**
     * Builds a dynamic XPath or CSS locator by replacing a placeholder with a value.
     * Avoids string concatenation scattered across Page classes (DRY).
     * <p>
     * Example usage in a Page class:
     *  private static final String PRODUCT_BY_NAME = "//div[contains(text(),'%s')]"
     *  By locator = buildLocator(PRODUCT_BY_NAME, "Blue Top")
     *
     * @param template locator template with %d placeholder
     * @param value    value to inject
     * @return a By.xpath locator with index substituted
     */
    protected By buildLocator(String template, String value) {
        return By.xpath(String.format(template, String.valueOf(value)));
    }
}