package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import com.automationexercise.pages.components.CartModalComponent;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC23: Verify address details in checkout page.
 * Verifies delivery and billing address match registration data.
 */
@Epic("Orders")
@Feature("Checkout")
public class CheckoutAddressTest extends BaseTest {

    private final Faker faker = new Faker();

    @Test(description = "TC23 - Verify address details in checkout page")
    @Story("Verify checkout address matches registration addess")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Steps:
            1. Navigate to home page and verify visible
            2. Register new account with address details
            3. Verify ACCOUNT CREATED, click Continue
            4. Verify logged in as username
            5. Add product to cart
            6. Click Cart, verify cart page
            7. Click Proceed To Checkout
            8. Verify delivery address matches registration data
            9. Verify billing address matches registration data
            10. Delete account, verify ACCOUNT DELETED
            """)
    public void testVerifyAddressDetailsInCheckou() {

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
                "'Logged in as username' should be visible");

        // -- Step 8: Add product to cart --
        ProductsPage productsPage = homePage.header().clickProducts();
        CartModalComponent cartModal = productsPage.hoverAndAddToCart("1");
        CartPage cartPage = cartModal.clickViewCart();

        // -- Step 9, 10: Verify cart page --
        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cart page should be displayed");

        // -- Step 11: Click Proceed To Checkout -- logged in, no modal --
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckoutLoggedIn();

        // -- Step 12: Verify delivery address matches registration data --
        String expectedFullName = "Mr. " + firstName + " " + lastName;
        Assert.assertTrue(
                checkoutPage.getBillingFullName().contains(firstName)
                && checkoutPage.getDeliveryFullName().contains(lastName),
                "Delivery address full name should match registration: " + expectedFullName
        );
        Assert.assertTrue(
                checkoutPage.getDeliveryAddress1().contains(address1),
                "Delivery address 1 line should match registration: " + address1
        );
        Assert.assertEquals(
                checkoutPage.getDeliveryCountry(), country,
                "Delivery address country should match registration: " + country
        );
        Assert.assertTrue(checkoutPage.getDeliveryPhone().contains(mobileNumber),
                "Delivery address phone should match registration: " + mobileNumber);

        // -- Step 13: Verify billing address matches registration data --
        Assert.assertTrue(
                checkoutPage.getBillingFullName().contains(firstName)
                && checkoutPage.getBillingFullName().contains(lastName),
                "Billing address full name should match registration: " + expectedFullName
        );
        Assert.assertTrue(
                checkoutPage.getBillingAddress1().contains(address1),
                "Billing address 1 line should match registration: " + address1
        );
        Assert.assertEquals(checkoutPage.getBillingCountry(), country,
                "Billing address country should match registration: " + country);
        Assert.assertTrue(checkoutPage.getBillingPhone().contains(mobileNumber),
                "Billing address phone should match registration: " + mobileNumber
        );

        // -- Step 14, 15: Delete account and verify --
        AccountDeletedPage accountDeletedPage = checkoutPage.header().clickDeleteAccount();
        Assert.assertEquals(accountDeletedPage.getAccountDeletedHeadingText(),
                "ACCOUNT DELETED!",
                "'ACCOUNT DELETED!' should be visible");
        accountDeletedPage.clickContinue();
    }
}