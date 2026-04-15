package com.automationexercise.pages;

import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.pages.components.SidebarComponent;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Home Page.
 * Header navigation accessed via header() getter.
 * Footer interactions accessed via footer() getter.
 * Sidebar navigation via sidebar() getter.
 */
public class HomePage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    // ==================== LOCATORS ====================

    private static final By HOME_SLIDER = By.cssSelector("#slider");

    // Dynamic locator - targets View Product link on home page feature items
    private static final String HOME_VIEW_PRODUCT_TMPL =
            "(//div[@class='features_items']//a[contains(@href,'/product_details')])[%s]";

    // Recommended items section
    private static final By RECOMMENDED_ITEMS_HEADING = By.cssSelector(".recommended_items .title");
    private static final By RECOMMENDED_ADD_TO_CART   = By.cssSelector("#recommended-item-carousel .add-to-cart");
    private static final By SCROLL_UP_ARROW           = By.cssSelector("#scrollUp");
    private static final By HERO_TEXT                 = By.cssSelector("div.active.item.left div h2");

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

    /** Scrolls to the bottom of home page to reveal Recommended Items section.
     * Step 3 of TC22.
     */
    @Step("Scroll to bottom of page")
    public void scrollToRecommendedSection() {
        log.info("Scrolling to bottom of page");
        scrollToBottom();
    }

    /**
     * Verifies 'RECOMMENDED ITEMS' section is visible.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'RECOMMENDED ITEMS' section is visible")
    public boolean isRecommendedItemsSectionVisible() {
        boolean visible = isDisplayed(RECOMMENDED_ITEMS_HEADING);
        log.info("'RECOMMENDED ITEMS' visible: {}", visible);
        return visible;
    }

    /**
     * Returns 'RECOMMENDED ITEMS' heading text.
     * Used for exact text assertion in TC22.
     *
     * @return heading text
     */
    @Step("Get 'RECOMMENDED ITEMS' heading text")
    public String getRecommendedItemsHeadingText() {
        return getText(RECOMMENDED_ITEMS_HEADING);
    }

    /**
     * Clicks 'Add To Cart' on the first recommended product.
     * Return CartModalComponent - modal appears after adding product.
     *
     * @return CartModalComponent instance
     */
    @Step("Click 'Add To Cart' on first recommended product")
    public CartModalComponent addFirstRecommendedItemToCart() {
        log.info("Adding first recommended item to cart");
        click(RECOMMENDED_ADD_TO_CART);
        return new CartModalComponent();
    }

    /**
     * Clicks the scroll-up arrow button at bottom right of the page.
     * Scrolls page back to top smoothly.
     */
    @Step("Click scroll up arrow button")
    public void clickScrollUpArrow() {
        log.info("Clicking scroll up arrow button");
        click(SCROLL_UP_ARROW);
    }

    /**
     * Returns the hero text visible at the top of home page.
     * Used for assertion in TC25 step 7.
     *
     * @return hero text content
     */
    @Step("Get hero text")
    public String getHeroText() {
        String text = getText(HERO_TEXT);
        log.info("Hero text: {}", text);
        return text;
    }

    /**
     * Verifies hero text is visible 0 confirms page scrolled back to top.
     *
     * @return true if hero text is visible
     */
    @Step("Verify hero text is visible")
    public boolean isHeroTextVisible() {
        boolean visible = isDisplayed(HERO_TEXT);
        log.info("Hero text visible: {}", visible);
        return visible;
    }

    /**
     * Scrolls page back to top using JavaScript - without arrow button.
     * Step 6 of TC26
     */
    @Step("Scroll up to top of page")
    public void scrollPageToTop() {
        log.info("Scrolling to top of page");
        scrollToTop();
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

    /**
     * Returns CategoryComponent for sidebar category navigation.
     * Instantiated fresh each call - consistent with no-caching approach.
     *
     * @return CategoryComponent instance
     */
    public SidebarComponent sidebar() {
        return new SidebarComponent();
    }
}