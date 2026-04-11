package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.ProductListPage;
import com.automationexercise.pages.HomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC18: View Category Products.
 * No account precondition needed - category pages publicly accessible.
 */
@Epic("Products")
@Feature("Category Navigation")
public class CategoryTest extends BaseTest {

    @Test(description = "TC18 - View Category Prodcuts")
    @Story("Navigate to category products via sidebar")
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify category sidebar is visible
            3. Click 'Women' category
            4. Click 'Dress' subcategory under Women
            5. Verify category page heading contains 'WOMEN'
            6. Click 'Men' category
            7. Click 'Tshirts' subcategory under Men
            8. Verify category page heading contains 'MEN'
            """)
    public void testViewCategoryProdcuts() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 3: Verify category sidebar is visible --
        Assert.assertTrue(homePage.sidebar().isCategorySidebarVisible(),
                "Category sidebar should be visible on the left side");

        // -- Step 4: Click 'Women' category to expand --
        homePage.sidebar().clickCategory("Women");

        // -- Step 5: Click 'Dress' subcategory under Women --
        ProductListPage categoryPage  = homePage.sidebar()
                .clickSubCategory("Women", "Dress");

        // -- Step 6: Verify Women category page --
        Assert.assertTrue(categoryPage .isOnCategoryPage(),
                "User should be navigated to Women category page");
        Assert.assertTrue(categoryPage .isProductsListVisible(),
                "Category products list should be visible");
        Assert.assertTrue(categoryPage .getHeadingText().contains("WOMEN"),
                "Category heading should contain 'WOMEN'");

        // -- Step 7: Click 'Men' category to expand --
        categoryPage .sidebar().clickCategory("Men");

        // -- Step 8: Click 'Tshirts' subcategory under Men --
        ProductListPage menCategoryPage  = categoryPage.sidebar()
                .clickSubCategory("Men", "Tshirts");

        // -- Step 8: Verify Men category page --
        Assert.assertTrue(menCategoryPage.isOnCategoryPage(),
                "User should be navigated to Men category page");
        Assert.assertTrue(menCategoryPage.isProductsListVisible(),
                "Men Category products list should be visible");
        Assert.assertTrue(menCategoryPage.getHeadingText().contains("MEN"),
                "Category heading should contain 'MEN'");
    }
}