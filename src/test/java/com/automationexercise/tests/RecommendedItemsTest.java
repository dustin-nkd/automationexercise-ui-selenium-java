package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.components.CartModalComponent;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC22: Add to cart from Recommended items.
 * No account precondition needed - recommended items publicly accessible.
 */
@Epic("Cart")
@Feature("Recommended Items")
public class RecommendedItemsTest extends BaseTest {

    @Test(description = "TC22 - Add to cart from Recommended items")
    @Story("Add recommended product to cart successfully")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Scroll to bottom of page
            3. Verify 'RECOMMENDED ITEMS' are visible
            4. Click 'Add To Cart' on first recommended product
            5. Click 'View Cart'
            6. Verify product is displayed in cart
            """)
    public void testAddToCartFromRecommendedItems() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 3: Scroll to bottom of page --
        homePage.scrollToRecommendedSection();

        // -- Step 4: Verify 'RECOMMENDED ITEMS' are visible --
        Assert.assertEquals(homePage.getRecommendedItemsHeadingText(),
                "RECOMMENDED ITEMS",
                "'RECOMMENDED ITEMS' heading should be visible");

        // -- Step 5: Click 'Add To Cart' on first recommended prodcut --
        CartModalComponent modal = homePage.addFirstRecommendedItemToCart();
        Assert.assertTrue(modal.isModalVisible(),
                "Cart modal should be visible after adding recommended product");

        // -- Step 6: click 'View Cart' --
        CartPage cartPage = modal.clickViewCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User should be navigated to Cart page");

        // -- Step 7: Verify product is displayed in cart --
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
                "Cart should contain at least one product from recommended items");
    }
}