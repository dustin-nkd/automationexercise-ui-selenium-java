package com.automationexercise.steps;

import com.automationexercise.App;
import com.automationexercise.pages.HomePage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reusable business-level flows for account management.
 * Encapsulates multipage UI sequences used as test preconditions.
 * <p>
 * Design:
 *  - Receives App instance from BaseTest - does not create its own driver
 *  - Used in @BeforeMethod for test data setup, not in @Test assertions
 *  - Each method returns the resulting Page for further interaction if needed
 * <p>
 * Usage:
 *  AccountSteps accountSteps = new AccountSteps(app);
 *  accountSteps.registerUser(name, email, password, ...)
 */
public class AccountSteps {

    private static final Logger log = LoggerFactory.getLogger(AccountSteps.class);

    private final App app;

    /**
     * Constructor - receives App from BaseTest.
     * App already has an active driver session at this point.
     *
     * @param app the application entry point
     */
    public AccountSteps(App app) {
        this.app = app;
    }

    /**
     * Registers a new user account via UI flow.
     * Covers TC01 steps 2-15 as reusable precondition.
     * <p>
     * Returns HomePage in logged-in state -ready for further test actions.
     *
     * @param name         display name
     * @param email        unique email address
     * @param password     account password
     * @param firstName    first name
     * @param lastName     last name
     * @param company      company name
     * @param address1     primary address
     * @param address2     secondary address
     * @param country      country (must match dropdown option)
     * @param state        state / province
     * @param city         city
     * @param zipcode      zip / postal code
     * @param mobileNumber mobile phone number
     * @return HomePage instance in logged-in state
     */
    @Step("Precondition: register new user account for '{name}'")
    public HomePage registerUser(String name, String email, String password,
                                 String firstName, String lastName, String company,
                                 String address1, String address2, String country,
                                 String state, String city, String zipcode,
                                 String mobileNumber) {
        log.info("Setting up account for: name='{}', email='{}'", name, email);

        return app.open()
                .header().clickSignupLogin()
                .signUp(name, email)
                .selectTitle("Mr")
                .enterPassword(password)
                .selectDateOfBirth("15", "May", "1990")
                .selectNewsletterCheckbox()
                .selectOptCheckBox()
                .fillAddressInfo(firstName, lastName, company,
                        address1, address2, country,
                        state, city, zipcode, mobileNumber)
                .clickCreateAccount()
                .clickContinue();
    }

    /**
     * Registers a new user with minimal required data.
     * Uses sensible defaults for optional fields.
     * Convienient overload for tests that don't need specific address details.
     *
     * @param name     display name
     * @param email    unique email addess
     * @param password account password
     * @return HomePage instance in logged-in state
     */
    public HomePage registerUser(String name, String email, String password) {
        log.info("Setting up account with minimal data for: '{}'", name);
        return registerUser(
                name, email, password,
                "Test", "User", "Test Company",
                "123 Test Street", "Apt 1",
                "Canada",
                "Ontario", "Toronto", "M1M1M1",
                "0123456789"
        );
    }
}