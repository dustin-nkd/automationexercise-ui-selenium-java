package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.CartModalComponent;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Cart feature.
 * TC12: Add Products in Cart.
 */
@Epic("Cart")
@Feature("Add to Cart")
public class CartTest extends BaseTest {

    @Test(description = "TC12 - Add Products in Cart")
    @Story("Add multiple products to cart and verify details")
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'Products' button
            4. Hover over first product and click 'Add to cart'
            5. Click 'Continue Shopping'
            6. Hover over second product and click 'Add to cart'
            7. Click 'View Cart'
            8. Verify both products are in cart
            9. Verify prices, quantities and totals
            """)
    public void testAddProductsInCart() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Products --
        ProductsPage productsPage = homePage.header().clickProducts();
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "User should be navigated to All Products page");

        // -- Step 5: Hover over first product and add to cart --
        CartModalComponent modal = productsPage.hoverAndAddToCart("1");
        Assert.assertTrue(modal.isModalVisible(),
                "Cart modal should be visible after adding first product");

        // -- Step 6: Click Continue Shopping --
        productsPage = modal.clickContinueShopping();

        // -- Step 7: Hover over second product and add to cart --
        modal = productsPage.hoverAndAddToCart("2");
        Assert.assertTrue(modal.isModalVisible(),
                "Cart modal should be visible after adding second product");

        // -- Step 8: Click View Cart --
        CartPage cartPage = modal.clickViewCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User should be navigated to Cart page");

        // -- Step 9: Verify both products are in cart --
        Assert.assertEquals(cartPage.getCartItemCount(), 2,
                "Cart should contain exactly 2 products");

        // -- Step 10: Verify prices, quantities and totals --
        Assert.assertTrue(cartPage.areCartItemDetailsVisible(),
                "Cart item prices, quantities adn totals should be visible");

        Assert.assertTrue(cartPage.areCartTotalsCorrect(),
                "Cart item totals should equal price x quantity");
    }
}