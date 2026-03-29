package com.automationexercise.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Account Deleted confirmation page.
 */
public class AccountDeletedPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(AccountDeletedPage.class);

    // ==================== LOCATORS ====================

    private static final By ACCOUNT_DELETED_HEADING = By.cssSelector("[data-qa='account-deleted'] b");
    private static final By CONTINUE_BUTTON         = By.cssSelector("[data-qa='continue-button']");

    // ==================== ACTIONS ====================

    /**
     * Verifies 'ACCOUNT DELETED!' heading is visible on the page.
     *
     * @return true if heading is visible
     */
    @Step("Verify ''ACCOUNT CREATED!' is visible")
    public boolean isAccountDeletedVisible() {
        boolean visible = isDisplayed(ACCOUNT_DELETED_HEADING);
        log.info("'ACCOUNT CREATED' visible: {}", visible);
        return visible;
    }

    /**
     * Returns the text of 'ACCOUNT DELETED!' heading.
     * Used for exact text assertion in test.
     *
     * @return heading text
     */
    @Step("Get 'ACCOUNT DELETED!' heading text")
    public String getAccountDeletedHeadingText() {
        return getText(ACCOUNT_DELETED_HEADING);
    }

    /**
     * Clicks 'Continue' button after account deletion.
     *
     * @return HomePage instance
     */
    @Step("Click 'Continue' button")
    public HomePage clickContinue() {
        log.info("Clicking 'Continue' button on Account Deleted page");
        click(CONTINUE_BUTTON);
        return new HomePage();
    }
}