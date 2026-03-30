package com.automationexercise.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Account Registration page.
 *
 */
public class RegisterPage extends BasePage {

    private static final Logger log =  LoggerFactory.getLogger(RegisterPage.class);

    // ==================== LOCATORS ====================

    // -- Page heading --
    private static final By ENTER_ACCOUNT_INFO_HEADING = By.cssSelector(".login-form h2.title b");

    // -- Account Information section --
    private static final By TITLE_MR_RADIO        = By.cssSelector("#id_gender1");
    private static final By TITLE_MRS_RADIO       = By.cssSelector("#id_gender2");
    private static final By NAME_INPUT            = By.cssSelector("#name");
    private static final By EMAIL_INPUT           = By.cssSelector("#email");
    private static final By PASSWORD_INPUT        = By.cssSelector("#password");
    private static final By DAY_OF_BIRTH_SELECT  = By.cssSelector("#days");
    private static final By MONTH_OF_BIRTH_SELECT = By.cssSelector("#months");
    private static final By YEAR_OF_BIRTH_SELECT  = By.cssSelector("#years");

    // -- Opt-in checkboxes --
    private static final By NEWSLETTER_CHECKBOX   = By.cssSelector("#newsletter");
    private static final By OPT_CHECKBOX          = By.cssSelector("#optin");

    // -- Address Information section --
    private static final By FIRST_NAME_INPUT      = By.cssSelector("#first_name");
    private static final By LAST_NAME_INPUT       = By.cssSelector("#last_name");
    private static final By COMPANY_INPUT         = By.cssSelector("#company");
    private static final By ADDRESS1_INPUT        = By.cssSelector("#address1");
    private static final By ADDRESS2_INPUT        = By.cssSelector("#address2");
    private static final By COUNTRY_SELECT        = By.cssSelector("#country");
    private static final By STATE_INPUT           = By.cssSelector("#state");
    private static final By CITY_INPUT            = By.cssSelector("#city");
    private static final By ZIPCODE_INPUT         = By.cssSelector("#zipcode");
    private static final By MOBILE_NUMBER_INPUT   = By.cssSelector("#mobile_number");

    // -- Submit --
    private static final By CREATE_ACCOUNT_BUTTON = By.cssSelector("button[data-qa='create-account']");

    // ==================== ACTIONS ====================

    /**
     * Verifies 'ENTER ACCOUNT INFORMATION' heading is visible.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'ENTER ACCOUNT INFORMATION' is visible")
    public boolean isEnterAccountInfoVisible() {
        boolean visible = isDisplayed(ENTER_ACCOUNT_INFO_HEADING);
        log.info("Verify 'ENTER ACCOUNT INFORMATION' visible: {}", visible);
        return visible;
    }

    /**
     * Returns the text of 'ENTER ACCOUNT INFORMATION' heading.
     * Used for exact text assertion in test.
     *
     * @return heading text
     */
    @Step("Get 'ENTER ACCOUNT INFORMATION' heading text")
    public String getEnterAccountInfoHeadingText() {
        return getText(ENTER_ACCOUNT_INFO_HEADING);
    }

    /**
     * Selects the title radio button (Mr. or Mrs).
     *
     * @param title accepted values: "Mr" or "Mrs"
     */
    @Step("Select title: {title}")
    public RegisterPage selectTitle(String title) {
        log.info("Selecting title: {}", title);
        if(title.equalsIgnoreCase("Mr")) {
            click(TITLE_MR_RADIO);
        } else if(title.equalsIgnoreCase("Mrs")) {
            click(TITLE_MRS_RADIO);
        } else {
            throw new IllegalArgumentException("Invalid title: '" + title + "'. Accepted: 'Mr', 'Mrs'");
        }
        return this;
    }

    /**
     * Fills in the password field.
     *
     * @param password the account password
     */
    @Step("Enter password")
    public RegisterPage enterPassword(String password) {
        log.info("Entering password: {}", password);
        type(PASSWORD_INPUT, password);
        return this;
    }

    /**
     * Selects the date of birth from the three dropdowns.
     *
     * @param day   day value (e.g., "15")
     * @param month month name (e.g., "May")
     * @param year  year value (e.g., "1990")
     */
    @Step("Select date of birth: {day} {month} {year}")
    public RegisterPage selectDateOfBirth(String day, String month, String year) {
        log.info("Selecting date of birth: {} {} {}", day, month, year);
        selectByVisibleText(DAY_OF_BIRTH_SELECT, day);
        selectByVisibleText(MONTH_OF_BIRTH_SELECT, month);
        selectByVisibleText(YEAR_OF_BIRTH_SELECT, year);
        return this;
    }

    /**
     * Checks the 'Sign up for our newsletter!' checkbox if not already checked.
     */
    @Step("Select 'Sign up for our newsletter!' checkbox")
    public RegisterPage selectNewsletterCheckbox() {
        log.info("Selecting newsletter checkbox");
        if (isCheckboxChecked(NEWSLETTER_CHECKBOX)) {
            click(NEWSLETTER_CHECKBOX);
        }
        return this;
    }

    /**
     * Checks the 'Receive special offers from our partners!' checkbox if not already checked.
     */
    @Step("Select 'Receive special offers from our partners!' checkbox")
    public RegisterPage selectOptCheckBox() {
        log.info("Selecting opt-in checkbox");
        if (isCheckboxChecked(OPT_CHECKBOX)) {
            click(OPT_CHECKBOX);
        }
        return this;
    }

    /**
     * Fills in all address information fields.
     *
     * @param firstName    first name
     * @param lastName     last name
     * @param company      company name (optional, can be empty string)
     * @param address1     primary address line
     * @param address2     secondary address line (optional, can be empty string)
     * @param country      country name (must match dropdown option exactly)
     * @param state        state / province
     * @param city         city
     * @param zipcode      zip / postal code
     * @param mobileNumber mobile phone number
     */
    @Step("Fill address information")
    public RegisterPage fillAddressInfo(String firstName, String lastName, String company,
                                String address1, String address2, String country,
                                String state, String city, String zipcode,
                                String mobileNumber) {
        log.info("Filling address information for: {} {}", firstName, lastName);
        type(FIRST_NAME_INPUT, firstName);
        type(LAST_NAME_INPUT, lastName);
        type(COMPANY_INPUT, company);
        type(ADDRESS1_INPUT, address1);
        type(ADDRESS2_INPUT, address2);
        selectByVisibleText(COUNTRY_SELECT, country);
        type(STATE_INPUT, state);
        type(CITY_INPUT, city);
        type(ZIPCODE_INPUT, zipcode);
        type(MOBILE_NUMBER_INPUT, mobileNumber);
        return this;
    }

    /**
     * Clicks 'Create Account' button and returns AccountCreatedPage.
     *
     * @return AccountCreatedPage instance
     */
    @Step("Click 'Create Account' button")
    public AccountCreatedPage clickCreateAccount() {
        log.info("Clicking 'Create Account' button");
        click(CREATE_ACCOUNT_BUTTON);
        return new AccountCreatedPage();
    }
}