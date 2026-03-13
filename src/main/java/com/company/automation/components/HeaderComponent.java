package com.company.automation.components;

import com.company.automation.core.BasePage;
import org.openqa.selenium.By;

public class HeaderComponent extends BasePage {

    private final By homeLink = By.xpath("//a[contains(text(),'Home')]");
    private final By loginLink =  By.xpath("//a[contains(text(),'Login')]");

    public void clickHome() {
        click(homeLink);
    }

    public void clickLogin() {
        click(loginLink);
    }
}
