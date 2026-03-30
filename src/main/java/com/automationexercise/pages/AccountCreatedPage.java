package com.automationexercise.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Account Created confirmation page.
 */
public class AccountCreatedPage extends BasePage{

    private static final Logger log =  LoggerFactory.getLogger(AccountCreatedPage.class);

    // ==================== LOCATORS ====================

    private static final By ACCOUNT_CREATED_HEADING = By.cssSelector("[data-qa='account-created'] b");
    private static final By CONTINUE_BUTTON         = By.cssSelector("[data-qa='continue-button']");

    // ==================== ACTIONS ====================

    /**
     * Verifies 'ACCOUNT CREATED!' heading is visible on the page.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'ACCOUNT CREATED!' is visible")
    public boolean isAccountCreatedVisible(){
        boolean visible = isDisplayed(ACCOUNT_CREATED_HEADING);
        log.info("'ACCOUNT CREATED' is visible: {}", visible);
        return visible;
    }

    /**
     * Returns the text of 'ACCOUNT CREATED!' heading
     * Used for exact text assertion in test.
     *
     * @return heading text
     */
    @Step("Get 'ACCOUNT CREATED!' heading text")
    public String getAccountCreatedHeadingText(){
        return getText(ACCOUNT_CREATED_HEADING);
    }

    /**
     * Clicks 'Continue' button - navigates back to HomePage as logged-in user.
     *
     * @return HomePage instance
     */
    @Step("Click 'Continue' button")
    public HomePage clickContinue() {
        log.info("Clicking 'Continue' button on Account Created page");
        click(CONTINUE_BUTTON);
        return new HomePage();
    }
}