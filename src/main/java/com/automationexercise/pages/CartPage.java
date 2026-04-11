package com.automationexercise.pages;

import com.automationexercise.pages.components.CheckoutModalComponent;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * Page Object for the Cart Page (/view_cart).
 */
public class CartPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CartPage.class);

    // ==================== LOCATORS ====================

    private static final By CART_PAGE_TITLE          = By.cssSelector("#cart_info");
    private static final By CART_ITEMS               = By.cssSelector("#cart_info tbody tr");
    private static final By CART_ITEM_NAME           = By.cssSelector("#cart_info tbody tr td.cart_description h4 a");
    private static final By CART_ITEM_PRICES         = By.cssSelector("#cart_info tbody tr td.cart_price p");
    private static final By CART_ITEM_QTYS           = By.cssSelector("#cart_info tbody tr td.cart_quantity button");
    private static final By CART_ITEM_TOTALS         = By.cssSelector("#cart_info tbody tr td.cart_total p");
    private static final By PROCEED_TO_CHECKOUT_BTN  = By.cssSelector(".btn.btn-default.check_out");
    private static final String REMOVE_ITEM_BTN_TMPL = "//tr[@id='product-%s']//a[@class='cart_quantity_delete']";
    private static final By FIRST_CART_ITEM_ROW      = By.cssSelector("#cart_info tbody tr:first-child");

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
     * Retursn the number of items in the cart.
     *
     * @return cart item count
     */
    @Step("Get cart item count")
    public int getCartItemCount() {
        int count = countElements(CART_ITEMS);
        log.info("Cart item count: {}", count);
        return count;
    }

    /**
     * Verifies all cart item prices, quantities and totals are visible.
     * TC12 step 10 - checks each row has price, qty=1, and total visible.
     *
     * @return true if all items have visible price, quantity and total
     */
    @Step("Verify cart items prices, quantities and totals are visible")
    public boolean areCartItemDetailsVisible() {
        List<WebElement> prices = findAll(CART_ITEM_PRICES);
        List<WebElement> qtys   = findAll(CART_ITEM_QTYS);
        List<WebElement> totals = findAll(CART_ITEM_TOTALS);

        if (prices.isEmpty() || qtys.isEmpty() || totals.isEmpty()) {
            log.warn("Cart item details not found");
            return false;
        }

        boolean pricesVisible = prices.stream().allMatch(WebElement::isDisplayed);
        boolean qtysVisible = qtys.stream().allMatch(WebElement::isDisplayed);
        boolean totalsVisible = totals.stream().allMatch(WebElement::isDisplayed);

        log.info("Cart details visible - prices: {}, quantities: {}, totals: {}",
                pricesVisible, qtysVisible, totalsVisible);

        return pricesVisible && qtysVisible && totalsVisible;
    }

    /**
     * Verifies each cart item's total equals its price multiplied by quantity.
     * TC12 step 10 - validates price calculation correctness.
     *
     * @return true if all totals are correct
     */
    @Step("Verify cart item totals are calculated correctly")
    public boolean areCartTotalsCorrect() {
        List<WebElement> prices = findAll(CART_ITEM_PRICES);
        List<WebElement> qtys = findAll(CART_ITEM_QTYS);
        List<WebElement> totals = findAll(CART_ITEM_TOTALS);

        for (int i = 0; i < prices.size(); i++) {
            // Parse price value - strip "Rs." prefix and whitespace
            int price    = parsePrice(prices.get(i).getText());
            int qty      = Integer.parseInt(qtys.get(i).getText().trim());
            int total    = parsePrice(totals.get(i).getText());
            int expected = price * qty;

            if (total != expected) {
                log.warn("Total mismatch at row {}: price={} * qty={} = {} but total={}",
                        i + 1, price, qty, expected, total);
                return false;
            }

            log.info("Row {}: price={} * qty={} = total={} ✓",  i + 1, price, qty, total);
        }

        return true;
    }

    /**
     * Returns the quantity of a cart item at the given position.
     * Uses 1-based index -1 means first cart item.
     *
     * @param index 1-based position of the cart item
     * @return quantity as integer
     */
    @Step("Get quantity of cart item at index: {index}")
    public int getCartItemQuantity(int index) {
        List<WebElement> qtys = findAll(CART_ITEM_QTYS);
        int quantity = Integer.parseInt(
                qtys.get(index - 1).getText().trim()
        );
        log.info("Cart item {} quantity: {}", index, quantity);
        return quantity;
    }

    /**
     * Clicks 'Proceed To Checkout' button.
     * Returns CheckoutModalComponent - shown when user is not logged in.
     *
     * @return CheckoutModalComponent instance
     */
    @Step("Click 'Proceed To Checkout' button")
    public CheckoutModalComponent clickProceedToCheckout() {
        log.info("Clicking 'Proceed To Checkout' button");
        click(PROCEED_TO_CHECKOUT_BTN);
        return new CheckoutModalComponent();
    }

    /**
     * Clicks 'Proceed To Checkout' when user is logged in.
     * Navigates directly to CheckoutPage - no modal appears.
     * Separate from clickProceedToCheckout() which returns CheckoutModalComponent.
     *
     * @return CheckoutPage instance
     */
    @Step("Click 'Proceed To Checkout' - logged in, navigate to CheckoutPage")
    public CheckoutPage clickProceedToCheckoutLoggedIn() {
        log.info("Clicking 'Proceed To Checkout' - logged in");
        click(PROCEED_TO_CHECKOUT_BTN);
        return new CheckoutPage();
    }

    /**
     * Clicks the'X' (delete) button for a cart item by product id.
     * Waits for the row to disappear after removal.
     *
     * @param productId the product id matching tr row id (e.g. "1", "2")
     */
    @Step("Remove cart item with product id: {productId}")
    public void removeItem(String productId) {
        log.info("Removing cart item with product id: {}", productId);
        By removeBtn = buildLocator(REMOVE_ITEM_BTN_TMPL, productId);
        By itemRow   = By.id("product-" + productId);
        click(removeBtn);
        waitForInvisible(itemRow);
    }

    /**
     * Verifies the cart is empty by checking no items remain.
     *
     * @return true if cart has no items
     */
    @Step("Verify cart is empty")
    public boolean isCartEmpty() {
        boolean empty = countElements(CART_ITEMS) == 0;
        log.info("Cart is empty: {}", empty);
        return empty;
    }

    /**
     * Verifies a specific product is no longer in the cart.
     *
     * @param productId the product id to check
     * @return true if prodcut row is not present
     */
    @Step("Verify product with id '{productId}' is removed from cart")
    public boolean isProductRemoved(String productId) {
        boolean removed = !isPresent(By.id("product-" + productId));
        log.info("Product '{} removed from cart: {}", productId, removed);
        return removed;
    }

    /**
     * Returns the product id of the first cart item.
     * Extracts id from the row's 'id' attribute (format: "product-{id}").
     * Used to dynamically get product id before removal - avoids hardcoding.
     *
     * @return product id string (e.g. "1", "2")
     */
    @Step("Get product id of first cart item")
    public String getFirstCartItemProduct() {
        String rowId = waitForPresence(FIRST_CART_ITEM_ROW).getAttribute("id");
        String productId = Objects.requireNonNull(rowId).replace("product-", "");
        log.info("First cart item product id: '{}'", productId);
        return productId;
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

    // ==================== PRIVATE HELPERS ====================

    /**
     * Parses a price string into an integer.
     * Strips currency prefix (e.g. "Rs. 500" -> 500).
     *
     * @param priceText raw price text from the page
     * @return integer price value
     */
    private int parsePrice(String priceText) {
        return Integer.parseInt(priceText.replaceAll("[^0-9]", "").trim());
    }
}