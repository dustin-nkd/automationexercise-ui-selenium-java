package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AccountDeletedPage;
import com.automationexercise.pages.AuthPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.steps.AccountSteps;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for TC02: Login User with correct email and password.
 * Precondition: account is created via AccountSteps before each test.
 */
@Epic("User Authentication")
@Feature("Login")
public class LoginUserTest extends BaseTest {

    private final Faker faker = new Faker();

    // Test data - shared between @BeforeMethod and @Test
    private String name;
    private String email;
    private String password;

    /**
     * Creates a fresh user account before each test via UI flow.
     * After registration, logs out so TC02 can perform a clean login.
     */
    @BeforeMethod(alwaysRun = true)
    public void createAccount() {
        name     = faker.name().firstName();
        email    = faker.internet().emailAddress();
        password = faker.text().text(12);

        // Register account then logout - test starts from logged-out state
        new AccountSteps(app)
                .registerUser(name, email, password,
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.company().name(),
                        faker.address().streetAddress(),
                        faker.address().secondaryAddress(),
                        "Canada",
                        faker.address().state(),
                        faker.address().city(),
                        faker.address().zipCode(),
                        faker.phoneNumber().phoneNumber()
                )
                .header().clickLogout();
    }

    @Test(description = "TC02 - Login User with correct email and password")
    @Story("Login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("""
            Precondition: user account created via UI
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click Signup / Login
            4. Verify 'Login to your account' is visible
            5. Enter correct email and password, click Login
            6. Verify 'Logged in as username' is visible
            7. Click Delete Account
            8. Verify 'ACCOUNT DELETED!' is visible
            """)
    public void testLoginWithCorrectCredentials() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Signup / Login --
        AuthPage authPage = homePage.header().clickSignupLogin();

        // -- Step 5: Verify 'Login to your account' is visible
        Assert.assertEquals(authPage.getLoginHeadingText(), "Login to your account",
                "'Login to your account' heading should be visible ");

        // -- Step 6, 7: Enter credentials and click login ---
        homePage = authPage.login(email, password);

        // -- Step 8: Verify logged in as username --
        Assert.assertTrue(homePage.header().isLoggedInAsVisible(),
                "'Logged in as username' should be visible");
        Assert.assertEquals(homePage.header().getLoggedInUsername(), name,
                "Logged in username should match registered name");

        // -- Step 9: Click Delete Account --
        AccountDeletedPage accountDeletedPage = homePage.header().clickDeleteAccount();

        // -- Step 10: Verify 'ACCOUNT DELETED!' is visible --
        Assert.assertEquals(accountDeletedPage.getAccountDeletedHeadingText(), "ACCOUNT DELETED!",
                "'ACCOUNT DELETED!' heading should be visible");

        accountDeletedPage.clickContinue();
    }

    @Test(description = "TC03 - Login User with incorrect email and password")
    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
        Steps:
        1. Navigate to home page
        2. Verify home page is visible
        3. Click Signup / Login
        4. Verify 'Login to your account' is visible
        5. Enter incorrect email and password, click Login
        6. Verify error 'Your email or password is incorrect!' is visible
        """)
    public void testLoginWithIncorrectCredentials() {

        // -- Invalid credentials - guaranteed to not exist --
        String invalidEmail    = faker.internet().emailAddress();
        String invalidPassword = faker.text().text(12);

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Signup / Login --
        AuthPage authPage = homePage.header().clickSignupLogin();

        // -- Step 5: Verify 'Login to your account' is visible
        Assert.assertEquals(authPage.getLoginHeadingText(), "Login to your account",
                "'Login to your account' heading should be visible");

        // -- Step 6, 7: Enter invalid credentials and click login --
        authPage.loginWithInvalidCredentials(invalidEmail, invalidPassword);

        // -- Step 8: Verify error message --
        Assert.assertEquals(authPage.getLoginErrorMessage(),
                "Your email or password is incorrect!",
                "Error message should be visible after failed login");
    }
}