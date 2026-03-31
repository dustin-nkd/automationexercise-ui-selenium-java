package com.automationexercise.pages.components;

import com.automationexercise.pages.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object for the shared Header/Navigation bar.
 * Owns navigation flow - returns target Page Object after each click.
 * No circular dependency: Pages do NOT hold HeaderComponent as a field.
 * <p>
 * Dependency direction (one-way):
 *  HeaderComponent -> Page classes *
 *  Page classes    -> (do NOT import HeaderComponent)
 */
public class HeaderComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HeaderComponent.class);

    // ==================== LOCATORS ====================

    private static final By NAV_BAR             = By.cssSelector("#header .navbar-nav");
    private static final By SIGNUP_LOGIN_LINK   = By.cssSelector("a[href='/login']");
    private static final By LOGOUT_LINK         = By.cssSelector("a[href='/logout']");
    private static final By DELETE_ACCOUNT_LINK = By.cssSelector("a[href='/delete_account']");
    private static final By CART_LINK           = By.cssSelector("a[href='/view_cart']");
    private static final By HOME_LINK           = By.cssSelector("ul.navbar-nav a[href=\"/\"]");
    private static final By CONTACT_US_LINK     = By.cssSelector("a[href='/contact_us']");
    private static final By LOGGED_IN_AS_LABEL  = By.cssSelector("li a i.fa-user + b");

    // ==================== NAVIGATION ACTIONS ====================

    /**
     * Clicks the 'Signup / Login' link and returns AuthPage.
     *
     * @return AuthPage instance
     */
    @Step("Click 'Signup / Login' link")
    public AuthPage clickSignupLogin() {
        log.info("Clicking 'Signup / Login' link");
        click(SIGNUP_LOGIN_LINK);
        return new AuthPage();
    }

    /**
     * Clicks the 'Logout' link and returns AuthPage.
     *
     * @return AuthPage instance
     */
    @Step("Click 'Logout' link")
    public AuthPage clickLogout() {
        log.info("Clicking 'Logout' link");
        click(LOGOUT_LINK);
        return new AuthPage();
    }

    /**
     * Clicks the 'Delete Account' link and returns AccountDeletedPage.
     *
     * @return AccountDeletedPage
     */
    @Step("Click 'Delete Account' link")
    public AccountDeletedPage clickDeleteAccount() {
        log.info("Clicking 'Delete Account' link");
        click(DELETE_ACCOUNT_LINK);
        return new AccountDeletedPage();
    }

//    /**
//     * Clicks the 'Cart' link and returns CartPage.
//     *
//     * @return CartPage instance
//     */
//    @Step("Click 'Cart' link")
//    public CartPage clickCart() {
//        log.info("Clicking 'Cart' link");
//        click(CART_LINK);
//        return new CartPage();
//    }

    /**
     * Click the 'Home' link and returns HomePage.
     *
     * @return HomePage instance
     */
    @Step("Click 'Home' link")
    public HomePage clickHome() {
        log.info("Clicking 'Home' link");
        click(HOME_LINK);
        return new HomePage();
    }

    /**
     * Clicks 'Contact Us' link and returns ContactUsPage.
     *
     * @return ContactUsPage instance
     */
    @Step("Click 'Contact Us' link")
    public ContactUsPage clickContactUs() {
        log.info("Clicking 'Contact Us' link");
        click(CONTACT_US_LINK);
        return new ContactUsPage();
    }

    // ==================== STATE CHECKS ====================

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
     * Checks whether 'Logged in as username' label is visible
     *
     * @return true if user is logged in
     */
    @Step("Verify 'Logged in as username' is visible")
    public boolean isLoggedInAsVisible(){
        return isDisplayed(LOGGED_IN_AS_LABEL);
    }

    /**
     * Returns the username from 'Logged in as <username>' label.
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