package com.automationexercise.pages;

import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Home Page.
 * Does NOT hold HeaderComponent as a field - avoids circular dependency.
 * Header navigation accessed via header() getter.
 * Footer interactions accessed via footer() getter.
 */
public class HomePage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    // ==================== LOCATORS ====================

    private static final By HOME_SLIDER = By.cssSelector("#slider");

    // Dynamic locator - targets View Product link on home page feature items
    private static final String HOME_VIEW_PRODUCT_TMPL =
            "(//div[@class='features_items']//a[contains(@href,'/product_details')])[%s]";

    // ==================== ACTIONS ====================

    /**
     * Verifies home page is fully loaded.
     *
     * @return true if slider and navbar are both visible
     */
    @Step("Verify home page is visible")
    public boolean isHomePageVisible() {
        boolean visible = isDisplayed(HOME_SLIDER) && header().isNavBarVisible();
        log.info("Home page visible {}: ", visible);
        return visible;
    }

    /**
     * Cliks 'View Product' link for a featured product on home page.
     * Uses 1-based index - 1 means first featured product.
     *
     * @param index 1-based position of the product
     * @return ProductDetailPage instace
     */
    @Step("Click 'View Product' for featured product at index: {index}")
    public ProductDetailPage clickViewProduct(String index) {
        log.info("Clicking 'View Product' for featured product at index: {}", index);
        click(buildLocator(HOME_VIEW_PRODUCT_TMPL, index));
        return new ProductDetailPage();
    }

    /**
     * Returns HeaderComponent for navigation.
     * Instantiated fresh each call - consistent with By locator approach (no caching).
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }

    /**
     * Returns FooterComponent for footer interactions.
     * Instantiated fresh each call - consistent with no-caching approach.
     *
     * @return FooterComponent instance
     */
    public FooterComponent footer() {
        return new FooterComponent();
    }
}