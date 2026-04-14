package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.pages.components.CheckoutModalComponent;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Paths;

/**
 * Test class for TC24: Download Invoice after purchase order.
 * Verifies invoice file is downloaded after successful order placement.
 */
@Epic("Order")
@Feature("Invoice Download")
public class InvoiceTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(InvoiceTest.class);
    private final Faker faker = new Faker();

    // Download directory - matches DriverManager Chrome prefs configuration
    private static final String DOWNLOAD_DIR =
            Paths.get(System.getProperty("user.dir"), "target", "downloads")
                    .toAbsolutePath().toString();

    /**
     * Cleans up download directory before each test.
     * Ensure no leftover files from previous runs affect assertions.
     */
    @BeforeMethod(alwaysRun = true)
    public void cleanDownloadDirectory() {
        File dir = new File(DOWNLOAD_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        } else {
            // Delete all existing files in download directory
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        log.info("Download directory cleaned: {}", DOWNLOAD_DIR);
    }

    @Test(description = "TC24 - Download Invoice after purchase order")
    @Story("Download invoice after placing order successfully")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page and verify visible
            2. Add product to cart
            3. Click Cart, verify cart page
            4. Click Proceed To Checkout — modal (guest user)
            5. Click Register/Login, register new account
            6. Verify ACCOUNT CREATED, click Continue
            7. Verify logged in as username
            8. Return to Cart, Proceed To Checkout
            9. Verify address details and order review
            10. Enter comment, Place Order
            11. Fill payment details, Pay and Confirm
            12. Verify success message
            13. Click Download Invoice, verify file downloaded
            14. Click Continue
            15. Delete account, verify ACCOUNT DELETED
            """)
    public void testDownloadInvoiceAfterPurchase() {

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

        // -- Step 5, 6: Verify cart page --
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be displayed");

        // -- Step 7: Click Proceed To Checkout - modal - (guest user) --
        CheckoutModalComponent checkoutModal = cartPage.clickProceedToCheckout();
        Assert.assertTrue(checkoutModal.isModalVisible(),
                "Checkout modal should be visible");

        // -- Step 8: click Register / Login --
        AuthPage authPage = checkoutModal.clickRegisterLogin();

        // -- Step 9: Register new account --
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
                "'Loggged in as username' should be visible");

        // -- Step 12: Return to Cart --
        cartPage = homePage.header().clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be displayed");

        // -- Step 13: Click Proceed To Cart - logged in, no modal --
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

        // -- Step 19: Click Download Invoice --
        paymentPage.clickDownloadInvoice();

        // -- Verify invoice file is downloaded --
        Assert.assertTrue(
                waitForDownload(DOWNLOAD_DIR, ".txt", 10),
                "Invoice PDF file should be downloaded to: " + DOWNLOAD_DIR
        );

        // -- Step 20: Click Continue --
        homePage = paymentPage.clickContinue();

        // -- Ste 21, 22: Delete account and verify --
        AccountDeletedPage accountDeletedPage = homePage.header().clickDeleteAccount();
        Assert.assertEquals(accountDeletedPage.getAccountDeletedHeadingText(),
                "ACCOUNT DELETED!",
                "'ACCOUNT DELETED!' should be visible");
        accountDeletedPage.clickContinue();
    }
}