package com.automation.training.starbucks.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	private WebDriver driver;
	private WebDriverWait wait;
	
	public BasePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		
		wait = new WebDriverWait(driver, 10);
		this.driver = driver;
	}
	
	public void dispose() {
		if(driver != null) {
			driver.quit();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebDriverWait getWait() {
		return wait;
	}
}
