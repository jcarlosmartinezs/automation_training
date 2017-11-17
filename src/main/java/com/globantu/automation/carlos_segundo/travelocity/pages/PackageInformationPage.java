package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.globantu.automation.carlos_segundo.travelocity.CarDetails;
import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;
import com.globantu.automation.carlos_segundo.travelocity.HotelDetails;

/**
 * A representation of the travelocity flight details page.
 * @author carlos.segundo
 *
 */
public class PackageInformationPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(PackageInformationPage.class);

	private final By TRANSPORTATION_SECTION_SELECTOR = By.id("dxGroundTransportationModule");
	
	private final By TRANSPORT_CARD_SELECTOR = By.xpath(".//*[contains(@class,'gt-tile')]");
	
	private final By TRANSPORT_TITLE_SELECTOR = By.xpath(".//h3[@class='flex-title']");
	
	private final By TRANSPORT_PASSENGERS_SELECTOR = By.xpath(".//span[@class='passengers']");
	
	private final By TRANSPORT_BAGS_SELECTOR = By.xpath(".//span[@class='bags']");
	
	private final By TRANSPORT_PRICE_SELECTOR = By.xpath(".//a[@data-tooltip-classes='gt-price-info']");
	
	private final By BUTTON_ADD_SELECTOR = By.xpath(".//button[contains(@class, 'gt-add-btn')]");
	
	private final By HOTEL_NAME_SELECTOR = By.id("trip-summary-hotel-title");
	
	private final By FLIGHT_INFORMATION_SELECTOR = By.id("rspFlightsContainer");
	
	private final By DEPARTURE_LOCATION_SELECTOR = By.xpath(".//span[contains(@id,'departure-airport')]");
	
	private final By ARRIVAL_LOCATION_SELECTOR = By.xpath(".//span[contains(@id,'arrival-airport')]");
	
	private final By AIRLINE_NAME_SELECTOR = By.xpath(".//span[contains(@class,'sub-info')]");
	
	private final By FLIGHT_DURATION_SELECTOR = By.xpath(".//*[contains(@id,'duration-')]");
	
	private final By FLIGHT_DEPARTURE_TIME_SELECTOR = By.xpath(".//span[contains(@id,'departure-time')]");
	
	private final By FLIGHT_ARRIVAL_TIME_SELECTOR = By.xpath(".//span[contains(@id,'arrival-time')]");
	
	private final By TRIP_SUMMARY_SELECTOR = By.xpath("//div[contains(@class, 'tripSummary')]");
	
	private final By CONTINUE_BUTTON_SELECTOR = By.xpath("//*[@id='trip-summary']//button[contains(@class, 'btn-action')]");
	
	private FlightDetails departureFlightDetails;
	
	private FlightDetails returnFlightDetails;
	
	private HotelDetails hotelDetails;
	
	private CarDetails carDetails;
	
	public PackageInformationPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Adds a car to the package
	 * @param option The option to add, the first option is 0.
	 * @return a {@link CarDetails} with the information of the added car.
	 */
	public CarDetails addCar(int option) {
		carDetails = new CarDetails();
		
		LOGGER.info("Add car to package, option: " + option);
		
		waitUntilElementIsPresent(TRANSPORTATION_SECTION_SELECTOR, true);
		WebElement transportSection = getDriver().findElement(TRANSPORTATION_SECTION_SELECTOR);
		getActions().moveToElement(transportSection).perform();
		
		List<WebElement> carElements = transportSection.findElements(TRANSPORT_CARD_SELECTOR);
		WebElement selectedCar = carElements.get(option);
		
		getActions().moveToElement(selectedCar).perform();
		
		WebElement carDescription = selectedCar.findElement(TRANSPORT_TITLE_SELECTOR);
		carDetails.setDescription(carDescription.getText());
		
		WebElement passengersElement = selectedCar.findElement(TRANSPORT_PASSENGERS_SELECTOR);
		carDetails.setPassengers(Integer.parseInt(passengersElement.getText().trim()));
		
		WebElement bagsElement = selectedCar.findElement(TRANSPORT_BAGS_SELECTOR);
		carDetails.setBags(Integer.parseInt(bagsElement.getText().trim()));
		
		WebElement priceElement = selectedCar.findElement(TRANSPORT_PRICE_SELECTOR);
		String priceText = priceElement.getText();
		carDetails.setPrice(new BigDecimal(priceText.substring(priceText.lastIndexOf("$") + 1).trim()));
		
		WebElement addButton = selectedCar.findElement(BUTTON_ADD_SELECTOR);
		waitUntilElementIsClickable(addButton);
		addButton.click();
		
		WebElement tripSummary = getDriver().findElement(TRIP_SUMMARY_SELECTOR);
		getActions().moveToElement(tripSummary).perform();
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nCar details: " + carDetails);
		}
		
		return carDetails;
	}
	
	/**
	 * Makes some validations over the requested package information vs the shown in screen.
	 * @return
	 */
	public boolean isPackageInformationvalid() {
		boolean valid = true;
		StringBuilder strb = new StringBuilder();
		
		strb.append("\nPackage details: ");
		
		WebElement hotelNameElement = getDriver().findElement(HOTEL_NAME_SELECTOR);
		String hotelName = hotelNameElement.getText();
		strb.append("\nSelected hotel: [").append(hotelDetails.getName())
				.append("], found in page[").append(hotelName).append("]");
		valid = valid && hotelDetails.getName().equalsIgnoreCase(hotelName);
		
		WebElement flightInformationSection = getDriver().findElement(FLIGHT_INFORMATION_SELECTOR);
		
		List<WebElement> airlineNameElements = flightInformationSection.findElements(AIRLINE_NAME_SELECTOR);
		String airlineName = airlineNameElements.get(0).getText();
		strb.append("\nSelected departure airline: [").append(departureFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && departureFlightDetails.getAirlineName().equalsIgnoreCase(airlineName);
		
		airlineName = airlineNameElements.get(1).getText();
		strb.append("\nSelected return airline: [").append(returnFlightDetails.getAirlineName())
				.append("], found in page[").append(airlineName).append("]");
		valid = valid && returnFlightDetails.getAirlineName().equalsIgnoreCase(airlineName);
		
		List<WebElement> departureTimeElements = getDriver().findElements(FLIGHT_DEPARTURE_TIME_SELECTOR);
		String departureTime = departureTimeElements.get(0).getText();
		strb.append("\nSelected departure departure time: [").append(departureFlightDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("]");
		valid = valid && departureFlightDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		
		List<WebElement> arrivalTimeElements = getDriver().findElements(FLIGHT_ARRIVAL_TIME_SELECTOR);
		String arrivalTime = arrivalTimeElements.get(0).getText();
		strb.append("\nSelected departure arrival time: [").append(departureFlightDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("]");
		valid = valid && departureFlightDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);

		departureTime = departureTimeElements.get(1).getText();
		strb.append("\nSelected return departure time: [").append(returnFlightDetails.getDepartureTime())
				.append("], found in page[").append(departureTime).append("]");
		valid = valid && returnFlightDetails.getDepartureTime().equalsIgnoreCase(departureTime);
		
		arrivalTime = arrivalTimeElements.get(1).getText();
		strb.append("\nSelected return arrival time: [").append(returnFlightDetails.getArrivalTime())
				.append("], found in page[").append(arrivalTime).append("]");
		valid = valid && returnFlightDetails.getArrivalTime().equalsIgnoreCase(arrivalTime);
		
		List<WebElement> flightDurationElements = flightInformationSection.findElements(FLIGHT_DURATION_SELECTOR);
		String flightDuration = flightDurationElements.get(0).getText();
		strb.append("\nSelected departure flight duration: [").append(departureFlightDetails.getDuration())
				.append("], found in page[").append(flightDuration).append("]");
		valid = valid && departureFlightDetails.getDuration().equalsIgnoreCase(flightDuration);
		
		flightDuration = flightDurationElements.get(1).getText();
		strb.append("\nSelected return flight duration: [").append(returnFlightDetails.getDuration())
				.append("], found in page[").append(flightDuration).append("]");
		valid = valid && returnFlightDetails.getDuration().equalsIgnoreCase(flightDuration);
		
		String[] airports = departureFlightDetails.getAirports().split("-");
		
		List<WebElement> departureAirportElements = getDriver().findElements(DEPARTURE_LOCATION_SELECTOR);
		String departureAirport = departureAirportElements.get(0).getText();
		strb.append("\nSelected departure departure airport: [").append(airports[0].trim())
				.append("], found in page[").append(departureAirport).append("]");
		valid = valid && airports[0].trim().equalsIgnoreCase(departureAirport);
		
		List<WebElement> arrivalAirportElements = getDriver().findElements(ARRIVAL_LOCATION_SELECTOR);
		String arrivalAirport = arrivalAirportElements.get(0).getText();
		strb.append("\nSelected departure arrival airport: [").append(airports[1].trim())
				.append("], found in page[").append(arrivalAirport).append("]");
		valid = valid && airports[1].trim().equalsIgnoreCase(arrivalAirport);
		
		airports = returnFlightDetails.getAirports().split("-");
		
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
	
	/**
	 * Clicks the continue button and returns the checkout page.
	 * @return
	 */
	public PackageCheckoutPage continueBooking() {
		WebElement continueButton = getDriver().findElement(CONTINUE_BUTTON_SELECTOR);
		waitUntilElementIsClickable(continueButton);

		getActions().moveToElement(continueButton).perform();
		continueButton.click();
		
		PackageCheckoutPage checkoutPage = new PackageCheckoutPage(getDriver());
		checkoutPage.setDepartureFlightDetails(departureFlightDetails);
		checkoutPage.setReturnFlightDetails(returnFlightDetails);
		checkoutPage.setHotelDetails(hotelDetails);
		checkoutPage.setCarDetails(carDetails);
		
		return checkoutPage;
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
