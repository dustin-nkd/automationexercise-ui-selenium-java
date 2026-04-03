package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Subscription feature.
 * TC10: Verify Subscription in home page.
 * TC11: Verify Subscription in cart page.
 */
@Epic("Content")
@Feature("Subscription")
public class SubscriptionTest extends BaseTest {

    private final Faker faker = new Faker();

    @Test(description = "TC10 - Verify subscription in home page")
    @Story("Subscribe successfully from home page footer")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Scroll down to footer
            4. Verify 'SUBSCRIPTION' heading is visible
            5. Enter email and click subscribe button
            6. Verify success message is visible
            """)
    public void testSubscriptionInHomePage() {

        // -- Generate test data --
        String email = faker.internet().emailAddress();

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Scroll down to footer --
        homePage.footer().scrollToFooter();

        // -- Step 5: Verify 'SUBSCRIPTION' heading is visible --
        Assert.assertEquals(homePage.footer().getSubscriptionHeadingText(),
                "SUBSCRIPTION",
                "'SUBSCRIPTION' heading should be visible in footer");

        // -- Step 6: Enter email and click subscribe --
        homePage.footer().subscribeWithEmail(email);

        // -- Step 7: Verify success message --
        Assert.assertEquals(
                homePage.footer().getSubscriptionSuccessMessage(),
                "You have been successfully subscribed!",
                "Subscription success message should be visible"
        );
    }

    @Test(description = "TC11 - Verify subscription in cart page")
    @Story("Subscribe successfully from cart page footer")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'Cart' button
            4. Verify user is on Cart page
            5. Scroll down to footer
            6. Verify 'SUBSCRIPTION' heading is visible
            7. Enter email and click subscribe button
            8. Verify success message is visible
            """)
    public void testSubscriptionInCartPage() {

        // -- Generate test data --
        String email = faker.internet().emailAddress();

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Cart --
        CartPage cartPage = homePage.header().clickCart();

        // -- Step 5: Verify user is on Cart page --
        Assert.assertTrue(cartPage.isOnCartPage(),
                "User shouldd be navigated to Cart page");

        // -- Step 6: Scroll down to footer --
        cartPage.footer().scrollToFooter();

        // -- Step 7: Verify 'SUBSCRIPTION' heading is visible --
        Assert.assertEquals(cartPage.footer().getSubscriptionHeadingText(),
                "SUBSCRIPTION",
                "'SUBSCRIPTION' heading should be visible in footer");

        // -- Step 8: Enter email and click subscribe --
        cartPage.footer().subscribeWithEmail(email);

        // -- Step 9: Verify success message --
        Assert.assertEquals(
                cartPage.footer().getSubscriptionSuccessMessage(),
                "You have been successfully subscribed!",
                "Subscription success message should be visible"
        );
    }
}