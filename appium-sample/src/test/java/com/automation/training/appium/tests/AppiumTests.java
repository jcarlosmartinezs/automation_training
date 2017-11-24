package com.automation.training.appium.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.automation.training.appium.pages.AboutPage;
import com.automation.training.appium.pages.LegalNoticesPage;
import com.automation.training.appium.pages.LoginPage;
import com.automation.training.appium.pages.PrivacyPolicyPage;
import com.automation.training.appium.pages.StartPage;
import com.automation.training.appium.pages.TermsOfServicePage;

import io.appium.java_client.MobileElement;

public class AppiumTests extends BaseTest {

	private static final Logger LOGGER = Logger.getLogger(AppiumTests.class);
	
	private final String EXISTING_USERNAME = "sosmeuser";
	
	private final String EXISTING_PASSWORD = "somsepassword";
	
	private final int ABOUT_ELEMENTS_COUNT = 6;
	
	@Test
	public void testApi() throws InterruptedException {
		StartPage startPage = getStartPage();
		
		LoginPage loginPage = startPage.startLogin();
		loginPage.login(EXISTING_USERNAME, EXISTING_PASSWORD);
		
		AboutPage aboutPage = loginPage.getAboutPage();
		List<MobileElement> menuElements = aboutPage.getMenuElements();
		assertEquals(menuElements.size(), ABOUT_ELEMENTS_COUNT);
		
		TermsOfServicePage tosPage = aboutPage.viewTermsOfService();
		tosPage.scrollDown(1000);
		tosPage.goBack();
		
		PrivacyPolicyPage ppPage = aboutPage.viewPrivacyPolicy();
		ppPage.scrollDown(1000);
		ppPage.goBack();
		
		LegalNoticesPage lnPage = aboutPage.viewLegalNotices();
		lnPage.scrollDown(1000);
		lnPage.goBack();
	}

}
