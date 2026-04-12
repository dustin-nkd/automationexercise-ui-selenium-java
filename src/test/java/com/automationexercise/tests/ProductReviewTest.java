package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductDetailPage;
import com.automationexercise.pages.ProductsPage;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC21: Add review on product.
 * No account precondition needed - review form publicly accessible.
 */
@Epic("Products")
@Feature("Product Review")
public class ProductReviewTest extends BaseTest {

    private final Faker faker = new Faker();

    @Test(description = "TC21 - Add review on product")
    @Story("Submit a product review successfully")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Click 'Products' button
            3. Verify All Products page
            4. Click 'View Product' for first product
            5. Verify 'Write Your Review' is visible
            6. Enter name, email and review text
            7. Click Submit button
            8. Verify success message 'Thank you for your review.'
            """)
    public void testAddReviewOnProduct() {

        // -- Generate test data --
        String name   = faker.name().fullName();
        String email  = faker.internet().emailAddress();
        String review = faker.lorem().paragraph();

        // -- Step 2, 3: Open app and click Products --
        HomePage homePage = app.open();
        ProductsPage productsPage = homePage.header().clickProducts();

        // -- Step 4: Verify All Products page --
        Assert.assertTrue(productsPage.isOnProductsPage(),
                "User should be navigated to All Products page");

        // -- Step 5: Click View Product for first product --
        ProductDetailPage productDetailPage = productsPage.clickViewProduct("1");

        // -- Step 6: Verify 'Write Your Review' is visible --
        Assert.assertEquals(productDetailPage.getWriteYourReviewHeadingText(),
                "WRITE YOUR REVIEW",
                "'WRITE YOUR REVIEW' heading should be visible");

        // -- Step 7: Fill review form --
        productDetailPage.fillReviewForm(name, email, review);

        // -- Step 8: Submit review --
        productDetailPage.submitReview();

        // -- Step 9: Verify succes message --
        Assert.assertEquals(productDetailPage.getReviewSuccessMessage(),
                "Thank you for your review.",
                "Success message should be visible after review submission");
    }
}