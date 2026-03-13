package com.company.automation.pages;

import com.company.automation.config.ConfigReader;
import com.company.automation.core.BasePage;
import com.company.automation.components.HeaderComponent;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By logo =
            By.xpath("//img[@alt='Website for automation practice']");

    private final HeaderComponent header = new HeaderComponent();

    public void open() {
        openUrl(ConfigReader.get("baseUrl"));
    }

    public boolean isHomePageDisplayed() {
        return isDisplayed(logo);
    }

    public void goToLogin() {
        header.clickLogin();
    }
}