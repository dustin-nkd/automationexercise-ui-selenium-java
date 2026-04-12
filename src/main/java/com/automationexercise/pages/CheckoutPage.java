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

    // -- Address detail locators --
    private static final By DELIVERY_FIRST_LAST_NAME = By.cssSelector("#address_delivery .address_firstname");
    private static final By DELIVERY_COMPANY         = By.cssSelector("#address_delivery .address_address1.address_address2");
    private static final By DELIVERY_ADDRESS1        = By.cssSelector("#address_delivery .address_address1:nth-child(4)");
    private static final By DELIVERY_CITY_STATE_ZIP  = By.cssSelector("#address_delivery .address_city");
    private static final By DELIVERY_COUNTRY         = By.cssSelector("#address_delivery .address_country_name");
    private static final By DELIVERY_PHONE           = By.cssSelector("#address_delivery .address_phone");

    private static final By BILLING_FIRST_LAST_NAME  = By.cssSelector("#address_invoice .address_firstname");
    private static final By BILLING_COMPANY          = By.cssSelector("#address_invoice .address_company");
    private static final By BILLING_ADDRESS1         = By.cssSelector("#address_invoice .address_address1:nth-child(4)");
    private static final By BILLING_CITY_STATE_ZIP   = By.cssSelector("#address_invoice .address_city_state_zip");
    private static final By BILLING_COUNTRY          = By.cssSelector("#address_invoice .address_country_name");
    private static final By BILLING_PHONE            = By.cssSelector("#address_invoice .address_phone");

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
     * Returns the full name shown in delivery address section.
     *
     * @return full name text
     */
    @Step("Get delivery full name")
    public String getDeliveryFullName() {
        return getText(DELIVERY_FIRST_LAST_NAME);
    }

    /**
     * Returns the full name shown in billing address section.
     *
     * @return full name text
     */
    @Step("Get billing full name")
    public String getBillingFullName() {
        return getText(BILLING_FIRST_LAST_NAME);
    }

    /**
     * Returns the address line 1 shown in delivery address section.
     *
     * @return address line 1 text
     */
    @Step("Get delivery address line 1")
    public String getDeliveryAddress1() {
        return getText(DELIVERY_ADDRESS1);
    }

    /**
     * Returns the address line 1 shown in billing address section.
     *
     * @return address line 1 text
     */
    @Step("Get billing address line 1")
    public String getBillingAddress1() {
        return getText(BILLING_ADDRESS1);
    }

    /**
     * Returns the country shown in delivery address section.
     *
     * @return country text
     */
    @Step("Get delivery address country")
    public String getDeliveryCountry() {
        return getText(DELIVERY_COUNTRY);
    }

    /**
     * Returns the country shown in billing address section.
     *
     * @return country text
     */
    @Step("Get billing address country")
    public String getBillingCountry() {
        return getText(BILLING_COUNTRY);
    }

    /**
     * Retuns the phone shown in delivery address section.
     *
     * @return phone text
     */
    @Step("Get delivery address phone")
    public String getDeliveryPhone() {
        return getText(DELIVERY_PHONE);
    }

    /**
     * Retuns the phone shown in billing address section.
     *
     * @return phone text
     */
    @Step("Get billing address phone")
    public String getBillingPhone() {
        return getText(BILLING_PHONE);
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