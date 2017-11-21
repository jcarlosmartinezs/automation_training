package com.automation.training.starbucks.tests;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.training.starbucks.pages.CoffeeFinderPage;
import com.automation.training.starbucks.pages.HomePage;

public class StarbucksTests extends BaseTest {

	private final String[] ANSWERS_CASE1 = {
			"Balanced and easy-going", 
			"A busy day", 
			"Cocoa", 
			"I love to try something new and different"
		};
	
	private final String EXPECTED_RECOMENDATION_CASE1 = "House Blend";
	
	@Test
	@Parameters({"menuElements"})
	public void testValidMenu(String requiredElements) {
		List<String> menuElements = Arrays.asList(requiredElements.split(","));
		
		HomePage homePage = getHomePage();
		boolean validMenu = homePage.validateMenu(menuElements);
		
		Assert.assertTrue(validMenu);
	}
	
	@Test
	public void testGetCoffeeFinder() {
		HomePage homePage = getHomePage();
		CoffeeFinderPage finderPage = homePage.findCoffee();
		WebElement result;
		
		for(int i = 1; i < 5; i++) {
			result = finderPage.answerQuestion(i, ANSWERS_CASE1[i - 1]);
			Assert.assertNotNull(result);
			Assert.assertEquals(result.getText(), ANSWERS_CASE1[i - 1]);
		}
		
		result = finderPage.findCoffee();
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getText(), EXPECTED_RECOMENDATION_CASE1);
	}
}
