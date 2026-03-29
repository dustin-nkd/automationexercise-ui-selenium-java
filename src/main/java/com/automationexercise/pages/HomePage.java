package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Home Page.
 * Navigation to this page is handled by App.open() - not by this class.
 * Header interactions are delegated to HeaderComponent.
 */
public class HomePage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    // Header component - shared across all pages, instantiated per Page
    public final HeaderComponent header = new HeaderComponent();

    // ==================== LOCATORS ====================

    // Hero slide - unique to home page, confirms correct page is loaded
    private static final By HOME_SLIDER = By.cssSelector("#slider");

    // ==================== ACTIONS ====================

    /**
     * Verifies home page is fully loaded by checking slider and navbar.
     *
     * @return true if home page is visible
     */
    @Step("Verify home page is visible")
    public boolean isHomePageVisible() {
        boolean visible = isDisplayed(HOME_SLIDER) && header.isNavBarVisible();
        log.info("Home page visible {}: ", visible);
        return visible;
    }

    /**
     * Clicks 'Signup / Login' and returns the AuthPage.
     *
     * @return AuthPage instance
     */
    @Step("Navigate to Signup / Login page")
    public AuthPage clickSignupLogin() {
        header.ClickSignupLogin();
        return new AuthPage();
    }

    /**
     * Clicks 'Delete Account' and returns the DeleteAccountPage.
     *
     * @return DeleteAccountPage instance
     */
    @Step("Click 'Delete Account' and navigate to Account Deleted page")
    public AccountDeletedPage clickDeleteAccountPage() {
        header.ClickDeleteAccount();
        return new AccountDeletedPage();
    }
}