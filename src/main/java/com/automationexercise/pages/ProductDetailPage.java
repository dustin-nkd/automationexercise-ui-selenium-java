package com.automationexercise.pages;

import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Product Detail page (/product_details/{id}).
 */
public class ProductDetailPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(ProductDetailPage.class);

    // ==================== LOCATORS ====================

    private static final By PRODUCT_NAME              = By.cssSelector(".product-information h2");
    private static final By PRODUCT_CATEGORY          = By.cssSelector(".product-information p:nth-child(3)");
    private static final By PRODUCT_PRICE             = By.cssSelector(".product-information span span");
    private static final By PRODUCT_AVAILABILITY      = By.cssSelector(".product-information p:nth-child(6)");
    private static final By PRODUCT_CONDITION         = By.cssSelector(".product-information p:nth-child(7)");
    private static final By PRODUCT_BRAND             = By.cssSelector(".product-information p:nth-child(8)");
    private static final By QUANTITY_INPUT            = By.cssSelector("#quantity");
    private static final By ADD_TO_CART_BTN           = By.cssSelector("button.cart");
    private static final By WRITE_YOUR_REVIEW_HEADING = By.cssSelector("a[href='#reviews']");
    private static final By REVIEW_NAME_INPUT         = By.cssSelector("#name");
    private static final By REVIEW_EMAIL_INPUT        = By.cssSelector("#email");
    private static final By REVIEW_TEXT_INPUT         = By.cssSelector("#review");
    private static final By REVIEW_SUBMIT_BUTTON      = By.cssSelector("#button-review");
    private static final By REVIEW_SUCCESS_MESSAGE    = By.cssSelector("#review-section .alert-success");

    // ==================== ACTIONS ====================

    /**
     * Verifies user is on product detail page by checking URL.
     *
     * @return true if current URL contains '/product_details'
     */
    @Step("Verify user is on Product Detail page")
    public boolean isOnProductDetailPage() {
        waitForUrlContains("/product_details");
        boolean onPage = getCurrentUrl().contains("/product_details");
        log.info("On Product Detail page: {}", onPage);
        return onPage;
    }

    /**
     * Verifies all required product detail fields are visible on the page.
     * Step 9: name, category, price, availability, condition, brand.
     *
     * @return true if all detail fields are visible
     */
    @Step("Verify all product detail fields are visible")
    public boolean isProductDetailsVisible() {
        boolean nameVisible         = isDisplayed(PRODUCT_NAME);
        boolean categoryVisible     = isDisplayed(PRODUCT_CATEGORY);
        boolean priceVisible        = isDisplayed(PRODUCT_PRICE);
        boolean availabilityVisible = isDisplayed(PRODUCT_AVAILABILITY);
        boolean conditionVisible    = isDisplayed(PRODUCT_CONDITION);
        boolean brandVisible        = isDisplayed(PRODUCT_BRAND);

        log.info("Product detail visibility - name: {}, category: {}, price: {}, " +
                "availibity: {}, condition: {}, brand: {}",
                nameVisible, categoryVisible, priceVisible,
                availabilityVisible, conditionVisible, brandVisible);

        return nameVisible && categoryVisible && priceVisible
                && availabilityVisible && conditionVisible && brandVisible;
    }

    /**
     * Returns product name text.
     *
     * @return product name
     */
    @Step("Get product name")
    public String getProductName() {
        return getText(PRODUCT_NAME);
    }

    /**
     * Returns product category text.
     *
     * @return product category
     */
    @Step("Get product category")
    public String getProductCategory() {
        return getText(PRODUCT_CATEGORY);
    }

    /**
     * Returns product price text.
     *
     * @return product price
     */
    @Step("Get product price")
    public String getProductPrice() {
        return getText(PRODUCT_PRICE);
    }

    /**
     * Returns product availibility text.
     *
     * @return product availibility
     */
    @Step("Get product availibility")
    public String getProductAvailability() {
        return getText(PRODUCT_AVAILABILITY);
    }

    /**
     * Returns product condition text.
     *
     * @return product condition
     */
    @Step("Get product condition")
    public String getProductCondition() {
        return getText(PRODUCT_CONDITION);
    }

    /**
     * Returns product brand text.
     *
     * @return product brand
     */
    @Step("Get product brand")
    public String getProductBrand() {
        return getText(PRODUCT_BRAND);
    }

    /**
     * Sets the product quantity.
     * Clears existing value types new quantity.
     *
     * @param quantity the desired quantity
     */
    @Step("Set product quantity to: {quantity}")
    public void setQuantity(int quantity) {
        log.info("Setting quantity to: {}", quantity);
        type(QUANTITY_INPUT, String.valueOf(quantity));
    }

    /**
     * Clicks 'Add to cart' button and returns CartModalComponent.
     *
     * @return CartModalComponent instance
     */
    @Step("Click 'Add to cart' button")
    public CartModalComponent clickAddToCart() {
        log.info("Clicking 'Add to cart' button");
        click(ADD_TO_CART_BTN);
        return new CartModalComponent();
    }

    /**
     * Verifies 'Write Your Review' heading is visible on product detail page.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'Write  Your Review' haeding is visible")
    public boolean isWriteYourReviewVisible() {
        boolean visible = isDisplayed(WRITE_YOUR_REVIEW_HEADING);
        log.info("'Write Your Review' visible: {}", visible);
        return visible;
    }

    /**
     * Returns the 'Write Your Review' heading text.
     *
     * @return heading text
     */
    @Step("Get 'Write Your Review' heading text")
    public String getWriteYourReviewHeadingText() {
        return getText(WRITE_YOUR_REVIEW_HEADING);
    }

    /**
     * Fills review form with name, email and review text.
     * Step 7 - all fields required together before submission (YAGNI).
     *
     * @param name   reviewer's name
     * @param email  reviewer's email
     * @param review review text
     */
    @Step("Fill review form - name: {name}, email: {email}")
    public void fillReviewForm(String name, String email, String review) {
        log.info("Filling review form for: '{}'", name);
        scrollToElement(WRITE_YOUR_REVIEW_HEADING);
        type(REVIEW_NAME_INPUT, name);
        type(REVIEW_EMAIL_INPUT, email);
        type(REVIEW_TEXT_INPUT, review);
    }

    /**
     * Clicks 'Submit' button to submit the review.
     */
    @Step("Click 'Submit' review button")
    public void submitReview() {
        log.info("Submitting review");
        click(REVIEW_SUBMIT_BUTTON);
    }

    /**
     * Returns the success message text after review submission.
     *
     * @return success message text
     */
    @Step("Get review success message text")
    public String getReviewSuccessMessage() {
        String message = getText(REVIEW_SUCCESS_MESSAGE);
        log.info("Review success message: '{}'", message);
        return message;
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