package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductListPage;
import com.automationexercise.pages.ProductsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC19: View & Cart Brand Products.
 */
@Epic("Products")
@Feature("Brand Navigation")
public class BrandTest extends BaseTest {

    @Test(description = "TC19 - View & Cart Brand Products")
    @Story("Navigate to brand products via sidebar")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Click 'Products' button
            3. Verify brand sidebar is visible
            4. Click on brand 'Polo'
            5. Verify user is on brand page and products visible
            6. Click on brand 'H&M'
            7. Verify user is on that brand page and products visible
            """)
    public void testViewBrandProducts() {

        // -- Step 2, 3: Open app and click Products --
        HomePage homePage = app.open();
        ProductsPage productsPage = homePage.header().clickProducts();

        // -- Step 4: Verify brand sidebar is visible --
        Assert.assertTrue(productsPage.sidebar().isBrandSidebarVisible(),
                "Brand sidebar should be visible on left side");

        // -- Step 5: Click brand 'Polo' --
        ProductListPage brandPage = productsPage.sidebar().clickBrand("Polo");

        // -- Step 6: Verify navigated to Polo brand page --
        Assert.assertTrue(brandPage.isOnBrandPage(),
                "User should be navigated to Polo brand page");
        Assert.assertTrue(brandPage.isProductsListVisible(),
                "Brand products list should be displayed");
        Assert.assertTrue(brandPage.getHeadingText().contains("POLO"),
                "Brand heading should contain 'POLO'");

        // -- Step 7: Click another brand 'H&M' --
        ProductListPage anotherBrandPage = brandPage.sidebar().clickBrand("H&M");

        // -- Step 8: Verify navigated to H&M brand page --
        Assert.assertTrue(anotherBrandPage.isOnBrandPage(),
                "User should be navigated to H&M brand page");
        Assert.assertTrue(anotherBrandPage.isProductsListVisible(),
                "H&M products list should be displayed");
        Assert.assertTrue(anotherBrandPage.getHeadingText().contains("H&M"),
                "Brand heading should contain 'H&M'");
    }
}