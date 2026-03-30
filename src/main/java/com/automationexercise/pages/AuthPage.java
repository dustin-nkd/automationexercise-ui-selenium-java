package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Login / Signup page (/login).
 * Contains both Login and Signup sections - they share the same URL.
 */
public class AuthPage extends BasePage {

    private static final Logger log =  LoggerFactory.getLogger(AuthPage.class);

    public final HeaderComponent header = new HeaderComponent();

    // ==================== LOCATORS ====================

    // -- Signup section --
    private static final By LOGIN_HEADING           = By.cssSelector(".login-form h2");
    private static final By LOGIN_EMAIL_INPUT       = By.cssSelector(".login-form input[data-qa='login-email']");
    private static final By LOGIN_PASSWORD_INPUT    = By.cssSelector(".login-form input[data-qa='login-password']");
    private static final By LOGIN_BUTTON            = By.cssSelector(".login-form button[data-qa='login-button']");

    // -- Signup section --
    private static final By NEW_USER_SIGNUP_HEADING = By.cssSelector(".signup-form h2");
    private static final By SIGNUP_NAME_INPUT       = By.cssSelector(".signup-form input[data-qa='signup-name']");
    private static final By SIGNUP_EMAIL_INPUT      = By.cssSelector(".signup-form input[data-qa='signup-email']");
    private static final By SIGNUP_BUTTON           = By.cssSelector(".signup-form button[data-qa='signup-button']");

    // ==================== LOGIN ACTIONS ====================

    /**
     * Verifies 'Login to your account' heading is visible.
     *
     * @return heading text for assertion
     */
    @Step("Get 'Login to your account' heading text")
    public String getLoginHeadingText() {
        return getText(LOGIN_HEADING);
    }

    /**
     * Verifies 'Login to your account' heading is visible on the page.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'Login to your account' is visible")
    public boolean isLoginHeadingVisible() {
        boolean visible = isDisplayed(LOGIN_HEADING);
        log.info("'Login to your account' visible: {}", visible);
        return visible;
    }

    /**
     * Enters email and password then clicks Login.
     * Steps 6 + 7 always performed together - combined per YAGNI.
     *
     * @param email    registered email address
     * @param password account password
     * @return HomePage instance after successfull login
     */
    @Step("Login with email: {email}")
    public HomePage login(String email, String password) {
        log.info("Loggin in with email: '{}", email);
        type(LOGIN_EMAIL_INPUT, email);
        type(LOGIN_PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
        return new HomePage();
    }

    // ==================== SIGNUP ACTIONS ====================

    /**
     * Verifies 'New User Signup' heading is visible on the page.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'New User Signup!' is visible")
    public boolean isNewUserSignupVisible() {
        boolean visible = isDisplayed(NEW_USER_SIGNUP_HEADING);
        log.info("'New User Signup!' visible: {}", visible);
        return visible;
    }

    /**
     * Returns the text of the 'New User Signup!' heading.
     * Used for exact text assertion in test.
     *
     * @return heading text
     */
    @Step("Get 'New User Signup!' heading text")
    public String getNewUserSignupHeadingText() {
        return getText(NEW_USER_SIGNUP_HEADING);
    }

    /**
     * Enters the name and email in the Signup form then clicks Signup.
     * Combines steps 6 + 7 - always performed together - combined per YAGNI.
     *
     * @param name  the new user's name
     * @param email the new user's email address
     * @return RegisterPage instance after form submission
     */
    @Step("Sign up with name: {name} and email: {email}")
    public RegisterPage signUp(String name, String email) {
        log.info("'Sign up with name: {}, email: {}", name, email);
        type(SIGNUP_NAME_INPUT, name);
        type(SIGNUP_EMAIL_INPUT, email);
        click(SIGNUP_BUTTON);
        return new RegisterPage();
    }
}