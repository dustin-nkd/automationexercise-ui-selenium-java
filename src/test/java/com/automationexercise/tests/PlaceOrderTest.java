package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.pages.components.CheckoutModalComponent;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Place Order feature.
 * TC14: Place Order - Register while Checkout.
 * TC15: Place Order - Register before checkout.
 */
@Epic("Orders")
@Feature("Place Order")
public class PlaceOrderTest extends BaseTest {

    private final Faker faker = new Faker();

    @Test(description = "TC14 - Place Order: Register while Checkout")
    @Story("Register during checkout flow and place order successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Steps:
            1. Navigate to home page and verify visible
            2. Add product to cart
            3. Click Cart, verify cart page
            4. Click Proceed To Checkout — modal appears
            5. Click Register/Login — navigate to auth page
            6. Register new account
            7. Verify ACCOUNT CREATED, click Continue
            8. Verify logged in as username
            9. Click Cart, Proceed To Checkout
            10. Verify address details and order review
            11. Enter comment, click Place Order
            12. Fill payment details, Pay and Confirm
            13. Verify order success message
            14. Delete account, verify ACCOUNT DELETED
            """)
    public void testPlaceOrderRegisterWhileCheckout() {

        // -- Generate test data --
        String name         = faker.name().firstName();
        String email        = faker.internet().emailAddress();
        String password     = faker.text().text(12);
        String firstName    = faker.name().firstName();
        String lastName     = faker.name().lastName();
        String company      = faker.company().name();
        String address1     = faker.address().streetAddress();
        String address2     = faker.address().secondaryAddress();
        String country      = "Canada";
        String state        = faker.address().state();
        String city         = faker.address().city();
        String zipcode      = faker.address().zipCode();
        String mobileNumber = faker.phoneNumber().phoneNumber();
        String comment      = faker.lorem().sentence();

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Add product to cart --
        ProductsPage productsPage = homePage.header().clickProducts();
        CartModalComponent cartModal = productsPage.hoverAndAddToCart("1");
        CartPage cartPage = cartModal.clickViewCart();

        // -- Step 5, 6: Click Cart, verify cart page --
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be visible");

        // -- Step 7: Click Proceed To Checkout -- modal appears (not logged in)
        CheckoutModalComponent checkoutModal = cartPage.clickProceedToCheckout();
        Assert.assertTrue(checkoutModal.isModalVisible(),
                "Checkout modal should be visible");

        // -- Step 8: Click Register / Login --
        AuthPage authPage = checkoutModal.clickRegisterLogin();

        // -- Step 9: Fill signup and create account --
        RegisterPage registerPage = authPage.signUp(name, email);
        registerPage.selectTitle("Mr")
                .enterPassword(password)
                .selectDateOfBirth("15", "May", "1990")
                .selectNewsletterCheckbox()
                .selectOptCheckBox()
                .fillAddressInfo(firstName, lastName, company,
                        address1, address2, country,
                        state, city, zipcode, mobileNumber);

        // -- Step 10: Verify ACCOUNT CREATED and click Continue --
        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();
        Assert.assertEquals(accountCreatedPage.getAccountCreatedHeadingText(),
                "ACCOUNT CREATED!",
                "'ACCOUNT CREATED!' should be visible");
        homePage = accountCreatedPage.clickContinue();

        // -- Step 11: Verify logged in as username --
        Assert.assertTrue(homePage.header().isLoggedInAsVisible(),
                "'Logged in as username' should be visible");
        Assert.assertEquals(homePage.header().getLoggedInUsername(), name,
                "Logged in username should match registered name");

        // -- Step 12: Click Cart --
        cartPage = homePage.header().clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be displayed");

        // -- Step 13: Click Proceed To Checkout -- goes directly to checkout (logged in)
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckoutLoggedIn();

        // -- Step 14: Verify address details and order review --
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(),
                "Delivery address should be visible");
        Assert.assertTrue(checkoutPage.isBillingAddressVisible(),
                "Billing address should be visible");
        Assert.assertTrue(checkoutPage.isOrderReviewVisible(),
                "Order review should be visible");

        // -- Step 15: Enter comment and place order --
        checkoutPage.enterComment(comment);
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        // -- Step 16, 17: Fill payment details and confirm --
        paymentPage.fillPaymentDetails(
                faker.name().fullName(),
                faker.finance().creditCard(),
                String.valueOf(faker.number().numberBetween(100, 999)),
                String.valueOf(faker.number().numberBetween(1, 12)),
                String.valueOf(faker.number().numberBetween(2025, 2030))
                );
        paymentPage.clickPayAndConfirmOrder();

        // -- Step 18: Verify success message --
        Assert.assertTrue(paymentPage.isSuccessMessageVisible(),
                "Order success message should be visible");
        Assert.assertEquals(paymentPage.getOrderSuccessMessageText(),
                "ORDER PLACED!",
                "Order placed success message should match");

        // -- Step 19, 20: Delete account and verify --
        homePage = paymentPage.clickContinue();
        AccountDeletedPage accountDeletedPage = homePage.header().clickDeleteAccount();
        Assert.assertEquals(accountDeletedPage.getAccountDeletedHeadingText(),
                "ACCOUNT DELETED!",
                "'ACCOUNT DELETED!' should be visible");
        accountDeletedPage.clickContinue();
    }

    @Test(description = "TC15 - Place Order: Register before Checkout")
    @Story("Register before checkout and place order successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Steps:
            1. Navigate to home page and verify visible
            2. Click Signup / Login and register new account
            3. Verify ACCOUNT CREATED, click Continue
            4. Verify logged in as username
            5. Add product to cart
            6. Click Cart, verify cart page
            7. Click Proceed To Checkout — direct (logged in)
            8. Verify address details and order review
            9. Enter comment, click Place Order
            10. Fill payment details, Pay and Confirm
            11. Verify order success message
            12. Delete account, verify ACCOUNT DELETED
            """)
    public void testPlaceOrderRegisterBeforeCheckout() {

        // -- Generate test data --
        String name         = faker.name().firstName();
        String email        = faker.internet().emailAddress();
        String password     = faker.text().text(12);
        String firstName    = faker.name().firstName();
        String lastName     = faker.name().lastName();
        String company      = faker.company().name();
        String address1     = faker.address().streetAddress();
        String address2     = faker.address().secondaryAddress();
        String country      = "Canada";
        String state        = faker.address().state();
        String city         = faker.address().city();
        String zipcode      = faker.address().zipCode();
        String mobileNumber = faker.phoneNumber().phoneNumber();
        String comment      = faker.lorem().sentence();

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4, 5: Click Signup / Login and register --
        RegisterPage registerPage = homePage.header()
                .clickSignupLogin()
                .signUp(name, email);

        registerPage.selectTitle("Mr")
                .enterPassword(password)
                .selectDateOfBirth("15", "May", "1990")
                .selectNewsletterCheckbox()
                .selectOptCheckBox()
                .fillAddressInfo(firstName, lastName, company,
                        address1, address2, country,
                        state, city, zipcode, mobileNumber);

        // -- Step 6: Verify ACCOUNT CREATED and click Continue --
        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();
        Assert.assertEquals(accountCreatedPage.getAccountCreatedHeadingText(),
                "ACCOUNT CREATED!",
                "'ACCOUNT CREATED!' should be visible");
        homePage = accountCreatedPage.clickContinue();

        // -- Step 7: Verify logged in as username --
        Assert.assertTrue(homePage.header().isLoggedInAsVisible(),
                "'Logged In as username' should be visible");
        Assert.assertEquals(homePage.header().getLoggedInUsername(), name,
                "Logged in username should match registered name");

        // -- Step 8: Add product to cart --
        ProductsPage productsPage = homePage.header().clickProducts();
        CartModalComponent cartModal = productsPage.hoverAndAddToCart("1");
        CartPage cartPage = cartModal.clickViewCart();

        // -- Step 9, 10: Verify cart page --
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be visible");

        // -- Step 11: Click Proceed To Checkout -- logged in, no modal --
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckoutLoggedIn();

        // -- Step 12: Verify addess details and order review --
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(),
                "Delivery address should be visible");
        Assert.assertTrue(checkoutPage.isBillingAddressVisible(),
                "Billing address should be visible");
        Assert.assertTrue(checkoutPage.isOrderReviewVisible(),
                "Order review should be visible");

        // -- Step 13: Enter comment and place order --
        checkoutPage.enterComment(comment);
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        // -- Step 14 ,15: Fill payment details and confirm --
        paymentPage.fillPaymentDetails(
                faker.name().fullName(),
                faker.finance().creditCard(),
                String.valueOf(faker.number().numberBetween(100, 999)),
                String.valueOf(faker.number().numberBetween(1, 12)),
                String.valueOf(faker.number().numberBetween(2025, 2030))
        );
        paymentPage.clickPayAndConfirmOrder();

        // -- Step 16: Verify success message --
        Assert.assertTrue(paymentPage.isSuccessMessageVisible(),
                "Order success message should be visible");
        Assert.assertEquals(paymentPage.getOrderSuccessMessageText(),
                "ORDER PLACED!",
                "Order placed success message should match");

        // -- Step 17, 18: Delete account and verify --
        homePage = paymentPage.clickContinue();
        AccountDeletedPage accountDeletedPage = homePage.header().clickDeleteAccount();
        Assert.assertEquals(accountDeletedPage.getAccountDeletedHeadingText(),
                "ACCOUNT DELETED!",
                "'ACCOUNT DELETED!' should be visible");
        accountDeletedPage.clickContinue();
    }
}