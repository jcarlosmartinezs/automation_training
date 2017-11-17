package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.globantu.automation.carlos_segundo.travelocity.CarDetails;
import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;
import com.globantu.automation.carlos_segundo.travelocity.HotelDetails;

/**
 * A representation of the travelocity flight checkout page.
 * @author carlos.segundo
 *
 */
public class PackageCheckoutPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(PackageCheckoutPage.class);

	private final String TRIP_SUMMARY_DIV_ID = "trip-summary";
	
	private final String FLIGHT_DETAILS_DIV_CSS = ".flight-details";
	
	private final String FLIGHT_DETAILS_DIV_PATH = "//*[@id='trip-summary']//*[@class='flight-details']";
	
	private final String AIRLINE_NAME_PATH = ".//div[@class='carrier-info']";
	
	private final String TRIP_SUMMARY_LINK_PATH = ".//a[contains(@class,'product-content-title')]";
	
	private final String FLIGHT_DURATION_PATH = ".//div[@class='duration-info']";
	
	private final String PRODUCT_DESCTIPTION_PATH =".//div[@class='product-description']";
	
	private FlightDetails departureFlightDetails;
	
	private FlightDetails returnFlightDetails;
	
	private HotelDetails hotelDetails;
	
	private CarDetails carDetails;
	
	public PackageCheckoutPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean validateCheckoutInfo() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();

		waitUntilElementIsPresent(By.id(TRIP_SUMMARY_DIV_ID), true);
		
		WebElement summaryDiv = getDriver().findElement(By.id(TRIP_SUMMARY_DIV_ID));
		waitUntilElementIsVisible(summaryDiv);
		List<WebElement> detailsLinks = summaryDiv.findElements(By.xpath(TRIP_SUMMARY_LINK_PATH));
		
		// Flights details
		WebElement flightDetailLink = detailsLinks.get(0);
		waitUntilElementIsClickable(flightDetailLink);
		flightDetailLink.click();
		
		waitUntilElementIsPresent(By.xpath(FLIGHT_DETAILS_DIV_PATH), true);
		List<WebElement> detailsDivs = summaryDiv.findElements(By.cssSelector(FLIGHT_DETAILS_DIV_CSS));
		
		// Departure flight
		WebElement departureDetails = detailsDivs.get(0);
		waitUntilElementIsVisible(departureDetails);
		
		strb.append("\nDeparture flight details: ");
		
		WebElement airlineNameElement = departureDetails.findElement(By.xpath(AIRLINE_NAME_PATH));
		String airlineName = airlineNameElement.getText();
		strb.append("\nSelected airline: [").append(departureFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && airlineName.toLowerCase().contains(departureFlightDetails.getAirlineName().toLowerCase());
		
		WebElement durationElement = departureDetails.findElement(By.xpath(FLIGHT_DURATION_PATH));
		String duration = durationElement.getText().split(",")[0];
		strb.append("\nSelected duration: [").append(departureFlightDetails.getDuration())
				.append("], found in page[").append(duration).append("]");
		valid = valid && departureFlightDetails.getDuration().equalsIgnoreCase(duration);
		
		// Return flight
		WebElement returnDetails = detailsDivs.get(1);
		waitUntilElementIsVisible(returnDetails);
		
		strb.append("\nReturn flight details: ");
		
		airlineNameElement = returnDetails.findElement(By.xpath(AIRLINE_NAME_PATH));
		airlineName = airlineNameElement.getText();
		strb.append("\nSelected airline: [").append(returnFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && airlineName.toLowerCase().contains(returnFlightDetails.getAirlineName().toLowerCase());
		
		durationElement = returnDetails.findElement(By.xpath(FLIGHT_DURATION_PATH));
		duration = durationElement.getText().split(",")[0];
		strb.append("\nSelected duration: [").append(returnFlightDetails.getDuration())
				.append("], found in page[").append(duration).append("]");
		valid = valid && returnFlightDetails.getDuration().equalsIgnoreCase(duration);
		
		// Hotel details
		WebElement hotelDetailLink = detailsLinks.get(1);
		waitUntilElementIsClickable(hotelDetailLink);
		hotelDetailLink.click();
		
		strb.append("\nHotel details: ");
		
		String hotelName = hotelDetailLink.getText();
		strb.append("\nSelected hotel name: [").append(hotelDetails.getName())
				.append("], found in page[").append(hotelName).append("]");
		valid = valid && hotelDetails.getName().equalsIgnoreCase(hotelName);
		
		WebElement hotelCard = hotelDetailLink.findElement(By.xpath(".//.."));
		
		WebElement roomsElement = hotelCard.findElement(By.xpath(PRODUCT_DESCTIPTION_PATH));
		String rooms = roomsElement.getText().split(" ")[0];
		strb.append("\nSelected rooms: [").append(hotelDetails.getRooms())
				.append("], found in page[").append(rooms).append("]");
		valid = valid && hotelDetails.getRooms() == Integer.parseInt(rooms.trim());
		
		// Hotel details
		WebElement carDetailLink = detailsLinks.get(2);
		waitUntilElementIsClickable(carDetailLink);
		carDetailLink.click();
		
		strb.append("\nCar details: ");
		
		String carDescription = carDetailLink.getAttribute("data-toggle-text");
		strb.append("\nSelected car: [").append(carDetails.getDescription())
				.append("], found in page[").append(carDescription).append("]");
		valid = valid && carDetails.getDescription().equalsIgnoreCase(carDescription);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(strb.toString());
		}
		
		return valid;
	}

	public FlightDetails getDepartureFlightDetails() {
		return departureFlightDetails;
	}

	public void setDepartureFlightDetails(FlightDetails departureFlightDetails) {
		this.departureFlightDetails = departureFlightDetails;
	}

	public FlightDetails getReturnFlightDetails() {
		return returnFlightDetails;
	}

	public void setReturnFlightDetails(FlightDetails returnFlightDetails) {
		this.returnFlightDetails = returnFlightDetails;
	}

	public HotelDetails getHotelDetails() {
		return hotelDetails;
	}

	public void setHotelDetails(HotelDetails hotelDetails) {
		this.hotelDetails = hotelDetails;
	}

	public CarDetails getCarDetails() {
		return carDetails;
	}

	public void setCarDetails(CarDetails carDetails) {
		this.carDetails = carDetails;
	}

}
