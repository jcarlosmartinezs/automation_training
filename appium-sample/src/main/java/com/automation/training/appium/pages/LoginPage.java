package com.automation.training.appium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * A representation of the login page of the twitter app.
 * @author carlos.segundo
 *
 */
public class LoginPage extends BasePage {

	private static final Logger LOGGER = Logger.getLogger(LoginPage.class);

	@FindBy(id="login_identifier")
	@AndroidFindBy(id="login_identifier")
	private MobileElement usernameInput;

	@FindBy(id="login_password")
	@AndroidFindBy(id="login_password")
	private MobileElement passwordInput;

	@FindBy(id="login_login")
	@AndroidFindBy(id="login_login")
	private MobileElement loginButton;
	
	@AndroidFindBy(id="overflow")
	private MobileElement menu;
	
	@AndroidFindBy(uiAutomator="new UiSelector().textContains(\"About\")")
	private MobileElement aboutOption;

	public LoginPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("LoginPage is displayed");
	}

	/**
	 * Fills the username and password inputs and tries to login by clicking the login button.
	 * @param username The username
	 * @param password The password
	 */
	public void login(String username, String password) {
		LOGGER.info("Login with username: " + username + ", and password: " + password);

		usernameInput.sendKeys(username);
		passwordInput.sendKeys(password);

		loginButton.click();
		waitLoadingMaskToDissapear();
	}
	
	/**
	 * Clicks the about option from the top menu.
	 * @return An {@link AboutPage} after the option is selected.
	 */
	public AboutPage getAboutPage() {
		menu.click();
		aboutOption.click();
		
		return new AboutPage(getDriver());
	}
	
}
