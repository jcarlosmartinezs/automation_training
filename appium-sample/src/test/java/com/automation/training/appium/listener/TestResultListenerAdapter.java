package com.automation.training.appium.listener;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.automation.training.appium.tests.BaseTest;

public class TestResultListenerAdapter extends TestListenerAdapter {
	private static final Logger LOGGER = Logger.getLogger(TestResultListenerAdapter.class);

    @Override
    public void onStart(ITestContext tc) {
        super.onStart(tc);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        LOGGER.info(tr.getInstanceName() + "." + tr.getMethod().getMethodName() + " -> Skipped");
        runAfterMethod(tr);
        super.onTestSkipped(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
    	LOGGER.info(tr.getInstanceName() + "." + tr.getMethod().getMethodName() + " -> Failed");
        takeScreenshot(tr);
        super.onTestFailure(tr);
    }

    private void runAfterMethod(ITestResult tr) {
        BaseTest testInstance = (BaseTest) tr.getMethod().getInstance();
        testInstance.tearDown();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
    	LOGGER.info(tr.getInstanceName() + "." + tr.getMethod().getMethodName() + " -> Success");
        super.onTestSuccess(tr);
    }

    @Override
    public void onFinish(ITestContext tc) {
        super.onFinish(tc);
    }

    private void takeScreenshot(ITestResult tr) {
    	try {
	        File screenshot = ((BaseTest)tr.getMethod().getInstance()).takeScreenshot();
	        String filePath = "target/surefire-reports" + "/screenshots/"
	                + tr.getInstanceName() + tr.getMethod().getMethodName() + "_" + tr.getStatus() + ".png";
	        File screenshotInPath = new File(filePath);
			FileUtils.copyFile(screenshot, screenshotInPath);
		} catch (IOException e) {
			LOGGER.error("Unable to save screenshot: " + e.getMessage(), e);
		}finally {
			runAfterMethod(tr);
		}
    }

}