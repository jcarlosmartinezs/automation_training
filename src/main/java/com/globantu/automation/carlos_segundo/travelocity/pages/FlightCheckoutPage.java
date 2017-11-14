package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;

/**
 * A representation of the travelocity flight checkout page.
 * @author carlos.segundo
 *
 */
public class FlightCheckoutPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(FlightCheckoutPage.class);

	private final String TRIP_SUMMARY_DIV_ID = "trip-summary";
	
	private final String FLIGHT_DETAILS_DIV_ID = "flight-details";
	
	private final String TRIP_SUMMARY_LINK_PATH = ".//a[contains(@class,'product-content-title')]";
	
	private final String AIRLINE_NAME_LABEL_CSS = "span.airline-name.trim-margin";
	
	private final String DEPARTURE_TIME_LABEL_PATH = "//div/span[@class='departure-time']";
	
	private final String ARRIVAL_TIME_LABEL_PATH = "//div/span[@class='arrival-time']";
	
	private final String DEPARTURE_AIRPORT_LABEL_PATH = "//div/span[@class='departure-airport-codes']";
	
	private final String ARRIVAL_AIRPORT_LABEL_PATH = "//div/span[@class='arrival-airport-codes']";
	
	private FlightDetails departureDetails;
	
	private FlightDetails returnDetails;
	
	public FlightCheckoutPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean validateCheckoutInfo() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();

		waitUntilElementIsPresent(By.id(TRIP_SUMMARY_DIV_ID));
		
		WebElement summaryDiv = getDriver().findElement(By.id(TRIP_SUMMARY_DIV_ID));
		WebElement flightDetailsLink = summaryDiv.findElement(By.xpath(TRIP_SUMMARY_LINK_PATH));
		waitUntilElementIsClickable(flightDetailsLink);
		flightDetailsLink.click();
		
		WebElement detailsDiv = getDriver().findElement(By.id(FLIGHT_DETAILS_DIV_ID));
		waitUntilElementIsVisible(detailsDiv);
		waitUntilElementIsPresent(By.cssSelector(AIRLINE_NAME_LABEL_CSS));
		
		strb.append("Flights details: ");
		
		List<WebElement> airlineNameElements = getDriver().findElements(By.cssSelector(AIRLINE_NAME_LABEL_CSS));
		String airlineName = airlineNameElements.get(0).getText();
		strb.append("\nSelected departure airline: [").append(departureDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && departureDetails.getAirlineName().equalsIgnoreCase(airlineName);
		
		airlineName = airlineNameElements.get(1).getText();
		strb.append("\nSelected return airline: [").append(returnDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && returnDetails.getAirlineName().equalsIgnoreCase(airlineName);
		
		List<WebElement> departureTimeElements = getDriver().findElements(By.xpath(DEPARTURE_TIME_LABEL_PATH));
		String departureTime = departureTimeElements.get(0).getText();
		strb.append("\nSelected departure departure time: [").append(departureDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("]");
		valid = valid && departureDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		
		departureTime = departureTimeElements.get(1).getText();
		strb.append("\nSelected return departure time: [").append(returnDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("]");
		valid = valid && returnDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		
		List<WebElement> arrivalTimeElements = getDriver().findElements(By.xpath(ARRIVAL_TIME_LABEL_PATH));
		String arrivalTime = arrivalTimeElements.get(0).getText();
		strb.append("\nSelected departure arrival time: [").append(departureDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("]");
		valid = valid && departureDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);
		
		arrivalTime = arrivalTimeElements.get(1).getText();
		strb.append("\nSelected return arrival time: [").append(returnDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("]");
		valid = valid && returnDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);
		
		String[] airports = departureDetails.getAirports().split("-");
		
		List<WebElement> departureAirportElements = getDriver().findElements(By.xpath(DEPARTURE_AIRPORT_LABEL_PATH));
		String departureAirport = departureAirportElements.get(0).getText();
		strb.append("\nSelected departure departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("]");
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		
		List<WebElement> arrivalAirportElements = getDriver().findElements(By.xpath(ARRIVAL_AIRPORT_LABEL_PATH));
		String arrivalAirport = arrivalAirportElements.get(0).getText();
		strb.append("\nSelected departure arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("]");
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		
		airports = returnDetails.getAirports().split("-");
		
		departureAirport = departureAirportElements.get(1).getText();
		strb.append("\nSelected return departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("]");
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		
		arrivalAirport = arrivalAirportElements.get(1).getText();
		strb.append("\nSelected return arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("]");
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(strb.toString());
		}
		
		return valid;
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
