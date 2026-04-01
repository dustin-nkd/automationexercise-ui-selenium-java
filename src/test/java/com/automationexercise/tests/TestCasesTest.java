package com.automationexercise.tests;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.TestCasesPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for TC07: Verify Test Cases Page.
 * No account precondition needed - page is publicly accessible.
 */
@Epic("Content")
@Feature("Test Cases")
public class TestCasesTest extends BaseTest {

    @Test(description = "TC07 - Verify Test Cases Page")
    @Story("Navigate to Test Cases page successfully")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Click 'Test Cases' button
            4. Verify user is navigated to test cases page successfully
            """)
    public void testVerifyTestCasesPage() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Click Test Cases --
        TestCasesPage testCasesPage = homePage.header().clickTestCases();

        // -- Step 5: Verify user is on Test Cases page --
        Assert.assertTrue(testCasesPage.isOnTestCasesPage(),
                "User should be navigated to Test Cases page");
    }
}