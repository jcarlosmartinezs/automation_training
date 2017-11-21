package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
	
	// elements for package page
	private final String FLIGHT_DETAILS_DIV_CSS = ".flight-details";
	
	private final String FLIGHT_DETAILS_DIV_PATH = "//*[@id='trip-summary']//*[@class='flight-details']";
	
	private final String AIRLINE_NAME_PATH = ".//div[@class='carrier-info']";
	
	private final String TRIP_SUMMARY_LINK_PATH = ".//a[contains(@class,'product-content-title')]";
	
	private final String PRODUCT_DESCTIPTION_PATH =".//div[@class='product-description']";
	
	// End of elements for package page
	
	// elements for multi item page
	
	private final String DEPARTURE_AIRPORT_LABEL_PATH = ".//div/span[@class='departure-airport-codes']";
	
	private final String ARRIVAL_AIRPORT_LABEL_PATH = ".//div/span[@class='arrival-airport-codes']";
	
	private final String DEPARTURE_TIME_LABEL_PATH = ".//div/span[@class='departure-time']";
	
	private final String ARRIVAL_TIME_LABEL_PATH = ".//div/span[@class='arrival-time']";
	
	private final String AIRLINE_NAME_LABEL_CSS = "span.airline-name";
	
	private final String HOTEL_NAME_PATH = ".//div[@class='hotel-name']";
	
	private final String ROOM_COUNT_PATH = ".//div/span[@class='room-count']";
	
	private final String CAR_DESCRIPTION_PATH = ".//div[@class='dx-title']";
	
	// End of elements for multi item page
	
	private FlightDetails departureFlightDetails;
	
	private FlightDetails returnFlightDetails;
	
	private HotelDetails hotelDetails;
	
	private CarDetails carDetails;
	
	public PackageCheckoutPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean validateCheckoutInfo() {
		boolean valid = true;

		waitUntilElementIsPresent(By.id(TRIP_SUMMARY_DIV_ID), true);
		WebElement summaryDiv = getDriver().findElement(By.id(TRIP_SUMMARY_DIV_ID));
		waitUntilElementIsVisible(summaryDiv);
		
		boolean isMultiItemPage = false;
		
		try {
			waitUntilElementIsPresent(By.cssSelector(AIRLINE_NAME_LABEL_CSS), false);
		}catch(TimeoutException e) {
			LOGGER.info("It seems the page is an instance of MultiItemCheckout");
			isMultiItemPage = true;
		}
		
		if(isMultiItemPage) {
			valid = validateInfoFromMultiItemPage();
		}else {
			valid = validateInfoFromPackagePage();
		}
		
		return valid;
	}
	
	protected boolean validateInfoFromMultiItemPage() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();
		
		WebElement summaryDiv = getDriver().findElement(By.id(TRIP_SUMMARY_DIV_ID));
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
		valid = valid && airlineName.toLowerCase().contains(departureFlightDetails.getAirlineName().toLowerCase());
		strb.append("\nSelected airline: [").append(departureFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("], valid :: ").append(valid);
		
		// Return flight
		WebElement returnDetails = detailsDivs.get(1);
		waitUntilElementIsVisible(returnDetails);
		
		strb.append("\nReturn flight details: ");
		
		airlineNameElement = returnDetails.findElement(By.xpath(AIRLINE_NAME_PATH));
		airlineName = airlineNameElement.getText();
		valid = valid && airlineName.toLowerCase().contains(returnFlightDetails.getAirlineName().toLowerCase());
		strb.append("\nSelected airline: [").append(returnFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("], valid :: ").append(valid);
		
		// Hotel details
		WebElement hotelDetailLink = detailsLinks.get(1);
		waitUntilElementIsClickable(hotelDetailLink);
		hotelDetailLink.click();
		
		strb.append("\nHotel details: ");
		
		String hotelName = hotelDetailLink.getText();
		valid = valid && hotelDetails.getName().equalsIgnoreCase(hotelName);
		strb.append("\nSelected hotel name: [").append(hotelDetails.getName())
				.append("], found in page[").append(hotelName).append("], valid :: ").append(valid);
		
		WebElement hotelCard = hotelDetailLink.findElement(By.xpath(".//.."));
		
		WebElement roomsElement = hotelCard.findElement(By.xpath(PRODUCT_DESCTIPTION_PATH));
		String rooms = roomsElement.getText().split(" ")[0];
		valid = valid && hotelDetails.getRooms() == Integer.parseInt(rooms.trim());
		strb.append("\nSelected rooms: [").append(hotelDetails.getRooms())
				.append("], found in page[").append(rooms).append("], valid :: ").append(valid);
		
		// Hotel details
		WebElement carDetailLink = detailsLinks.get(2);
		waitUntilElementIsClickable(carDetailLink);
		carDetailLink.click();
		
		strb.append("\nCar details: ");
		
		String carDescription = carDetailLink.getAttribute("data-toggle-text");
		valid = valid && carDetails.getDescription().equalsIgnoreCase(carDescription);
		strb.append("\nSelected car: [").append(carDetails.getDescription())
				.append("], found in page[").append(carDescription).append("], valid :: ").append(valid);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(strb.toString());
		}
		
		return valid;
	}

	protected boolean validateInfoFromPackagePage() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();
		
		WebElement summaryDiv = getDriver().findElement(By.id(TRIP_SUMMARY_DIV_ID));
		
		// Flights details
		List<WebElement> airlineNameElements = summaryDiv.findElements(By.cssSelector(AIRLINE_NAME_LABEL_CSS));
		String airlineName = airlineNameElements.get(0).getText();
		valid = valid && airlineName.toLowerCase().contains(departureFlightDetails.getAirlineName().toLowerCase());
		strb.append("\nSelected departure airline: [").append(departureFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("], valid :: ").append(valid);
		
		airlineName = airlineNameElements.get(1).getText();
		valid = valid && airlineName.toLowerCase().contains(returnFlightDetails.getAirlineName().toLowerCase());
		strb.append("\nSelected return airline: [").append(returnFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("], valid :: ").append(valid);
		
		String[] airports = departureFlightDetails.getAirports().split("-");
		
		List<WebElement> departureAirportElements = summaryDiv.findElements(By.xpath(DEPARTURE_AIRPORT_LABEL_PATH));
		String departureAirport = departureAirportElements.get(0).getText();
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		strb.append("\nSelected departure departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("], valid :: ").append(valid);
		
		List<WebElement> arrivalAirportElements = summaryDiv.findElements(By.xpath(ARRIVAL_AIRPORT_LABEL_PATH));
		String arrivalAirport = arrivalAirportElements.get(0).getText();
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		strb.append("\nSelected departure arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("], valid :: ").append(valid);
		
		airports = returnFlightDetails.getAirports().split("-");
		
		departureAirport = departureAirportElements.get(1).getText();
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		strb.append("\nSelected return departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("], valid :: ").append(valid);
		
		arrivalAirport = arrivalAirportElements.get(1).getText();
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		strb.append("\nSelected return arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("], valid :: ").append(valid);
		
		List<WebElement> departureTimeElements = summaryDiv.findElements(By.xpath(DEPARTURE_TIME_LABEL_PATH));
		String departureTime = departureTimeElements.get(0).getText();
		valid = valid && departureFlightDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		strb.append("\nSelected departure departure time: [").append(departureFlightDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("], valid :: ").append(valid);
		
		departureTime = departureTimeElements.get(1).getText();
		valid = valid && returnFlightDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		strb.append("\nSelected return departure time: [").append(returnFlightDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("], valid :: ").append(valid);
		
		List<WebElement> arrivalTimeElements = summaryDiv.findElements(By.xpath(ARRIVAL_TIME_LABEL_PATH));
		String arrivalTime = arrivalTimeElements.get(0).getText();
		valid = valid && departureFlightDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);
		strb.append("\nSelected departure arrival time: [").append(departureFlightDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("], valid :: ").append(valid);
		
		arrivalTime = arrivalTimeElements.get(1).getText();
		valid = valid && returnFlightDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);
		strb.append("\nSelected return arrival time: [").append(returnFlightDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("], valid :: ").append(valid);
		
		// Hotel details
		strb.append("\nHotel details: ");

		WebElement hotelElement = summaryDiv.findElement(By.xpath(HOTEL_NAME_PATH));
		String hotelName = hotelElement.getText();
		valid = valid && hotelDetails.getName().equalsIgnoreCase(hotelName);
		strb.append("\nSelected hotel name: [").append(hotelDetails.getName())
				.append("], found in page[").append(hotelName).append("], valid :: ").append(valid);
		
		WebElement roomsElement = summaryDiv.findElement(By.xpath(ROOM_COUNT_PATH));
		String rooms = roomsElement.getText().split(" ")[0];
		valid = valid && hotelDetails.getRooms() == Integer.parseInt(rooms.trim());
		strb.append("\nSelected rooms: [").append(hotelDetails.getRooms())
				.append("], found in page[").append(rooms).append("], valid :: ").append(valid);
		
		// Car details
		strb.append("\nCar details: ");
		
		WebElement carDetailLink = summaryDiv.findElement(By.xpath(CAR_DESCRIPTION_PATH));
		String carDescription = carDetailLink.getText();
		valid = valid && carDetails.getDescription().equalsIgnoreCase(carDescription);
		strb.append("\nSelected car: [").append(carDetails.getDescription())
				.append("], found in page[").append(carDescription).append("], valid :: ").append(valid);
		
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
