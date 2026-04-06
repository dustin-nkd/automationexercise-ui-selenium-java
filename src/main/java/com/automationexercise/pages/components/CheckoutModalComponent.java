package com.automationexercise.pages.components;

import com.automationexercise.pages.AuthPage;
import com.automationexercise.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object for the Checkout modal popup.
 * Appears when user clicks 'Proceed To Checkout' without being logged in.
 * Provides option to Register/Login before proceeding.
 * <p>
 * Usage:
 *  CheckoutModalComponent modal = cartPage.clickProceedToCheckout();
 *  AuthPage authPage = modal.clickRegisterLogin();
 */
public class CheckoutModalComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CheckoutModalComponent.class);

    // ==================== LOCATORS ====================

    private static final By MODAL_CONTENT       = By.cssSelector(".modal-content");
    private static final By REGISTER_LOGIN_LINK = By.cssSelector(".modal-content a");

    // ==================== ACTIONS ====================

    /**
     * Verifies the checkout modal is visible.
     *
     * @return true if modal is visible
     */
    @Step("Verify checkout modal is visible")
    public boolean isModalVisible() {
        boolean visible = isDisplayed(MODAL_CONTENT);
        log.info("Checkout modal visible: {}", visible);
        return visible;
    }

    /**
     * Clicks 'Register / Login' button - navigates to auth page.
     *
     * @return AuthPage instance
     */
    @Step("Click 'Register / Login' button")
    public AuthPage clickRegisterLogin() {
        log.info("Clicking 'Register / Login' button");
        click(REGISTER_LOGIN_LINK);
        return new AuthPage();
    }
}