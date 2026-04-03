package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.ProductsPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object fot the 'Added to Cart' modal popup.
 * Appears after clicking 'Add to cart' on any product.
 * Provides two navigation options: Continue Shopping or View Cart.
 *
 * Usage:
 *  CartModalComponent modal = productsPage.hoverAndAddToCart(1);
 *  modal.clickContinueShopping(); // -> ProductsPage
 *  modal.clickViewCart()          // -> CartPage
 */
public class CartModalComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CartModalComponent.class);

    // ==================== LOCATORS ====================

    private static final By MODAL_CONTENT         = By.cssSelector("#cartModal .modal-content");
    private static final By CONTINUE_SHOPPING_BTN = By.cssSelector("#cartModal .btn-success");
    private static final By VIEW_CART_BTN         = By.cssSelector("#cartModal a[href='/view_cart']");

    // ==================== ACTIONS ====================

    /**
     * Verifies the cart modal is visible after adding a product.
     *
     * @return true if modal is visible
     */
    @Step("Verify cart modal is visible")
    public boolean isModalVisible() {
        boolean visible = isDisplayed(MODAL_CONTENT);
        log.info("Cart modal visible: {}", visible);
        return visible;
    }

    /**
     * Clicks 'Continue Shopping' button - closes modal, stays on ProductsPage.
     *
     * @return ProductsPage instance
     */
    @Step("Click 'Continue Shopping' button")
    public ProductsPage clickContinueShopping() {
        log.info("Clicking 'Continue Shopping'");
        click(CONTINUE_SHOPPING_BTN);
        waitForInvisible(MODAL_CONTENT);
        return new ProductsPage();
    }

    /**
     * Clicks 'View Cart' button - navigates to CartPage.
     *
     * @return CartPage instance
     */
    @Step("Click 'View Cart' button")
    public CartPage clickViewCart() {
        log.info("Clicking 'View Cart'");
        click(VIEW_CART_BTN);
        return new CartPage();
    }
}
