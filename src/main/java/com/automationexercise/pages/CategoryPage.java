package com.automationexercise.pages;

import com.automationexercise.pages.components.CategoryComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Category Products page.
 * Displayed after clicking any category or subcategory link.
 */
public class CategoryPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CategoryPage.class);

    // ==================== LOCATORS ====================

    private static final By CATEGORY_HEADING = By.cssSelector(".features_items .title");
    private static final By PRODUCTS_LIST    = By.cssSelector(".features_items");

    // ==================== ACTIONS ====================

    /**
     * Returns the category page heading text.
     * Example: "WOMEN - DRESS PRODUCTs"
     *
     * @return heading text
     */
    @Step("Get category page heading text")
    public String getCategoryHeadingText() {
        String heading = getText(CATEGORY_HEADING);
        log.info("Category heading: '{}'", heading);
        return heading;
    }

    /**
     * Verifies the category products list is visible.
     *
     * @return true if products list is displayed
     */
    @Step("Verify category products list is visible")
    public boolean isProductsListVisible() {
        boolean visible = isDisplayed(PRODUCTS_LIST);
        log.info("Category products list is visible: {}", visible);
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
     * Returns CategoryComponent for sidebar navigation.
     *
     * @return CategoryComponent instance
     */
    public CategoryComponent category() {
        return new CategoryComponent();
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