package com.company.automation.base;

import com.company.automation.utils.ScreenshotUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  @Override
  public void onTestFailure(ITestResult result) {
    ScreenshotUtils.attachScreenshot();
  }
}
