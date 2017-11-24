package com.automation.training.appium.pages;

import static java.lang.String.format;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * A class to extend specific android behaviours.
 * @author carlos.segundo
 *
 */
public abstract class AndroidPage extends BasePage {

	public static final String SEPARATOR = System.getProperty("line.separator");
	
	private static final String UISELECTOR_CONSTRUCTOR = "new UiSelector().%s";
	
	private static final Logger LOGGER = Logger.getLogger(AndroidPage.class);
	
	public AndroidPage(AppiumDriver<MobileElement> driver) {
		super(driver);
	}
	
	/**
	 * Gets a list of elements that contains the given text using the android uiautomator
	 * @param text The text contained in the elements to locate
	 * @return A List of {@link AndroidElement} with the elements found containing the given text.
	 */
	public List<AndroidElement> getElementsByTextContatins(String text) {
		By locator = MobileBy.AndroidUIAutomator(
				format(UISELECTOR_CONSTRUCTOR, "textContains(\"" + text + "\")"));
		
		return findElements(locator); 
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<AndroidElement> findElements(By locator) {
		List<AndroidElement> elements = (List<AndroidElement>) ((AndroidDriver) getDriver()).findElements(locator);
		LOGGER.info("Found: " + elements.size() + " elements" + SEPARATOR);
		
		for (AndroidElement element : elements) {
			Point location = element.getLocation();
			Dimension size = element.getSize();
			LOGGER.info("---> Text: " + element.getText());
			LOGGER.info("---> TagName: " + element.getTagName());
			LOGGER.info("---> Enabled: " + element.isEnabled());
			LOGGER.info("---> Selected: " + element.isSelected());
			LOGGER.info("---> Displayed: " + element.isDisplayed());
			LOGGER.info("---> Location: [X=" + location.getX() + " Y=" + location.getY() + "]");
			LOGGER.info("---> Size: [Height=" + size.getHeight() + " Width=" + size.getWidth() + "]" + SEPARATOR);
		}
		
		return elements;
	}
}
