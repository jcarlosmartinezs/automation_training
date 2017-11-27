package com.automation.training.appium.listener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporter implements IReporter {

    private ExtentReports extent;

    public ExtentReporter() {
    }

    public ExtentReporter(ExtentReports extent) {
        this.extent = extent;
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        String allSuites = "";
        extent = new ExtentReports(outputDirectory + File.separator + "results.html");

        for (ISuite suite : suites) {
            allSuites = allSuites.concat(" ").concat(suite.getName());
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }
        extent.flush();
        try {
            extent.close();
        } catch(Exception exception) {
            System.out.println("Skipping extent report because of: " + exception.getMessage());
        }
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getMethod().getMethodName())
                        .assignCategory(result.getMethod().getGroups());
                test.getTest().setStartedTime(getTime(result.getStartMillis()));
                test.getTest().setEndedTime(getTime(result.getEndMillis()));
                test.getTest().setDescription(result.getMethod().getDescription());
                String message = "Test " + status.toString().toLowerCase() + "ed";
                List<String> reporterLoggedMessages = Reporter.getOutput(result);
                // Logs inside ExtentReports the custom messages.
                for (String logMessage : reporterLoggedMessages) {
                    test.log(LogStatus.PASS, logMessage);
                }
                if (status.equals(LogStatus.FAIL)) {
                    logScreenshot(status, result, test);
                }

                if (result.getAttribute("warn") != null) {
                    test.log(LogStatus.WARNING, result.getAttribute("warn").toString());
                    test.log(LogStatus.WARNING, "Warned test");
                    logScreenshot(LogStatus.WARNING, result, test);
                } else {
                    test.log(status, message);

                    if (result.getThrowable() != null) {
                        test.log(status, result.getThrowable());
                    }
                }
                extent.endTest(test);
            }
        }
    }

    private void logScreenshot(LogStatus status, ITestResult result, ExtentTest test) {
        String img = test.addScreenCapture("screenshots\\"
                + result.getInstanceName() + result.getMethod().getMethodName() + "_" + result.getStatus() + ".png");
        test.log(status, img);
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

//    public static String milisToTimestamp(long milis) {
//        String timestamp = (new Timestamp(milis)).toString();
//        String[] charsToReplace = new String[] { "-", ":", ".", " " };
//        String validTimestamp = StringUtils.replaceInvalidChars(timestamp, charsToReplace, "_");
//        return validTimestamp;
//    }

}