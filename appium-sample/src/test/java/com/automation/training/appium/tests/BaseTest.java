package com.automation.training.appium.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.automation.training.appium.AndroidAppDriver;
import com.automation.training.appium.AndroidAppDriver.AndroidDriverBuilder;
import com.automation.training.appium.pages.StartPage;

public class BaseTest {

	public static final String APPIUM_URL_PARAMETER_NAME = "appiumUrl";
	
	public static final String DEVICE_NAME_PARAMETER_NAME = "deviceName";
	
	public static final String APP_FILE_PARAMETER_NAME = "appFile";
	
	public static final String APP_PACKAGE_PARAMETER_NAME = "appPackage";
	
	public static final String START_ACTIVITY_PARAMETER_NAME = "startActivity";
	
	private AndroidAppDriver driver;
	
	private Properties testParameters;
	
	private StartPage startPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"parametersFile"})
	public void setUp(String parametersFile) throws IOException {
		this.testParameters = new Properties();
		testParameters.load(new FileInputStream(parametersFile));
		
		this.driver = new AndroidDriverBuilder(testParameters.getProperty(APPIUM_URL_PARAMETER_NAME))
				.withDevice(testParameters.getProperty(DEVICE_NAME_PARAMETER_NAME))
				.withAppFile(testParameters.getProperty(APP_FILE_PARAMETER_NAME))
				.withAppPackage(testParameters.getProperty(APP_PACKAGE_PARAMETER_NAME))
				.withStartActivity(testParameters.getProperty(START_ACTIVITY_PARAMETER_NAME))
				.build();
		startPage = new StartPage(driver.getDriver());
	}

	public StartPage getStartPage() {
		return startPage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.dispose();
	}

}
