package com.automation.training.appium.pages;

import org.apache.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * A representation of the terms of service page of the twitter app.
 * @author carlos.segundo
 *
 */
public class TermsOfServicePage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(TermsOfServicePage.class);
	
	@AndroidFindBy(uiAutomator="new UiSelector().textContains(\"Terms of service\")")
	private MobileElement termsOfServiceView;
	
	public TermsOfServicePage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("TermsOfServicePage is displayed");
	}
	
}
