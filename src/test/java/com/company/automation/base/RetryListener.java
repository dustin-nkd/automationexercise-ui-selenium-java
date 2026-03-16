package com.company.automation.base;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

  @Override
  public void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

    Class<? extends IRetryAnalyzer> retry = annotation.getRetryAnalyzerClass();

    if (retry == RetryAnalyzer.class) {
      annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
  }
}
