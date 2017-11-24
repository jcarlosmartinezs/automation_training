package com.automation.training.appium.pages;

import org.apache.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * A representation of the legal notices page of the twitter app.
 * @author carlos.segundo
 *
 */
public class LegalNoticesPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(LegalNoticesPage.class);
	
	public LegalNoticesPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("LegalNoticesPage is displayed");
	}
	
}
