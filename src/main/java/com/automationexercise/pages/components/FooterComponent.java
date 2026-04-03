package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component Object for the shared Footer.
 * Present on all pages - encapsulates footer locators and interactions.
 * <p>
 * Usage in any Page class:
 *  footer().isSubscriptionVisible();
 *  footer().subscribeWithEmail("test@test.com");
 */
public class FooterComponent extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(FooterComponent.class);

    // ==================== LOCATORS ====================

    private static final By SUBSCRIPTION_HEADING = By.cssSelector("#footer .single-widget h2");
    private static final By SUBSCRIPTION_INPUT   = By.cssSelector("#susbscribe_email");
    private static final By SUBSCRIPTION_BUTTON  = By.cssSelector("#subscribe");
    private static final By SUBSCRIPTION_SUCCESS = By.cssSelector("#success-subscribe .alert-success");

    // ==================== ACTIONS ====================

    /**
     * Scrolls down to footer section.
     * Step 4 of TC10 - footer is below the fold on most screens.
     */
    @Step("Scroll down to footer")
    public void scrollToFooter() {
        log.info("Scrolling to footer");
        scrollToElement(SUBSCRIPTION_HEADING);
    }

    /**
     * Returns the subscription heading text.
     * Used for assertion in TC10 step 5.
     *
     * @return subscription heading text
     */
    @Step("Get subscription heading text")
    public String getSubscriptionHeadingText() {
        return  getText(SUBSCRIPTION_HEADING);
    }

    /**
     * Verifies 'SUBSCRIPTION' heading is visible in the footer.
     *
     * @return true if heading is visible
     */
    @Step("Verify 'SUBSCRIPTION' heading is visible")
    public boolean isSubscriptionHeadingVisible() {
        boolean visible = isDisplayed(SUBSCRIPTION_HEADING);
        log.info("'SUBSCRIPTION' heading visible: {}", visible);
        return visible;
    }

    /**
     * Enters email and clicks subscribe button.
     * Step 6 - always performed together (YAGNI).
     *
     * @param email the email address to subscribe with
     */
    @Step("Subscribe with email: {email}")
    public void subscribeWithEmail(String email) {
        log.info("Subscribing with email: {}", email);
        type(SUBSCRIPTION_INPUT, email);
        click(SUBSCRIPTION_BUTTON);
    }

    /**
     * Returns the success message text after subscription.
     * Used for assertion in TC10 step 7.
     *
     * @return success message text
     */
    @Step("Get subscription success message text")
    public String getSubscriptionSuccessMessage() {
        String message = getText(SUBSCRIPTION_SUCCESS);
        log.info("Subscription success message: '{}'", message);
        return message;
    }

    /**
     * Verifies subscription success message is visible.
     *
     * @return true if success message is visible.
     */
    @Step("Verify subscription success message is visible")
    public boolean isSubscriptionSuccessVisible() {
        return isDisplayed(SUBSCRIPTION_SUCCESS);
    }
}