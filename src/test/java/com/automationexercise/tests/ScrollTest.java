package com.automationexercise.tests;


import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test classs for scroll functionality.
 * TC25: Verify Scroll Up using 'Arrow' button and Scroll Down functionality.
 * TC26: Verify Scroll Up without 'Arrow' button and Scroll Down functionality.
 */
@Epic("UI")
@Feature("Scroll")
public class ScrollTest extends BaseTest {

    @Test(description = "TC25 - Verify Scroll Up using Arrow button and Scroll Down")
    @Story("Scroll down to footer and scroll back up using arrow button")
    @Severity(SeverityLevel.NORMAL)
    @Description("""
            Steps:
            1. Navigate to home page
            2. Verify home page is visible
            3. Scroll down to bottom of page
            4. Verify 'SUBSCRIPTION' is visible in footer
            5. Click scroll up arrow button
            6. Verify page scrolled to top — hero text visible
            """)
    public void testScrollUpUsingArrowButton() {

        // -- Step 2, 3: Open app and verify home page --
        HomePage homePage = app.open();
        Assert.assertTrue(homePage.isHomePageVisible(),
                "Home page should be visible");

        // -- Step 4: Scroll down to bottom --
        homePage.scrollToRecommendedSection();

        // -- Step 5: Verify 'SUBSCRIPTION' is visible --
        Assert.assertEquals(homePage.footer().getSubscriptionHeadingText(),
                "SUBSCRIPTION",
                "'SUBSCRIPTION' heading should be visible in footer");

        // -- Step 6: Click scroll up arrow --
        homePage.clickScrollUpArrow();

        // -- Step 7: Verify page scrolled to top --
        Assert.assertTrue(homePage.isHeroTextVisible(),
                "Hero text should be visible after scrolling up");
        Assert.assertTrue(
                homePage.getHeroText().contains("Full-Fledged practice website for Automation Engineers"),
                "Hero text should contain expected content after scroll up"
        );
    }
}