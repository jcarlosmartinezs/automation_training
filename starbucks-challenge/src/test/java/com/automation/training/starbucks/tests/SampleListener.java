package com.automation.training.starbucks.tests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class SampleListener implements ITestListener {

	public void onStart(ITestContext arg0) {

	}
	
	public void onFinish(ITestContext arg0) {

	}
	
	public void onTestStart(ITestResult arg0) {

	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	public void onTestSkipped(ITestResult arg0) {

	}

	public void onTestSuccess(ITestResult arg0) {
		System.out.println("El test pasó!\n");
	}
	
	public void onTestFailure(ITestResult arg0) {
		System.out.println("El test falló :(\n");
	}

}
