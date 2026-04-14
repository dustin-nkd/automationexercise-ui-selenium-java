package com.automationexercise;

import com.automationexercise.config.ConfigManager;
import com.automationexercise.driver.DriverManager;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.components.HeaderComponent;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application entry point - represents the application under test.
 * Responsible for launching the app and returning the initial Page Object.
 * <p>
 * Usage iin test (via BaseTest):
 *  HomePage  homePage = app.open();
 * <p>
 * This pattern ensures:
 *  - Browser is always open before any Page Object is created
 *  - Single entry point for navigating to the app
 *  - Test read naturally: "app opens, returns HomePage"
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    /**
     * Navigates to the base URL and returns the HomePage.
     * Browser is guaranteed to be open at this point (initialized in BaseTest.setUp).
     *
     * @return HomePage instance representing the loaded home page
     */
    @Step("Open application")
    public HomePage open() {
        String url = ConfigManager.get("base.url");
        log.info("Opening application at: {}", url);
        DriverManager.getDriver().get(url);
        return new HomePage();
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