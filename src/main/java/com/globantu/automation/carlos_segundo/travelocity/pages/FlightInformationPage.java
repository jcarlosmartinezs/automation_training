package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;

/**
 * A representation of the travelocity flight details page.
 * @author carlos.segundo
 *
 */
public class FlightInformationPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(FlightInformationPage.class);

	private final String CONTINUE_BUTTON_ID = "bookButton";
	
	private final String AIRLINE_NAME_LABEL_PATH = "//h4[@class='airlineName']";
	
	private final String DEPARTURE_TIME_LABEL_PATH = "//div[@class='departure']/span";
	
	private final String ARRIVAL_TIME_LABEL_PATH = "//div[@class='arrival']/span";
	
	private final String DEPARTURE_AIRPORT_LABEL_PATH = "//div[@class='departure']/div";
	
	private final String ARRIVAL_AIRPORT_LABEL_PATH = "//div[@class='arrival']/div";
	
	private FlightDetails departureDetails;
	
	private FlightDetails returnDetails;
	
	public FlightInformationPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Makes some validations over the departure information requested vs the shown in screen.
	 * @return
	 */
	public boolean isDepartureInformationvalid() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();
		
		strb.append("Departure details: ");
		
		List<WebElement> airlineNameElements = getDriver().findElements(By.xpath(AIRLINE_NAME_LABEL_PATH));
		String airlineName = airlineNameElements.get(0).getText();
		strb.append("\nSelected airline: [").append(departureDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && departureDetails.getAirlineName().equalsIgnoreCase(airlineName);
		
		List<WebElement> departureTimeElements = getDriver().findElements(By.xpath(DEPARTURE_TIME_LABEL_PATH));
		String departureTime = departureTimeElements.get(0).getText();
		strb.append("\nSelected departure time: [").append(departureDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("]");
		valid = valid && departureDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		
		List<WebElement> arrivalTimeElements = getDriver().findElements(By.xpath(ARRIVAL_TIME_LABEL_PATH));
		String arrivalTime = arrivalTimeElements.get(0).getText();
		strb.append("\nSelected arrival time: [").append(departureDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("]");
		valid = valid && departureDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);

		String[] airports = departureDetails.getAirports().split("-");
		
		List<WebElement> departureAirportElements = getDriver().findElements(By.xpath(DEPARTURE_AIRPORT_LABEL_PATH));
		String departureAirport = departureAirportElements.get(0).getText();
		strb.append("\nSelected departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("]");
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		
		List<WebElement> arrivalAirportElements = getDriver().findElements(By.xpath(ARRIVAL_AIRPORT_LABEL_PATH));
		String arrivalAirport = arrivalAirportElements.get(0).getText();
		strb.append("\nSelected arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("]");
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(strb.toString());
		}
		
		return valid;
	}
	
	/**
	 * Makes some validations over the returning information requestes vs the shown in screen.
	 * @return
	 */
	public boolean isReturnInformationvalid() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();
		
		strb.append("Return details: ");
		
		List<WebElement> airlineNameElements = getDriver().findElements(By.xpath(AIRLINE_NAME_LABEL_PATH));
		String airlineName = airlineNameElements.get(1).getText();
		strb.append("\nSelected airline: [").append(returnDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && returnDetails.getAirlineName().equalsIgnoreCase(airlineName);
		
		List<WebElement> departureTimeElements = getDriver().findElements(By.xpath(DEPARTURE_TIME_LABEL_PATH));
		String departureTime = departureTimeElements.get(2).getText();
		strb.append("\nSelected departure time: [").append(returnDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("]");
		valid = valid && returnDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		
		List<WebElement> arrivalTimeElements = getDriver().findElements(By.xpath(ARRIVAL_TIME_LABEL_PATH));
		String arrivalTime = arrivalTimeElements.get(2).getText();
		strb.append("\nSelected arrival time: [").append(returnDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("]");
		valid = valid && returnDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);
		
		String[] airports = returnDetails.getAirports().split("-");
		
		List<WebElement> departureAirportElements = getDriver().findElements(By.xpath(DEPARTURE_AIRPORT_LABEL_PATH));
		String departureAirport = departureAirportElements.get(1).getText();
		strb.append("\nSelected departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("]");
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		
		List<WebElement> arrivalAirportElements = getDriver().findElements(By.xpath(ARRIVAL_AIRPORT_LABEL_PATH));
		String arrivalAirport = arrivalAirportElements.get(1).getText();
		strb.append("\nSelected arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("]");
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(strb.toString());
		}
		
		return valid;
	}

	/**
	 * Clicks the continue button and returns the checkout page.
	 * @return
	 */
	public FlightCheckoutPage continueBooking() {
		WebElement continueButton = getDriver().findElement(By.id(CONTINUE_BUTTON_ID));
		waitUntilElementIsClickable(continueButton);

		getActions().moveToElement(continueButton).perform();
		continueButton.click();
		
		return new FlightCheckoutPage(getDriver());
	}
	
	public FlightDetails getDepartureDetails() {
		return departureDetails;
	}

	public void setDepartureDetails(FlightDetails departureDetails) {
		this.departureDetails = departureDetails;
	}

	public FlightDetails getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(FlightDetails returnDetails) {
		this.returnDetails = returnDetails;
	}
	
}
