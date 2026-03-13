package com.company.automation.tests;

import com.company.automation.base.BaseTest;
import com.company.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest{

    @Test
    public void shouldNavigateToLoginPage() {

        HomePage homePage = new HomePage();

        homePage.open();

        homePage.goToLogin();
    }
}
