package com.automationexercise.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Payment page.
 */
public class PaymentPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(PaymentPage.class);

    // ==================== LOCATORS ====================

    private static final By NAME_ON_CARD_INPUT = By.cssSelector("[data-qa='name-on-card']");
    private static final By CARD_NUMBER_INPUT  = By.cssSelector("[data-qa='card-number']");
    private static final By CVC_INPUT          = By.cssSelector("[data-qa='cvc']");
    private static final By EXPIRY_MONTH_INPUT = By.cssSelector("[data-qa='expiry-month']");
    private static final By EXPIRY_YEAR_INPUT  = By.cssSelector("[data-qa='expiry-year']");
    private static final By PAY_CONFIRM_BTN    = By.cssSelector("[data-qa='pay-button']");
    private static final By SUCCESS_MESSAGE    = By.cssSelector("[data-qa='order-placed'] b");
    private static final By CONTINUE_BTN       = By.cssSelector("[data-qa='continue-button']");

    // ==================== ACTIONS ====================

    /**
     * Fills all payment details.
     * Steps 16 - all fields required together before submission (YAGNI).
     *
     * @param nameOnCard  cardholder name
     * @param cardNumber  card number
     * @param cvc         card cvc
     * @param expiryMonth expiry month (e.g. "12")
     * @param expiryYear  exiry year (e.g. "2027")
     */
    @Step("Fill payment details - card holder: {nameOnCard}")
    public void fillPaymentDetails(String nameOnCard, String cardNumber,
                                   String cvc, String expiryMonth,
                                   String expiryYear) {
        log.info("Filling payment details for: '{}'", nameOnCard);
        type(NAME_ON_CARD_INPUT, nameOnCard);
        type(CARD_NUMBER_INPUT, cardNumber);
        type(CVC_INPUT, cvc);
        type(EXPIRY_MONTH_INPUT, expiryMonth);
        type(EXPIRY_YEAR_INPUT, expiryYear);
    }

    /**
     * Clicks 'Pay and Confirm Order' button.
     */
    @Step("Click 'Pay and Confirm Order' button")
    public void clickPayAndConfirmOrder() {
        log.info("Clicking 'Pay and Confirm Order' button");
        click(PAY_CONFIRM_BTN);
    }

    /**
     * Returns the order success message text.
     * Used for assertion in TC14 step 18.
     *
     * @return success message text
     */
    @Step("Get order success message text")
    public String getOrderSuccessMessageText() {
        String message = getText(SUCCESS_MESSAGE);
        log.info("Order success message: '{}'", message);
        return message;
    }

    /**
     * Verifies order placed success message is visible.
     *
     * @return true if success message is visible
     */
    @Step("Verify order success message is visible")
    public boolean isSuccessMessageVisible() {
        return isDisplayed(SUCCESS_MESSAGE);
    }

    /**
     * Clicks 'Continue' button after order placed successfully.
     *
     * @return HomePage instance
     */
    @Step("Click 'Continue' button after order placed")
    public HomePage clickContinue() {
        log.info("Clicking 'Continue' after order placed");
        click(CONTINUE_BTN);
        return new HomePage();
    }
}