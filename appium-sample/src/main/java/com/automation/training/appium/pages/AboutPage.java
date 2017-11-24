package com.automation.training.appium.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

/**
 * Represents an about page of the twitter app.
 * @author carlos.segundo
 *
 */
public class AboutPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(AboutPage.class);

	private final String WEB_VIEW_CLASS = "android.webkit.WebView";
	
	private final String LINEAR_LAYOUT_CLASS = "android.widget.LinearLayout";
	
	@AndroidFindBy(className="android.widget.ListView")
	private MobileElement itemsList;
	
	@AndroidFindBy(uiAutomator="new UiSelector().textContains(\"Terms of service\")")
	private MobileElement termsOfServiceOption;
	
	@AndroidFindBy(uiAutomator="new UiSelector().textContains(\"Privacy policy\")")
	private MobileElement privacyPolicyOption;
	
	@AndroidFindBy(uiAutomator="new UiSelector().textContains(\"Legal notices\")")
	private MobileElement legalNoticesOption;
	
	private Wait<RemoteWebDriver> fluentWait;
	
	public AboutPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		LOGGER.info("AboutPage is displayed");
		
		// Sets FluentWait Setup
		fluentWait = new FluentWait<RemoteWebDriver>(getDriver())
		        .withTimeout(5, TimeUnit.SECONDS)
		        .pollingEvery(500, TimeUnit.MILLISECONDS)
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
	}
	
	/**
	 * Clicks the option to see the Terms of service
	 * @return A {@link TermsOfServicePage}
	 */
	public TermsOfServicePage viewTermsOfService() {
		termsOfServiceOption.click();
		
		WebElement webViewElement = fluentWait.until((d) -> d.findElement(MobileBy.className(WEB_VIEW_CLASS)));
		try {
			fluentWait.until((d) -> {
				String tosText = webViewElement.getText();
				LOGGER.info("Terms Of Service: " + tosText);
				return !StringUtils.isBlank(tosText) ? tosText : null;
			});
		}catch(TimeoutException e) {
			LOGGER.warn("Could not retrieve Terms Of Service text");
		}
		
		return new TermsOfServicePage(getDriver());
	}
	
	/**
	 * Clicks the option to see the Privacy policy
	 * @return a {@link PrivacyPolicyPage}
	 */
	public PrivacyPolicyPage viewPrivacyPolicy() {
		privacyPolicyOption.click();
		
		WebElement webViewElement = fluentWait.until((d) -> d.findElement(MobileBy.className(WEB_VIEW_CLASS)));
		try {
			fluentWait.until((d) -> {
				String ppText = webViewElement.getText();
				LOGGER.info("Privacy Policy: " + ppText);
				return !StringUtils.isBlank(ppText) ? ppText : null;
			});
		}catch(TimeoutException e) {
			LOGGER.warn("Could not retrieve Privacy Policy text");
		}
		
		return new PrivacyPolicyPage(getDriver());
	}
	
	/**
	 * Clicks the option to see the Legal notices
	 * @return a {@link LegalNoticesPage}
	 */
	public LegalNoticesPage viewLegalNotices() {
		legalNoticesOption.click();
		
		WebElement webViewElement = fluentWait.until((d) -> d.findElement(MobileBy.className(WEB_VIEW_CLASS)));
		try {
			fluentWait.until((d) -> {
				String lnText = webViewElement.getText();
				LOGGER.info("Legal Notices: " + lnText);
				return !StringUtils.isBlank(lnText) ? lnText : null;
			});
		}catch(TimeoutException e) {
			LOGGER.warn("Could not retrieve Legal Notices text");
		}
		
		return new LegalNoticesPage(getDriver());
	}
	
	/**
	 * Gets a list of the clickable options of this page
	 * @return a List of {@link MobileElement} for the elements found
	 */
	public List<MobileElement> getMenuElements() {
		return itemsList.findElementsByClassName(LINEAR_LAYOUT_CLASS);
	}
	
}
