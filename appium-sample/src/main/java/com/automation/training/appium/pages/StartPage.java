package com.automation.training.appium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * A representation of the start page of the twitter app.
 * @author carlos.segundo
 *
 */
public class StartPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(StartPage.class);
	
	@FindBy(id="cta_button")
	@AndroidFindBy(id="cta_button")
	private WebElement startButton;
	
	@FindBy(id="sign_in_text")
	@AndroidFindBy(id="sign_in_text")
	private WebElement loginLink;
	
	public StartPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("StartPage is displayed");
	}
	
	/**
	 * Clicks the sign up option.
	 * @return a {@link RegisterPage} after the start button is clicked.
	 */
	public RegisterPage getStarted() {
		startButton.click();
		LOGGER.info("StartButton has been clicked");
		
		return new RegisterPage(getDriver());
	}
	
	/**
	 * Clicks the login option.
	 * @return a {@link LoginPage} after the login option is clicked.
	 */
	public LoginPage startLogin() {
		loginLink.click();
		LOGGER.info("LoginLink has been clicked");
		
		return new LoginPage(getDriver());
	}
}
