package com.automation.training.starbucks.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.automation.training.BrowserDriver;
import com.automation.training.BrowserDriver.Browsers;
import com.automation.training.starbucks.pages.HomePage;

public class BaseTest {
	BrowserDriver browserDriver;
	
	private HomePage homePage;
	
	@BeforeSuite(alwaysRun=true)
	@Parameters({"browser", "driverLocation"})
	public void beforeSuite(String browser, String driverLocation) {
		browserDriver = new BrowserDriver(Browsers.valueOf(browser), driverLocation);
	}

	@BeforeMethod
	public void getHome() {
		homePage = new HomePage(browserDriver.getDriver());
	}
	
	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		homePage.dispose();
	}
	
	public HomePage getHomePage() {
		return homePage;
	}
	
}
