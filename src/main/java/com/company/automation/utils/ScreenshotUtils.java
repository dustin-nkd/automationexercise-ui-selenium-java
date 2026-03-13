package com.company.automation.utils;

import com.company.automation.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class ScreenshotUtils {

    public static void attachScreenshot() {

        byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment("Screenshot",
                new ByteArrayInputStream(screenshot)
        );
    }
}
