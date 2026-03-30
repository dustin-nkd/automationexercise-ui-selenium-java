package com.automationexercise.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Login / Signup page (/login).
 * Does NOT hold HeaderComponent as a field - avoids circular dependency.
 */
public class AuthPage extends BasePage {

    private static final Logger log =  LoggerFactory.getLogger(AuthPage.class);

    // ==================== LOCATORS ====================

    // -- Login section --
    private static final By LOGIN_HEADING           = By.cssSelector(".login-form h2");
    private static final By LOGIN_EMAIL_INPUT       = By.cssSelector(".login-form input[data-qa='login-email']");
    private static final By LOGIN_PASSWORD_INPUT    = By.cssSelector(".login-form input[data-qa='login-password']");
    private static final By LOGIN_BUTTON            = By.cssSelector(".login-form button[data-qa='login-button']");
    private static final By LOGIN_ERROR_MESSAGE     = By.cssSelector(".login-form p");

    // -- Signup section --
    private static final By NEW_USER_SIGNUP_HEADING = By.cssSelector(".signup-form h2");
    private static final By SIGNUP_NAME_INPUT       = By.cssSelector(".signup-form input[data-qa='signup-name']");
    private static final By SIGNUP_EMAIL_INPUT      = By.cssSelector(".signup-form input[data-qa='signup-email']");
    private static final By SIGNUP_BUTTON           = By.cssSelector(".signup-form button[data-qa='signup-button']");

    // ==================== LOGIN ACTIONS ====================

    /**
     * Returns the text of 'Login to your account' heading.
     *
     * @return heading text
     */
    @Step("Get 'Login to your account' heading text")
    public String getLoginHeadingText() {
        return getText(LOGIN_HEADING);
    }

    /**
     * Verifies user is on login page by checking URL.
     *
     * @return true if current URL contains '/login'
     */
    @Step("Verify user is on login page")
    public boolean isOnLoginPage() {
        waitForUrlContains("/login");
        return getCurrentUrl().contains("login");
    }

    /**
     * Enters credentials ad clicks Login - expects success.
     *
     * @param email    registered email address
     * @param password account password
     * @return HomePage instance after successful login
     */
    @Step("Login with email: {email}")
    public HomePage login(String email, String password) {
        log.info("Logging in with email: '{}", email);
        type(LOGIN_EMAIL_INPUT, email);
        type(LOGIN_PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
        return new HomePage();
    }

    /**
     * Enters invalid credentials and clicks Login - expects failure.
     * Stays on AuthPage after failed login attempt.
     *
     * @param email    invalid email address
     * @param password invalid password
     */
    @Step("Attempt login with invalid credentials - email: {email}")
    public void loginWithInvalidCredentials(String email, String password) {
        log.info("Attempting login with invalid credentials - email: {}", email);
        type(LOGIN_EMAIL_INPUT, email);
        type(LOGIN_PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
    }

    /**
     * Returns the error message text shown after failed login attempt.
     *
     * @return error message text
     */
    @Step("Get login error message text")
    public String getLoginErrorMessage() {
        String message = getText(LOGIN_ERROR_MESSAGE);
        log.info("Login error message: {}", message);
        return message;
    }

    // ==================== SIGNUP ACTIONS ====================

    /**
     * Verifies 'New User Signup!' heading is visible.
     *
     * @return heading text for assertion
     */
    @Step("Get 'New User Signup!' heading text")
    public String getNewUserSignupHeadingText() {
        return getText(NEW_USER_SIGNUP_HEADING);
    }

    /**
     * Enters the name and email the click Signup.
     *
     * @param name  the new user's name
     * @param email the new user's email address
     * @return RegisterPage instance
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