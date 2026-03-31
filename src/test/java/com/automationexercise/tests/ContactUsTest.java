package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.ContactUsPage;
import com.automationexercise.pages.HomePage;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Test class for TC06: Contact Us Form.
 * No account precondition needed - contact form is publicly accessible.
 */
@Epic("Content")
@Feature("Contact Us")
public class ContactUsTest extends BaseTest {

    private final Faker faker = new Faker();

    @Test(description = "TC06 - Contact Us Form")
    @Story("Submit contact us form successfully")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'Contact Us' button
            4. Verify 'GET IN TOUCH' is visible
            5. Fill name, email, subject, message
            6. Upload file
            7. Click Submit and accept alert
            8. Verify success message is visible
            9. Click Home button and verify home page is loaded
            """)
    public void testContactUsForm() {

        // -- Generate test data --
        String name    = faker.name().fullName();
        String email   = faker.internet().emailAddress();
        String subject = faker.lorem().sentence();
        String message = faker.lorem().paragraph();

        // -- Resolve upload file path from test resources --
        String filePath = new File("src/test/resources/testdata/test-upload.txt")
                .getAbsolutePath();

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Contact Us --
        ContactUsPage contactUsPage = homePage.header().clickContactUs();

        // -- Step 5: Verify 'GET IN TOUCH' is visible --
        Assert.assertEquals(contactUsPage.getGetInTouchHeadingText(), "GET IN TOUCH",
                "'GET IN TOUCH' heading should be visible");

        // -- Step 6: Fill contact form --
        contactUsPage.fillContactForm(name, email, subject, message);

        // -- Step 7: Upload file --
        contactUsPage.uploadFile(filePath);

        // -- Step 8: Submit form and accept alret --
        contactUsPage.submitForm();

        // -- Step 10: Verify success message --
        Assert.assertTrue(
                contactUsPage.getSuccessMessageText()
                        .contains("Success! Your details have been submitted successfully."),
                "Success message shhould be visible after form submission"
        );

        // -- Step 11: Click Home and verify home page --
        homePage = contactUsPage.header().clickHome();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Should be navigated back to home page successfully");
    }
}