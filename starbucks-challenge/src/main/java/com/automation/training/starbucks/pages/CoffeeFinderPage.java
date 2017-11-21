package com.automation.training.starbucks.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * A representation of the starbucks coffee finder page.
 * @author carlos.segundo
 *
 */
public class CoffeeFinderPage extends BasePage {
	
	private final String BUTTONS_PATH = ".//button[@data-event='%s']";
	
	private final String QUESTION_BLOCK_SELECTOR = "div.question-block.question%s";

	private final String QUESTION3_BLOCK_PATH = ".//div[contains(@class,'question-block')]";
	
	private final String QUESTION3_BLOCK_ID = "question3";
	
	private final String QUESTION_SELECTION_PATH = ".//h4";
	
	private final String COFFEE_RECOMENDATION_PATH = ".//h3";
	
	@FindBy(id="find-my-coffee")
	private WebElement findCoffeeButton;
	
	@FindBy(id="featured-coffees-stack")
	private WebElement featuredCoffeesDiv;
	
	public CoffeeFinderPage(WebDriver driver) {
		super(driver);
	}

	public WebElement answerQuestion(int questionNumber, String answer) {
		WebElement questionBlockDiv = null;
		WebElement selection = null;
		
		try {
			if(questionNumber == 1 || questionNumber == 2 || questionNumber == 4) {
				questionBlockDiv = getDriver().findElement(By.cssSelector(String.format(QUESTION_BLOCK_SELECTOR, questionNumber)));
				
				WebElement answerButton = questionBlockDiv.findElement(By.xpath(String.format(BUTTONS_PATH, answer)));
				getWait().until(ExpectedConditions.elementToBeClickable(answerButton));
				
				answerButton.click();
				selection = questionBlockDiv.findElement(By.xpath(QUESTION_SELECTION_PATH));
			}else if(questionNumber == 3) {
				questionBlockDiv = getDriver().findElement(By.id(QUESTION3_BLOCK_ID));
				
				List<WebElement> questionDivs = questionBlockDiv.findElements(By.xpath(QUESTION3_BLOCK_PATH));
				WebElement activeQuestionDiv = questionDivs.stream().filter(d -> d.isDisplayed()).findFirst().orElse(null);
				
				if(activeQuestionDiv != null) {
					WebElement answerButton = activeQuestionDiv.findElement(By.xpath(String.format(BUTTONS_PATH, answer)));
					getWait().until(ExpectedConditions.elementToBeClickable(answerButton));
					
					answerButton.click();
					
					List<WebElement> resultDivs = questionBlockDiv.findElements(By.xpath(QUESTION_SELECTION_PATH));
					selection = resultDivs.stream().filter(d -> d.isDisplayed()).findFirst().orElse(null);
				}
			}
		}catch(NoSuchElementException e) {
			System.err.println("No div for question " + questionNumber + " found");
		}
		
		return selection;
	}
	
	public WebElement findCoffee() {
		WebElement recomendation = null;
		Actions actions = new Actions(getDriver());
		getWait().until(ExpectedConditions.elementToBeClickable(findCoffeeButton));
		
		actions.moveToElement(findCoffeeButton).perform();
		findCoffeeButton.click();
		getWait().until(ExpectedConditions.visibilityOf(featuredCoffeesDiv));
		
		List<WebElement> recomendationDivs = featuredCoffeesDiv.findElements(By.xpath(COFFEE_RECOMENDATION_PATH));
		recomendation = recomendationDivs.stream().filter(d -> d.isDisplayed()).findFirst().orElse(null);
		
		return recomendation;
	}
	
}
