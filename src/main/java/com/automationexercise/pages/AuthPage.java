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
    private static final By NEW_USER_SIGNUP_HEADING = By.cssSelector(".signup-form h2");
    private static final By SIGNUP_NAME_INPUT       = By.cssSelector(".signup-form input[data-qa='signup-name']");
    private static final By SIGNUP_EMAIL_INPUT      = By.cssSelector(".signup-form input[data-qa='signup-email']");
    private static final By SIGNUP_BUTTON           = By.cssSelector(".signup-form button[data-qa='signup-button']");

    // ==================== ACTIONS ====================

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
     * Combines steps 6 + 7 - always performed together, never independently.
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