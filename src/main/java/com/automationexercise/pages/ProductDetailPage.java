package com.automationexercise.pages;

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

    private static final By PRODUCT_NAME         = By.cssSelector(".product-information h2");
    private static final By PRODUCT_CATEGORY     = By.cssSelector(".product-information p:nth-child(3)");
    private static final By PRODUCT_PRICE        = By.cssSelector(".product-information span span");
    private static final By PRODUCT_AVAILABILITY = By.cssSelector(".product-information p:nth-child(6)");
    private static final By PRODUCT_CONDITION    = By.cssSelector(".product-information p:nth-child(7)");
    private static final By PRODUCT_BRAND        = By.cssSelector(".product-information p:nth-child(8)");

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
     * Returns HeaderComponent for navigation.
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }
}