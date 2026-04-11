package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.steps.AccountSteps;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for TC20: Search Products and Verify Cart After Login.
 * Precondition: account created via AccountSteps.
 */
@Epic("Cart")
@Feature("Search and Cart")
public class SearchCartTest extends BaseTest {

    private final Faker faker = new Faker();

    private String email;
    private String password;

    /**
     * Creates a fresh account before each test then logs out.
     * TC20 needs to log in mid-test to verify cart persistence.
     */
    @BeforeMethod(alwaysRun = true)
    public void createAccount() {
        String name = faker.name().firstName();
        email       = faker.internet().emailAddress();
        password    = faker.text().text(12);

        // Register and logout - TC20 starts as guest, logs in mid-test
        new AccountSteps(app)
                .registerUser(name, email, password)
                .header().clickLogout();
    }

    @Test(description = "TC20 - Search Products and Verify Cart After Login")
    @Story("Search products, add to cart, verify cart persists after login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Precondition: account created via AccountSteps
            Steps:
            1. Navigate to home page
            2. Click Products button
            3. Verify All Products page
            4. Search for product
            5. Verify SEARCHED PRODUCTS heading
            6. Verify search results visible
            7. Add all search results to cart
            8. Click Cart, verify products visible
            9. Login with credentials
            10. Go to Cart page again
            11. Verify products still visible in cart after login
            """)
    public void testSearchProductsAndVerifyCartAfterLogin() {

        // -- Search keyword --
        String searchKeyword = "blue";

        // -- Step 2, 3: Open app and click Products --
        HomePage homePage = app.open();
        ProductsPage productsPage = homePage.header().clickProducts();

        // -- Step 4: Verify All Products page --
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "User should be navigated to All Products page");

        // -- Step 5: Search for product --
        productsPage.searchProduct(searchKeyword);

        // -- Step 6: Verify 'SEARCHED PRODUCTS' heading --
        Assert.assertEquals(productsPage.getSearchedProductsHeadingText(),
                "SEARCHED PRODUCTS",
                "'SEARCHED PRODUCTS' heading should be visible");

        // -- Step 7: Verify search results are visible and relevant --
        int searchResultCount = productsPage.getSearchResultCount();
        Assert.assertTrue(searchResultCount > 0,
                "At least one search result should be visible");
        Assert.assertTrue(productsPage.areAllSearchResultsRelevant(searchKeyword),
                "All search results should be relevant to keyword: " + searchKeyword);

        // -- Step 8: Add all search results to cart --
        CartModalComponent modal = productsPage.addAllSearchResultsToCart();

        // -- Step 9: Click Cart and verify product visible --
        CartPage cartPage = modal.clickViewCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User should be navigated to Cart page");
        Assert.assertEquals(cartPage.getCartItemCount(), searchResultCount,
                "Cart should contain all searched products");

        // -- Step 10: Login with credentials --
        homePage = cartPage.header().clickSignupLogin().login(email, password);
        Assert.assertTrue(homePage.header().isLoggedInAsVisible(),
                "'Logged in as username' should be visible after login");

        // -- Step 11: Go to Cart page again --
        cartPage = homePage.header().clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User should be navigated to Cart page after login");

        // -- Step 12: Verify products still visible in cart after login --
        Assert.assertEquals(cartPage.getCartItemCount(), searchResultCount,
                "Cart should still contain all searched products after login");
    }
}