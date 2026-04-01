package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductDetailPage;
import com.automationexercise.pages.ProductsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Products feature.
 * TC08: Verify All Products and product detail page.
 */
@Epic("Products")
@Feature("Product Listing")
public class ProductsTest extends BaseTest {

    @Test(description = "TC-08 - Verify All Products and product detail page")
    @Story("View all products and verify product detail")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'Products' button
            4. Verify user is on ALL PRODUCTS page
            5. Verify products list is visible
            6. Click 'View Product' of first product
            7. Verify user is on product detail page
            8. Verify product details: name, category, price,
               availability, condition, brand are visible
            """)
    public void testVerifyAllProductsandProductDetail() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Products --
        ProductsPage productsPage = homePage.header().clickProducts();

        // -- Step 5: Verify user is on All Products page --
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "User should be navigated to All Products page");

        // -- Step 6: Verify products list is visible --
        Assert.assertTrue(productsPage.isProductsListVisible(),
                "Products list should be visible");

        // -- Step 7: Click View Product of first product --
        ProductDetailPage productDetailPage = productsPage.clickViewProduct(1);

        // -- Step 8: Verify user is on product detail page --
        Assert.assertTrue(productDetailPage.isOnProductDetailPage(),
                "User should be navigated to product detail page");

        // -- Step 9: Verify all product detail fields are visible --
        Assert.assertTrue(productDetailPage.isProductDetailsVisible(),
                "All product detail fields should be visible: " +
                "name, category, price, availability, condition, brand");
    }

    @Test(description = "TC09 - Search Product")
    @Story("Search for a product by name")
    @Severity(SeverityLevel.NORMAL)
    @Description(
            """
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'Products' button
            4. Verify user is on ALL PRODUCTS page
            5. Enter product name in search input and click search
            6. Verify 'SEARCHED PRODUCTS' is visible
            7. Verify all products related to search are visible
            """)
    public void testSearchProduct() {

        // -- Search keyword -- using a common keyword likely to return results
        String searchKeyword = "top";

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Products --
        ProductsPage productsPage = homePage.header().clickProducts();

        // -- Step 5: Verify user is on All Products page --
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "User should be navigated to All Products page");

        // -- Step 6: Search for product --
        productsPage.searchProduct(searchKeyword);

        // -- Step 7: Verify 'SEARCHED PRODUCTS' heading is visible --
        Assert.assertEquals(productsPage.getSearchedProductsHeadingText(),
                "SEARCHED PRODUCTS",
                "'SEARCHED PRODUCTS' heading should be visible");

        // -- Step 8: Verify search results are visible and relevant --
        Assert.assertTrue(productsPage.getSearchResultCount() > 0,
                "At least one search result should be visible");

        Assert.assertTrue(productsPage.areAllSearchResultsRelevant(searchKeyword),
                "All search results should contains keyword: '" + searchKeyword + "'");
    }
}