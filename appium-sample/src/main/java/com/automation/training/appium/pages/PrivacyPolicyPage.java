package com.automation.training.appium.pages;

import org.apache.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * A representation of the privacy policy page of the twitter app.
 * @author carlos.segundo
 *
 */
public class PrivacyPolicyPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(PrivacyPolicyPage.class);
	
	public PrivacyPolicyPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("PrivacyPolicyPage is displayed");
	}
	
}
