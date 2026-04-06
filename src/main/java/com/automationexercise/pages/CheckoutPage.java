package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Checkout page (/checkout).
 */
public class CheckoutPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CheckoutPage.class);

    // ==================== LOCATORS ====================

    private static final By DELIVERY_ADDRESS  = By.cssSelector("#address_delivery");
    private static final By BILLING_ADDRESS   = By.cssSelector("#address_invoice");
    private static final By ORDER_ITEMS_TABLE = By.cssSelector("#cart_info");
    private static final By COMMENT_INPUT     = By.cssSelector("textarea.form-control");
    private static final By PLACE_ORDER_BTN   = By.cssSelector("a.btn.btn-default.check_out");

    // ==================== ACTIONS ====================

    /**
     * Verifies delivery address section is visible.
     *
     * @return true if delivery address is visible
     */
    @Step("Verify delivery address is visible")
    public boolean isDeliveryAddressVisible() {
        boolean visible = isDisplayed(DELIVERY_ADDRESS);
        log.info("Delivery address visible: {}", visible);
        return visible;
    }

    /**
     * Verifies billing address section is visible
     *
     * @return true if billing address is visible
     */
    @Step("Verify billing address is visible")
    public boolean isBillingAddressVisible() {
        boolean visible = isDisplayed(BILLING_ADDRESS);
        log.info("Billing address visible: {}", visible);
        return visible;
    }

    /**
     * Verifies order review table is visible.
     *
     * @return true if order items table is visible
     */
    @Step("Verify order review is visible")
    public boolean isOrderReviewVisible() {
        boolean visible = isDisplayed(ORDER_ITEMS_TABLE);
        log.info("Order review visible: {}", visible);
        return visible;
    }

    /**
     * Enters a comment in the order comment text area.
     *
     * @param comment the order comment text
     */
    @Step("Enter order comment: {comment}")
    public void enterComment(String comment) {
        log.info("Entering order comment: {}", comment);
        type(COMMENT_INPUT, comment);
    }

    /**
     * Clicks 'Place Order' button and returns PaymentPage.
     *
     * @return PaymentPage
     */
    @Step("Click 'Place Order' button")
    public PaymentPage clickPlaceOrder() {
        log.info("Clicking 'Place Order' button");
        click(PLACE_ORDER_BTN);
        return new PaymentPage();
    }

    /**
     * Returns HeaderComponent for navigation.
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }
}