package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC01: Register User.
 * Verifies the full user registration flow on automationexercise.com.
 */
@Epic("User Authentication")
@Feature("Register")
public class RegisterUserTest extends BaseTest {

    // Fake instance - generates realistic, randomized test data per test run
    private final Faker faker = new Faker();

    @Test(description = "TC01 -Register User")
    @Story("Register a new user successfully")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            Steps:
                        1. Navigate to home page
                        2. Verify home page is visible
                        3. Click Signup / Login
                        4. Verify 'New User Signup!' is visible
                        5. Enter name and email, click Signup
                        6. Verify 'ENTER ACCOUNT INFORMATION' is visible
                        7. Fill account and address details
                        8. Click Create Account
                        9. Verify 'ACCOUNT CREATED!' is visible
                        10. Click Continue — verify logged in as username
                        11. Click Delete Account
                        12. Verify 'ACCOUNT DELETED!' is visible
            """)
    public void testRegisterUser() {

        // -- Generate randomized test data --
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

        // -- Step 2, 3: Navigate to home page and verify --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Signup / Login --
        AuthPage authPage = homePage.clickSignupLogin();

        // -- Step 5: Verify 'New User Signup!' is visible --
        Assert.assertEquals(authPage.getNewUserSignupHeadingText(), "New User Signup!",
                "'New User Signup!' heading should be visible");

        // -- Step 6, 7: Enter name + email, click Signup --
        RegisterPage registerPage = authPage.signUp(name, email);

        // -- Step 8: Verify 'ENTER ACCOUNT INFORMATION' is visible --
        Assert.assertEquals(registerPage.getEnterAccountInfoHeadingText(), "ENTER ACCOUNT INFORMATION",
                "'ENTER ACCOUNT INFORMATION' heading should be visible");

        // -- Step 9: Fill account details --
        registerPage.selectTitle("Mr");
        registerPage.enterPassword(password);
        registerPage.selectDateOfBirth("15", "May", "1990");

        // -- Step 10: Select checkboxes --
        registerPage.selectNewsletterCheckbox();
        registerPage.selectOptCheckBox();

        // -- Step 11, 12: Fill address details --
        registerPage.fillAddressInfo(
                firstName, lastName, company,
                address1, address2, country,
                state, city, zipcode, mobileNumber
        );

        // -- Step 13: Click Create Account --
        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();

        // - Step 14: Verify 'ACCOUNT CREATED!' is visible --
        Assert.assertEquals(accountCreatedPage.getAccountCreatedHeadingText(), "ACCOUNT CREATED!",
                "'ACCOUNT CREATED!' heading should be visible");

        // -- Step 15: Click Continue --
        homePage = accountCreatedPage.clickContinue();

        // -- Step 16: Verify logged in as username --
        Assert.assertTrue(homePage.header.isLoggedInAsVisible(),
                "'Logged in as username' should be visible");
        Assert.assertEquals(homePage.header.getLoggedInUsername(), name,
                "Logged in username match registered name");

        // -- Step 17: Click Delete Account --
        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccountPage();

        // -- Step 18: Verify 'ACCOUNT DELETED!' is visible
        Assert.assertEquals(accountDeletedPage.getAccountDeletedHeadingText(), "ACCOUNT DELETED!",
                "'ACCOUNT DELETED!' heading should be visible");

        // -- Step 19: Click Continue --
        accountDeletedPage.clickContinue();
    }
}