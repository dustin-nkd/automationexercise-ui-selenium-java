package com.automationexercise.pages;

import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Cart Page (/view_cart).
 */
public class CartPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CartPage.class);

    // ==================== LOCATORS ====================

    private static final By CART_PAGE_TITLE = By.cssSelector("#cart_info");

    // ==================== ACTIONS ====================

    /**
     * Verifies user is on Cart page by checking URL.
     *
     * @return true if current URL contains '/view_cart'
     */
    @Step("Verify user is on Cart page")
    public boolean isOnCartPage() {
        waitForUrlContains("/view_cart");
        boolean onPage = getCurrentUrl().contains("/view_cart");
        log.info("On Cart page: {}", onPage);
        return onPage;
    }

    /**
     * Returns HeaderComponent for navigation.
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }

    /**
     * Returns FooterComponent for footer interaction.
     * Reuses FooterComponent - subscription logic defined once (DRY).
     *
     * @return FooterComponent instance
     */
    public FooterComponent footer() {
        return new FooterComponent();
    }
}