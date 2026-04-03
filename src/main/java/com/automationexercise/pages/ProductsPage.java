package com.automationexercise.pages;

import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Page Object for All Products page (/products).
 */
public class ProductsPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(ProductsPage.class);

    // ==================== LOCATORS ====================

    private static final By ALL_PRODUCTS_HEADING      = By.cssSelector(".title b");
    private static final By PRODUCTS_LIST             = By.cssSelector(".features_items");
    private static final By SEARCH_INPUT              = By.cssSelector("#search_product");
    private static final By SEARCH_BUTTON             = By.cssSelector("#submit_search");
    private static final By SEARCHED_PRODUCTS_HEADING =  By.cssSelector(".features_items .title");
    private static final By SEARCH_RESULT_ITEMS       = By.cssSelector(".features_items .product-image-wrapper");
    private static final By SEARCH_RESULT_ITEMS_NAME  = By.cssSelector(".features_items .productinfo p");

    // Dynamic locator template - targets 'View Product' link by position
    // Index is 1-based match CSS nth-child
    private static final String VIEW_PRODUCT_LINK_TMPL =
            "(//a[contains(@href, '/product_details')])[%s]";

    // -- Add to cart overlay (visible on hover) --
    private static final String ADD_TO_CART_BTN_TMPL =
            "(//div[@class='product-image-wrapper'])[%s]//a[contains(@data-product-id,'')]";
    private static final String PRODUCT_OVERLAY_TMPL =
            "(//div[@class='product-image-wrapper'])[%s]//div[@class='overlay-content']//a[@class='btn btn-default add-to-cart']";
    private static final String PRODUCT_IMAGE_WRAPPER_TMPL =
            "(//div[@class='product-image-wrapper'])[%s]";

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
    public ProductDetailPage clickViewProduct(String index) {
        log.info("Clicking 'View Product' for product at index: {}", index);
        scrollToElement(buildLocator(VIEW_PRODUCT_LINK_TMPL, index));
        click(buildLocator(VIEW_PRODUCT_LINK_TMPL, index));
        return new ProductDetailPage();
    }

    /**
     * Enters product name in search input and clicks search button.
     * Step 6 - always performed together (YAGNI).
     *
     * @param productName the product name to search for
     */
    @Step("Search for product: {productName}")
    public void searchProduct(String productName) {
        log.info("Searching for product: {}", productName);
        type(SEARCH_INPUT, productName);
        click(SEARCH_BUTTON);
    }

    /**
     * Returns the 'SEARCHED PRODUCTS' heading text
     * Used for exact text assertion in TCO9 step 7.
     *
     * @return heading text
     */
    @Step("Get 'SEARCHED PRODUCTS' heading text")
    public String getSearchedProductsHeadingText() {
        return getText(SEARCHED_PRODUCTS_HEADING);
    }

    /**
     * Returns the count of product items in the search results.
     * Used to verify at least one result is visible (TC09 step 8).
     *
     * @return number of search result items
     */
    @Step("Get search result count")
    public int getSearchResultCount() {
        log.info("Search result count: {}", countElements(SEARCH_RESULT_ITEMS));
        return countElements(SEARCH_RESULT_ITEMS);
    }

    /**
     * Verifies all search result product names contain the search keyword,
     * Ensure results are relevant to the search query (TC09 step 8).
     *
     * @param keyword the search keyword to verify against
     * @return true if all product names contain the keyword (case-insensitive)
     */
    @Step("Verify all search results contain keyword: {keyword}")
    public boolean areAllSearchResultsRelevant(String keyword) {
        List<WebElement> productNames = findAll(SEARCH_RESULT_ITEMS_NAME);

        if (productNames.isEmpty()) {
            log.warn("No search results found for keyword: {}", keyword);
            return false;
        }

        boolean allRelevant = productNames.stream()
                .map(e -> e.getText().toLowerCase())
                .allMatch(name -> name.contains(keyword.toLowerCase()));

        log.info("All search reuslts relevant to '{}': {}", keyword, allRelevant);
        return allRelevant;
    }

    /**
     * Hovers over a product and clicks 'Add to cart'.
     * Uses 1-based index - 1 means firts product.
     * Return CartModalComponent - modal appears after adding product.
     *
     * @param index 1-based position of the product in the list
     * @return CartModalComponent instance
     */
    @Step("Hover over product at index {index} and click 'Add to cart'")
    public CartModalComponent hoverAndAddToCart(String index) {
        log.info("Hovering over product at index: {} and adding to cart", index);
        By productWrapper = buildLocator(PRODUCT_IMAGE_WRAPPER_TMPL, index);
        By addToCartBtn   = buildLocator(PRODUCT_OVERLAY_TMPL, index);
        hoverOverElement(productWrapper);
        click(addToCartBtn);
        return new CartModalComponent();
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