package com.automation.training.appium.pages;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * An abstract representation of a generic page. It is not platform dependent. 
 * Specific platform behaviours should be implemented extending from this class.
 * @author carlos.segundo
 *
 */
public abstract class BasePage {
	
	private final int TIMEOUT = 15;
	
	private final String LOADING_LOCATOR = "//AndroidLoading | //iOSLoading";
	
	private WebDriverWait wait;
	
	private AppiumDriver<MobileElement> driver;
	
	public BasePage(AppiumDriver<MobileElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(
				driver, TIMEOUT, TimeUnit.SECONDS), this);
		
		this.driver = driver;
		wait = new WebDriverWait(driver, 30);
	}

	protected AppiumDriver<MobileElement> getDriver() {
		return driver;
	}
	
	protected WebDriverWait getWait() {
		return wait;
	}
	
	/**
	 * Closes the current appium session and every associated window.
	 */
	public void dispose() {
		if(driver != null) {
			driver.quit();
		}
	}
	
	/**
	 * Clicks the device's back button.
	 */
	@SuppressWarnings("rawtypes")
	public void goBack() {
		if(driver instanceof AndroidDriver) {
			((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
		}
	}
	
	/**
	 * Scrolls up performing a touch from the center top to the center bottom of the screen.
	 * @param durationInMillis The duration of the movement.
	 */
	public void scrollUp(int durationInMillis) {
		Dimension size = driver.manage().window().getSize();
		
		int screenMiddle = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.10);
		int endY = (int) (size.getHeight() * 0.75);
		
		new TouchAction(driver)
				.press(screenMiddle, startY)
				.waitAction(Duration.ofMillis(durationInMillis))
				.moveTo(screenMiddle, endY)
				.release()
				.perform();
	}
	
	/**
	 * Scrolls down performing a touch from the center bottom to the center top of the screen.
	 * @param durationInMillis The duration of the movement.
	 */
	public void scrollDown(int duration) {
		Dimension size = driver.manage().window().getSize();
		
		int screenMiddle = size.getWidth() / 2;
		int startY = (int) (size.getHeight() * 0.90);
		int endY = (int) (size.getHeight() * 0.25);
		
		new TouchAction(driver)
				.press(screenMiddle, startY)
				.waitAction(Duration.ofMillis(duration))
				.moveTo(screenMiddle, endY)
				.release()
				.perform();
    }

	/**
	 * Tries to locate a loading indicator and if present waits for it to disappear.
	 */
	public void waitLoadingMaskToDissapear() {
		WebElement waitElement = null;
		 
		//Sets FluentWait Setup
		FluentWait<RemoteWebDriver> fwait = new FluentWait<RemoteWebDriver>(driver)
		        .withTimeout(2, TimeUnit.SECONDS)
		        .pollingEvery(500, TimeUnit.MILLISECONDS)
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
			waitElement = fwait.until((d) -> d.findElement(By.xpath(LOADING_LOCATOR)));
		} catch (Exception e) {}
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		if (waitElement != null) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOADING_LOCATOR)));
		}
	}
}
