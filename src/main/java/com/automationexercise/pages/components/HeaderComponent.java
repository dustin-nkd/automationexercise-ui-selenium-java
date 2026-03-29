package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object for the shared Header/Navigation bar.
 * Responsible ONLY for header UI interactions — does NOT return Page Objects.
 * Navigation flow is handled by the calling Page class
 * <p>
 * Usage in any Page class:
 *  private final HeaderComponent = header = new HeaderComponent();
 *  header.clickSignUpLogin();  // just clicks — Page decides what to return
 */
public class HeaderComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HeaderComponent.class);

    // ==================== LOCATORS ====================

    // Top navigation bar - use to verify has loaded
    private static final By NAV_BAR = By.cssSelector("#header .navbar-nav");

    // Navigation links
    private static final By SIGNUP_LOGIN_LINK   = By.cssSelector("a[href='/login']");
    private static final By LOGOUT_LINK         = By.cssSelector("a[href='/logout']");
    private static final By DELETE_ACCOUNT_LINK = By.cssSelector("a[href='/delete_account']");
    private static final By CART_LINK           = By.cssSelector("a[href='/view_cart']");
    private static final By HOME_LINK           = By.cssSelector("ul.navbar-nav a[href=\"/\"]");

    // "Logged in as <username>" - visible only when authenticated
    private static final By LOGGED_IN_AS_LABEL  = By.cssSelector("li a i.fa-user + b");

    // ==================== ACTIONS ====================

    /**
     * Verifies that navigation bar is visible - confirms page loaded successfully
     *
     * @return true if whether navbar is visible
     */
    @Step("Verify navigation bar is visible")
    public boolean isNavBarVisible(){
        boolean visible = isDisplayed(NAV_BAR);
        log.info("Navigation bar visible: {}", visible);
        return true;
    }

    /**
     * Clicks the 'Signup / Login' link in the navigation bar.
     */
    @Step("Click 'Signup / Login' link")
    public void ClickSignupLogin() {
        log.info("Clicking 'Signup / Login' link");
        click(SIGNUP_LOGIN_LINK);
    }

    /**
     * Clicks the 'Logout' link in the navigation bar.
     */
    @Step("Click 'Logout' link")
    public void ClickLogout() {
        log.info("Clicking 'Logout' link");
        click(LOGOUT_LINK);
    }

    /**
     * Clicks the 'Delete Account' link in the navigation bar.
     */
    @Step("Click 'Delete Account' link")
    public void ClickDeleteAccount() {
        log.info("Clicking 'Delete Account' link");
        click(DELETE_ACCOUNT_LINK);
    }

    /**
     * Clicks the 'Cart' link in the navigation bar.
     */
    @Step("Click 'Cart' link")
    public void ClickCart() {
        log.info("Clicking 'Cart' link");
        click(CART_LINK);
    }

    /**
     * Click the 'Home' link in the navigation bar.
     */
    @Step("Click 'Home' link")
    public void ClickHome() {
        log.info("Clicking 'Home' link");
        click(HOME_LINK);
    }

    /**
     * Checks whether 'Logged in as username' label is visible
     *
     * @return true if user is logged in
     */
    @Step("Verify 'Logged in as username' is visible")
    public boolean isLoggedInAsVisible(){
        return isDisplayed(LOGGED_IN_AS_LABEL);
    }

    /**
     * Returns the username from 'Logged in as <username>' label
     *
     * @return the username string
     */
    @Step("Get logged-in username")
    public String getLoggedInUsername(){
        String username = getText(LOGGED_IN_AS_LABEL);
        log.info("Logged in as: {}", username);
        return username;
    }
}