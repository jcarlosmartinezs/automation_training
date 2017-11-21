package com.automation.training.starbucks.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

	private final String PAGE_URL = "https://www.starbucks.com/";
	
	private final String COFFEE_FINDER_URL = "https://athome.starbucks.com/coffee-finder/";
	
	private final String COFFEE_FINDER_OPTION_PATH = ".//a[@href='%s']";
	
	private final String MENU_ELEMENTS_PATH = ".//a";
	
	private final String COFFEE_MENU_ID = "menu_coffee";
	
	@FindBy(id="nav")
	private WebElement menuNavBar;
	
	@FindBy(id="nav_coffee")
	private WebElement coffeeNav;
	
	@FindBy(id=COFFEE_MENU_ID)
	private WebElement coffeeMenu;
	
	public HomePage(WebDriver driver) {
		super(driver);
		driver.get(PAGE_URL);
	}

	/**
	 * Validates the starbucks menu bar contains the indicated elements
	 * @param expectedElements A list with the expected elements in the menu bar
	 * @return <b>true</b> if all elements were found, <b>false</b> otherwise.
	 */
	public boolean validateMenu(List<String> expectedElements) {
		boolean validMenu = true;
		
		getWait().until(ExpectedConditions.visibilityOf(menuNavBar));
		
		List<WebElement> menuElements = menuNavBar.findElements(By.xpath(MENU_ELEMENTS_PATH));
		validMenu = !menuElements.isEmpty();

		if(validMenu) {
			List<WebElement> foundElements = menuElements.stream()
					.filter(element -> expectedElements.contains(element.getText()))
					.collect(Collectors.toList());
			
			validMenu = foundElements.size() == expectedElements.size();
		}
		
		return validMenu;
	}
	
	/**
	 * Gets the coffee finder page
	 * @return a {@link CoffeeFinderPage}
	 */
	public CoffeeFinderPage findCoffee() {
		Actions actions = new Actions(getDriver());
		
		getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(COFFEE_MENU_ID)));
		
		String finderPath = String.format(COFFEE_FINDER_OPTION_PATH, COFFEE_FINDER_URL);
		WebElement coffeeFinderOption = coffeeMenu.findElement(By.xpath(finderPath));
		
		actions.moveToElement(coffeeNav).perform();
		getWait().until(ExpectedConditions.elementToBeClickable(coffeeFinderOption));
		
		coffeeFinderOption.click();
		
		return new CoffeeFinderPage(getDriver());
	}
}
