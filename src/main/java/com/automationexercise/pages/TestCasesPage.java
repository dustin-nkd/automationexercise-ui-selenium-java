package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for thet Test Cases Page (/test_cases).
 */
public class TestCasesPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(TestCasesPage.class);

    // ==================== LOCATORS ====================

    private static final By TEST_CASES_HEADING = By.cssSelector(".title b");

    // ==================== ACTIONS ====================

    /**
     * Verifies user is on the Test Cases page by checking URL.
     *
     * @return true if current URL contains '/test_cases'
     */
    @Step("Verify user is on Test Case page")
    public boolean isOnTestCasesPage() {
        waitForUrlContains("/test_cases");
        boolean onPage = getCurrentUrl().contains("/test_cases");
        log.info("On Test Cases page: {}", onPage);
        return onPage;
    }

    /**
     * Returns the page heading text.
     * Used for additional assertion to confirm correct page content.
     *
     * @return heading text
     */
    @Step("Get Test Cases page heading text")
    public String getHeadingText() {
        return getText(TEST_CASES_HEADING);
    }

    /**
     * Returns HeaderComponent for navigation.
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }
}