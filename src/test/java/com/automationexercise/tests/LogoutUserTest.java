package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AuthPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.steps.AccountSteps;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for TC04: Logout User.
 * Precondition: account created and logged in via AccountSteps.
 */
@Epic("User Authentication")
@Feature("Logout")
public class LogoutUserTest extends BaseTest {

    private final Faker faker = new Faker();

    private String name;
    private String email;
    private String password;

    /**
     * Creates a fresh account before each test.
     * Does NOT log out after registration - TC04 starts from logged-in state.
     */
    @BeforeMethod(alwaysRun = true)
    public void createAccount() {
        name     =  faker.name().firstName();
        email    = faker.internet().emailAddress();
        password = faker.text().text(12);

        // Register and stay logged in - TC04 needs logged-in state as starting point
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
                        );
    }

    @Test(description = "TC04 - Logout User")
    @Story("Logout logged-in user successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Description("""
            Precondition: user account created and logged in via UI
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Verify 'Logged in as username' is visible
            4. Click Logout button
            5. Verify user is navigated to login page
            """)
    public void testLogoutUser() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 8: Verify logged in as username (precondition confirmation) --
        Assert.assertTrue(homePage.header().isLoggedInAsVisible(),
                "'Logged in as username' should be visible");
        Assert.assertEquals(homePage.header().getLoggedInUsername(), name,
                "Logged in username should match registered name");

        // -- Step 9: Click Logout --
        AuthPage authPage = homePage.header().clickLogout();

        // -- Step 10: Verify user is navigated to login page --
        Assert.assertTrue(authPage.isOnLoginPage(),
                "User should be navigated to login page after logout");
        Assert.assertEquals(authPage.getLoginHeadingText(), "Login to your account",
                "'Login to your account' heading should be visible after logout");
    }
}