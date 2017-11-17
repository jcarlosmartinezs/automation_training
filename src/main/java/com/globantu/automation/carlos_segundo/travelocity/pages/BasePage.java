package com.globantu.automation.carlos_segundo.travelocity.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	
	public BasePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		
		wait = new WebDriverWait(driver, 30);
		this.driver = driver;
		actions = new Actions(this.driver);
	}
	
	public void dispose() {
		if(driver != null) {
			driver.quit();
		}
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected WebDriverWait getWait() {
		return wait;
	}
	
	protected Actions getActions() {
		return actions;
	}
	
	protected void waitUntilElementIsPresent(By byLocator, boolean ignoreTimeout) {
		if(ignoreTimeout) {
			getWait().ignoring(TimeoutException.class).until(ExpectedConditions.presenceOfElementLocated(byLocator));
		}else {
			getWait().until(ExpectedConditions.presenceOfElementLocated(byLocator));
		}
	}
	
	protected void waitUntilElementIsNotPresent(WebElement element) {
		getWait().until(ExpectedConditions.stalenessOf(element));
	}
	
	protected void waitUntilElementIsVisible(WebElement element) {
		getWait().until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitUntilElementIsNotVisible(WebElement element, boolean ignoreTimeout) {
		if(ignoreTimeout) {
			getWait().ignoring(TimeoutException.class).until(ExpectedConditions.invisibilityOf(element));
		}else {
			getWait().until(ExpectedConditions.invisibilityOf(element));
		}
	}
	
	protected void waitUntilElementIsClickable(WebElement element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
	}
	
	protected void selectElementByText(WebElement element, String selection) {
		new Select(element).selectByVisibleText(selection);
	}
	
	protected String getSelectedElementValue(WebElement element) {
		return new Select(element).getFirstSelectedOption().getAttribute("value");
	}

}
