package com.automation.training.appium.pages;

import org.apache.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * A representation of the register page of the twitter app.
 * @author carlos.segundo
 *
 */
public class RegisterPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(RegisterPage.class);
	
	public RegisterPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("RegisterPage is displayed");
	}
}
