package com.automationexercise.pages;

import com.automationexercise.pages.components.SidebarComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for any product listing page with sidebar.
 * Replaces CategoryPage and BrandPage - same structure, different URL context.
 *
 * Use for:
 *  - Category pages (/category_products/{id})
 *  - Brand pages (/brand_products/{brand})
 *
 *  URL-based verification differentiates which context is active.
 */
public class ProductListPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(ProductListPage.class);

    // ==================== LOCATORS ====================

    private static final By PAGE_HEADING  = By.cssSelector(".features_items .title");
    private static final By PRODUCTS_LIST = By.cssSelector(".features_items");

    // ==================== ACTIONS ====================

    /**
     * Returns the page heading text.
     * Example: "WOMEN - DRESS PRODUCTS" or "BRAND - POLO PRODUCTS"
     *
     * @return heading text
     */
    @Step("Get page heading text")
    public String getHeadingText() {
        String heading = getText(PAGE_HEADING);
        log.info("Page heading: '{}'", heading);
        return heading;
    }

    /**
     * Verifies products list is visible on the page.
     *
     * @return true if products list is displayed
     */
    @Step("Verify products list is visible")
    public boolean isProductsListVisible() {
        boolean visible = isDisplayed(PRODUCTS_LIST);
        log.info("Products list is visible: {}", visible);
        return visible;
    }

    /**
     * Verifies user is on a category page by checking URL.
     *
     * @return true if URL contains 'category_products'
     */
    @Step("Verify user is on category page")
    public boolean isOnCategoryPage() {
        waitForUrlContains("category_products");
        boolean onPage = getCurrentUrl().contains("category_products");
        log.info("On category page: {}", onPage);
        return onPage;
    }

    /**
     * Verifies user is on a brand page by checking URL.
     *
     * @return true if URL contains 'brand_products'
     */
    @Step("Verify user is on brand page")
    public boolean isOnBrandPage() {
        waitForUrlContains("brand_products");
        boolean onPage = getCurrentUrl().contains("brand_products");
        log.info("On brand page: {}", onPage);
        return onPage;
    }

    /**
     * Returns CategoryComponent for category and brand navigation.
     *
     * @return CategoryComponent instance
     */
    public SidebarComponent sidebar() {
        return new SidebarComponent();
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