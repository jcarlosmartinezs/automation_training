package com.globantu.automation.carlos_segundo.travelocity.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.globantu.automation.carlos_segundo.BrowserDriver;
import com.globantu.automation.carlos_segundo.BrowserDriver.Browsers;
import com.globantu.automation.carlos_segundo.travelocity.pages.HomePage;

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
