package com.automationexercise.pages;

import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object for the Contact Us Page (/contact_us).
 */
public class ContactUsPage extends BasePage{

    private static final Logger log = LoggerFactory.getLogger(ContactUsPage.class);

    // ==================== LOCATORS ====================

    private static final By GET_IN_TOUCH_HEADING = By.cssSelector(".contact-form h2");
    private static final By NAME_INPUT           = By.cssSelector("input[data-qa='name']");
    private static final By EMAIL_INPUT          = By.cssSelector("input[data-qa='email']");
    private static final By SUBJECT_INPUT        = By.cssSelector("input[data-qa='subject']");
    private static final By MESSAGE_INPUT        = By.cssSelector("textarea[data-qa='message']");
    private static final By FILE_UPLOAD_INPUT    = By.cssSelector("input[type='file']");
    private static final By SUBMIT_BUTTON        = By.cssSelector("input[data-qa='submit-button']");
    private static final By SUCCESS_MESSAGE      = By.cssSelector(".contact-form .alert-success");

    // ==================== ACTIONS ====================

    /**
     * Verifies 'GET IN TOUCH' heading is visible on the page.
     *
     * @return heading text for assertion
     */
    @Step("Get 'GET IN TOUCH' heading text")
    public String getGetInTouchHeadingText() {
        return getText(GET_IN_TOUCH_HEADING);
    }

    /**
     * Fills all contact form fields.
     * Step 6 - always filled together before submission.
     *
     * @param name    sender's name
     * @param email   sender's email
     * @param subject message subject
     * @param message message body
     */
    @Step("Fill contact form - name: {name}, email: {email}, subject: {subject}")
    public void fillContactForm(String name, String email,
                                String subject, String message) {
        log.info("Filling contact form for: '{}'", name);
        type(NAME_INPUT, name);
        type(EMAIL_INPUT, email);
        type(SUBJECT_INPUT, subject);
        type(MESSAGE_INPUT, message);
    }

    /**
     * Uploads a file via the file input element.
     * Step 7 - sends absolute file path directly to input[type='file']
     *
     * @param filePath absolute path to the file to upload
     */
    @Step("Upload file: {filePath}")
    public void uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        uploadFile(FILE_UPLOAD_INPUT, filePath);
    }

    /**
     * Clicks 'Submit' button then accepts the browser alert dialog.
     * Step 8 + 9 - alert appers immediately after submit click.
     * Always performed together - no test case need them split (YAGNI).
     */
    @Step("Click 'Submit' button and accept alert")
    public void submitForm() {
        log.info("Submitting contact form");
        click(SUBMIT_BUTTON);
        acceptAlert();
    }

    /**
     * Returns the success message text after form submission.
     * Step 10 - used for assertion.
     *
     * @return success message text
     */
    @Step("Get success message text")
    public String getSuccessMessageText() {
        String message = getText(SUCCESS_MESSAGE);
        log.info("Success message: {}", message);
        return message;
    }

    /**
     * Returns HeaderComponent for navigation.
     *
     * @return HeaderComponent instance
     */
    public HeaderComponent header() {
        return new HeaderComponent();
    }
}