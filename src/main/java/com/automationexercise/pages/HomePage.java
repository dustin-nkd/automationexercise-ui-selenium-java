package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Home Page.
 * Does NOT hold HeaderComponent as a field - avoids circular dependency.
 * Header navigation accessed via header() getter.
 */
public class HomePage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    // ==================== LOCATORS ====================

    private static final By HOME_SLIDER = By.cssSelector("#slider");

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
     * Returns HeaderComponent for navigation.
     * Instantiated fresh each call - consistent with By locator approach (no caching).
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }
}