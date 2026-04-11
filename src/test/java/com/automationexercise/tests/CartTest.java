package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductDetailPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.CartModalComponent;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Cart feature.
 * TC12: Add Products in Cart.
 * TC13: Verify Product quantity in Cart.
 * TC17: Remove Products From Cart.
 */
@Epic("Cart")
@Feature("Cart Management")
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

    @Test(description = "TC13 - Verify Product quantity in Cart")
    @Story("Add product with specific quantity and verify in cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'View Product' for first product on home page
            4. Verify product detail page is opened
            5. Set quantity to 4
            6. Click 'Add to cart'
            7. Click 'View Cart'
            8. Verify product quantity in cart is 4
            """)
    public void testVerifyProductQuantityInCart() {

        // -- Expected quantity --
        int expectedQuantity = 4;

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click View Product for first product --
        ProductDetailPage productDetailPage = homePage.clickViewProduct("1");

        // -- Step 5: Verify product detail page is opened --
        Assert.assertTrue(productDetailPage.isOnProductDetailPage(),
                "Product detail page should be opened");

        // -- Step 6: Set quantity to 4 --
        productDetailPage.setQuantity(expectedQuantity);

        // -- Step 7: Click Add to cart --
        CartModalComponent modal = productDetailPage.clickAddToCart();
        Assert.assertTrue(modal.isModalVisible(),
                "Cart modal should be visible after adding product");

        // -- Step 8: Click View Cart --
        CartPage cartPage = modal.clickViewCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User should be navigated to Cart page");

        // -- Step 9: Verify product quantity is 4 --
        Assert.assertEquals(cartPage.getCartItemQuantity(1), expectedQuantity,
                "Product quantity in cart should be " + expectedQuantity);
    }

    @Test(description = "TC17 - Remove Products From Cart")
    @Story("Remove a product from cart and verifyh cart is empty")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Add product to cart via Products page
            4. Click View Cart
            5. Verify cart page is displayed
            6. Click 'X' button to remove product
            7. Verify product is removed from cart
            """)
    public void testRemoveProductFromCart() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Add first product to cart --
        ProductsPage productsPage = homePage.header().clickProducts();
        CartModalComponent modal = productsPage.hoverAndAddToCart("1");

        // -- Step 5: Click View Cart --
        CartPage cartPage = modal.clickViewCart();

        // -- Step 6: Verify cart page is displayed --
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be displayed");
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should contain 1 product before removal");

        // -- Get product id dynamically - avoids hardcoding product id --
        String productId = cartPage.getFirstCartItemProduct();

        // -- Step 7: Click 'X' to remove product --
        cartPage.removeItem(productId);

        // -- Step 8: Verify product is removed --
        Assert.assertTrue(cartPage.isProductRemoved(productId),
                "Product should be removed from cart");
        Assert.assertTrue(cartPage.isCartEmpty(),
                "Cart should be empty after removing the only product");
    }
}