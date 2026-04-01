package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for All Products page (/products).
 */
public class ProductsPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(ProductsPage.class);

    // ==================== LOCATORS ====================

    private static final By ALL_PRODUCTS_HEADING = By.cssSelector(".title b");
    private static final By PRODUCTS_LIST        = By.cssSelector(".features_items");

    // Dynamic locator template - targets 'View Product' link by position
    // Index is 1-based match CSS nth-child
    private static final String VIEW_PRODUCT_LINK_TMPL =
            "(//a[contains(@href, '/product_details')])[%d]";

    // ==================== ACTIONS ====================

    /**
     * Verifies user is on All Products page by checking URL.
     *
     * @return true if current URL contains '/products'
     */
    @Step("Verify user is on All Products page")
    public boolean isOnProductsPage() {
        waitForUrlContains("/products");
        boolean onPage = getCurrentUrl().contains("/products");
        log.info("On Products page: {}", onPage);
        return onPage;
    }

    /**
     * Returns the All Products heading text.
     *
     * @return heading text
     */
    @Step("Get All Products heading text")
    public String getAllProductsHeadingText() {
        return getText(ALL_PRODUCTS_HEADING);
    }

    /**
     * Verifies the products list is visible on the page.
     *
     * @return true if products list is displayed
     */
    @Step("Verify products list is visible")
    public boolean isProductsListVisible() {
        boolean visible = isDisplayed(PRODUCTS_LIST);
        log.info("Products list visible: {}", visible);
        return visible;
    }

    /**
     * Clicks 'View Product' link for a product at the given position.
     * Uses 1-based index - 1 means the first product.
     *
     * @param index 1-based position of the product in the list
     * @return ProductDetailPage instance
     */
    @Step("Click 'View Product' for product at index: {index}")
    public ProductDetailPage clickViewProduct(int index) {
        log.info("Clicking 'View Product' for product at index: {}", index);
        scrollToElement(buildLocator(VIEW_PRODUCT_LINK_TMPL, index));
        click(buildLocator(VIEW_PRODUCT_LINK_TMPL, index));
        return new ProductDetailPage();
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