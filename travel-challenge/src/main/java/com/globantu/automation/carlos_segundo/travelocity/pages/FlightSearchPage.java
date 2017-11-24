package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;

/**
 * A representation of the travelocity flights search page.
 * @author carlos.segundo
 *
 */
public class FlightSearchPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(FlightSearchPage.class);
	
	private final String ADULT_COUNT_SELECT_ID = "adult-count";
	
	private final String CHILD_COUNT_SELECT_ID = "child-count";
	
	private final String FLIGHT_LIST_ID = "flightModuleList";
	
	private final String SELECTED_FLIGHT_ARTICLE_ID = "outboundflightModule";
	
	private final String PACKAGE_CONTAINER_ID = "multiItemPlaybackContainer";
	
	private final String CHOICE_NO_THANKS_ID = "forcedChoiceNoThanks";
	
	private final String FLIGHT_LIST_ITEM_TAG = ".//li[contains(@id, 'flight-module-')]";
	
	private final String SORT_SELECT_PATH = "//*[@id='sortBar']//select";
	
	private final String FLIGHT_DETAILS_LINK_PATH = ".//span[contains(@class,'details-holder')]/a";
	
	private final String FLIGHT_DETAILS_DIV_PATH = "//div[@class='flight-details']";

	private final String FLIGHT_DEPARTURE_TIME_PATH = ".//span[@data-test-id='departure-time']";
	
	private final String FLIGHT_ARRIVAL_TIME_PATH = ".//span[@data-test-id='arrival-time']";
	
	private final String FLIGHT_AIRLINE_NAME_PATH = ".//*[@data-test-id='airline-name']";
	
	private final String FLIGHT_DURATION_PATH = ".//*[@data-test-id='duration']";
	
	private final String FLIGHT_AIRPORTS_PATH = ".//*[@class='flight-info']";
	
	private final String FLIGHT_NUM_STOPS_PATH = ".//*[@data-test-num-stops]";
	
	private final String FLIGHT_NUM_STOPS_ATTRIBUTE = "data-test-num-stops";
	
	private final String FLIGHT_PRICE_PER_TRAVELER_PATH = ".//*[@data-test-price-per-traveler]";
	
	private final String FLIGHT_PRICE_PER_TRAVELER_ATTRIBUTE = "data-test-price-per-traveler";
	
	private final String SELECT_BUTTON_PATH = ".//button[@data-test-id='select-button']";
	
	@FindBy(id="departure-airport-1")
	private WebElement departureInput;
	
	@FindBy(id="arrival-airport-1")
	private WebElement arrivalInput;
	
	@FindBy(id="departure-date-1")
	private WebElement departureDateInput;
	
	@FindBy(id="return-date-1")
	private WebElement returnDateInput;
	
	@FindBy(id=ADULT_COUNT_SELECT_ID)
	private WebElement adultCountSelect;
	
	@FindBy(id=CHILD_COUNT_SELECT_ID)
	private WebElement childCountSelect;
	
	public FlightSearchPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Gets the information used to get this page
	 * @return a {@link FlightDetails}
	 */
	public FlightDetails getSearchDetails() {
		FlightDetails flightDetails = new FlightDetails();
		flightDetails.setDepartureLocation(getDepartureLocation());
		flightDetails.setArrivalLocation(getArrivalLocation());
		flightDetails.setAdultSeats(getAdultSeats());
		flightDetails.setChildrenSeats(getChildrenSeats());
		flightDetails.setDepartureDate(getDepartureDate());
		flightDetails.setReturningDate(getReturnDate());
		
		return flightDetails;
	}
	
	/**
	 * Gets the departure location
	 * @return
	 */
	public String getDepartureLocation() {
		String result = null;
		waitUntilElementIsVisible(departureInput);
		
		result = departureInput.getAttribute("value");
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Departure: " + result);
		}
		
		return result;
	}
	
	/**
	 * Gets the arrival location
	 * @return
	 */
	public String getArrivalLocation() {
		String result;
		waitUntilElementIsVisible(arrivalInput);
		
		result = arrivalInput.getAttribute("value");
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Arrival: " + result);
		}
		
		return result;
	}
	
	/**
	 * Gets the number of adult seats selected
	 * @return
	 */
	public int getAdultSeats() {
		waitUntilElementIsPresent(By.id(ADULT_COUNT_SELECT_ID), true);
		String result = getSelectedElementValue(adultCountSelect);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Adult seats: " + result);	
		}
		
		return Integer.parseInt(result);
	}
	
	/**
	 * Gets the number of children seats selected
	 * @return
	 */
	public int getChildrenSeats() {
		waitUntilElementIsPresent(By.id(CHILD_COUNT_SELECT_ID), true);
		String result = getSelectedElementValue(childCountSelect);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Child seats: " + result);	
		}
		
		return Integer.parseInt(result);
	}

	/**
	 * Gets the selected departure date
	 * @return
	 */
	public LocalDate getDepartureDate() {
		waitUntilElementIsVisible(departureDateInput);
		String format = departureDateInput.getAttribute("placeholder");
		format = format.replaceAll("m", "M");
		String value = departureDateInput.getAttribute("value");
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Departure date: " + value);
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDate departureDate = null;
		
		try {
			departureDate = LocalDate.parse(value, dtf);
		} catch (DateTimeParseException e) {
			LOGGER.error("Bad format for departure date: FORMAT[" + format + "], DATE[" + value + "]");
		}
		
		return departureDate;
	}
	
	/**
	 * Gets the selected returning date
	 * @return
	 */
	public LocalDate getReturnDate() {
		waitUntilElementIsVisible(returnDateInput);
		String format = returnDateInput.getAttribute("placeholder");
		format = format.replaceAll("m", "M");
		String value = returnDateInput.getAttribute("value");
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Return date: " + value);
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDate returnDate = null;
		try {
			returnDate = LocalDate.parse(value, dtf);
		} catch (DateTimeParseException e) {
			LOGGER.error("Bad format for return date: FORMAT[" + format + "], DATE[" + value + "]");
		}
		
		return returnDate;
	}
	
	/**
	 * Clicks all the flight details links
	 */
	public void viewAllFlightsDetails() {
		WebElement flightsList = getDriver().findElement(By.id(FLIGHT_LIST_ID));
		List<WebElement> detailsLinks = flightsList.findElements(By.xpath(FLIGHT_DETAILS_LINK_PATH));
		detailsLinks.forEach(l ->
		{
			getActions().moveToElement(l).perform();
			waitUntilElementIsClickable(l);
			l.click();
		});
	}
	
	/**
	 * Orders the flights results by the given option text
	 * @param orderText The option visible text to order by
	 * @return a List of String with the duration of the ordered results (TODO: Should return the resulting list of flights details)
	 */
	public List<String> orderResultsBy(String orderText) {
		waitUntilElementIsPresent(By.xpath(SORT_SELECT_PATH), true);
		
		WebElement sortSelect = getDriver().findElement(By.xpath(SORT_SELECT_PATH));
		waitUntilElementIsClickable(sortSelect);
		selectElementByText(sortSelect, orderText);
		waitUntilElementIsClickable(sortSelect);

		WebElement flightsList = getDriver().findElement(By.id(FLIGHT_LIST_ID));
		List<WebElement> durationElements = flightsList.findElements(By.xpath(FLIGHT_DURATION_PATH));
		List<String> durations = durationElements.stream().map(d -> d.getText()).collect(Collectors.toList());
		
		return durations;
	}

	/**
	 * Selects a departure flight.
	 * @param flightOption The option in the flight list results to select. The options start in 0, for the first flight.
	 * @return A {@link FlightDetails} with the information of the selected flight.
	 */
	public FlightDetails selectDepartureFlight(int flightOption) {
		return selectFlight(flightOption);
	}
	
	/**
	 * Selects a returning flight, skipping the offer to add a hotel to the booking of the flight.
	 * @param flightOption The option in the flight list results to select. The options start in 0, for the first flight.
	 * @return The {@link FlightInformationPage} of the selected flights.
	 */
	public BasePage selectReturningFlight(int flightOption, boolean isFlightAndHotelPackage) {
		BasePage detailsPage = null;
		
		if(!isFlightAndHotelPackage) {
			waitUntilElementIsPresent(By.id(SELECTED_FLIGHT_ARTICLE_ID), true);

			String mainWindowHandle = getDriver().getWindowHandle();
			FlightDetails returnDetails = selectFlight(flightOption);
			
			try {
				waitUntilElementIsPresent(By.id(CHOICE_NO_THANKS_ID), false);
				WebElement noThanksLink = getDriver().findElement(By.id(CHOICE_NO_THANKS_ID));
				waitUntilElementIsVisible(noThanksLink);
				waitUntilElementIsClickable(noThanksLink);
				noThanksLink.click();
			}catch(TimeoutException e) {
				LOGGER.error("No optional offer shown...");
			}
			
			detailsPage = new FlightInformationPage(getDriver());
			((FlightInformationPage) detailsPage).setReturnDetails(returnDetails);
			
			for(String windowHandle : getDriver().getWindowHandles()) {
				if(!windowHandle.equals(mainWindowHandle)) {
					getDriver().switchTo().window(windowHandle);
					break;
				}
			}
		}else {
			waitUntilElementIsPresent(By.id(PACKAGE_CONTAINER_ID), true);

			FlightDetails returnDetails = selectFlight(flightOption);
			
			try {
				waitUntilElementIsPresent(By.id(CHOICE_NO_THANKS_ID), false);
				WebElement noThanksLink = getDriver().findElement(By.id(CHOICE_NO_THANKS_ID));
				waitUntilElementIsClickable(noThanksLink);
				noThanksLink.click();
			}catch(TimeoutException e) {
				LOGGER.error("No optional offer shown...");
			}
			
			detailsPage = new PackageInformationPage(getDriver());
			((PackageInformationPage) detailsPage).setReturnFlightDetails(returnDetails);
		}
		
		return detailsPage;
	}
	
	/**
	 * Selects a flight given a selection index and fills a {@link FlightDetails} with the information of the selected flight.
	 * @param flightOption The option in the flight list results to select. The options start in 0, for the first flight.
	 * @return A {@link FlightDetails} with the information of the selected flight.
	 */
	protected FlightDetails selectFlight(int flightOption) {
		FlightDetails selectedFlight = null;
		
		try {
			selectedFlight = new FlightDetails();
			LOGGER.info("Select flight, option: " + flightOption);
			
			waitUntilElementIsPresent(By.id(FLIGHT_LIST_ID), true);
			WebElement flightsList = getDriver().findElement(By.id(FLIGHT_LIST_ID));
			waitUntilElementIsVisible(flightsList);
			
			waitUntilElementIsPresent(By.xpath(SORT_SELECT_PATH), true);
			
			WebElement sortSelect = getDriver().findElement(By.xpath(SORT_SELECT_PATH));
			waitUntilElementIsClickable(sortSelect);
			
			List<WebElement> flights = flightsList.findElements(By.xpath(FLIGHT_LIST_ITEM_TAG));
			WebElement selectedElement = flights.get(flightOption);
			getActions().moveToElement(selectedElement).perform();
			
			waitUntilElementIsVisible(selectedElement);
			WebElement detailsLink = selectedElement.findElement(By.xpath(FLIGHT_DETAILS_LINK_PATH));
			detailsLink.click();

			waitUntilElementIsPresent(By.xpath(FLIGHT_DETAILS_DIV_PATH), true);
			WebElement flightDetailsDiv = detailsLink.findElement(By.xpath(FLIGHT_DETAILS_DIV_PATH));
			
			WebElement infoElement = selectedElement.findElement(By.xpath(FLIGHT_AIRLINE_NAME_PATH));
			selectedFlight.setAirlineName(infoElement.getText());
			
			String elementPath = selectedElement.getAttribute("id");
			elementPath = "//*[@id='" + elementPath + "']" + FLIGHT_AIRPORTS_PATH.substring(1);
			LOGGER.info("Airports element path: " + elementPath);
			waitUntilElementIsPresent(By.xpath(elementPath), true);
			infoElement = selectedElement.findElement(By.xpath(FLIGHT_AIRPORTS_PATH));
			selectedFlight.setAirports(infoElement.getText());
			
			infoElement = selectedElement.findElement(By.xpath(FLIGHT_ARRIVAL_TIME_PATH));
			selectedFlight.setArrivalTime(infoElement.getText());
			
			infoElement = selectedElement.findElement(By.xpath(FLIGHT_DEPARTURE_TIME_PATH));
			selectedFlight.setDepartureTime(infoElement.getText());
			
			infoElement = selectedElement.findElement(By.xpath(FLIGHT_DURATION_PATH));
			selectedFlight.setDuration(infoElement.getText());
			
			infoElement = selectedElement.findElement(By.xpath(FLIGHT_NUM_STOPS_PATH));
			selectedFlight.setNumStops(Integer.parseInt(infoElement.getAttribute(FLIGHT_NUM_STOPS_ATTRIBUTE)));
			
			infoElement = selectedElement.findElement(By.xpath(FLIGHT_PRICE_PER_TRAVELER_PATH));
			selectedFlight.setPricePerTraveler(new BigDecimal(infoElement.getAttribute(FLIGHT_PRICE_PER_TRAVELER_ATTRIBUTE).substring(1)));

			WebElement selectButton = selectedElement.findElement(By.xpath(SELECT_BUTTON_PATH));
			getActions().moveToElement(selectButton).perform();
			
			waitUntilElementIsClickable(selectButton);
			
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("\nSelected flight information: " + selectedFlight);
			}
			
			selectButton.click();
			getWait().ignoring(TimeoutException.class).until(ExpectedConditions.or(
					ExpectedConditions.stalenessOf(flightDetailsDiv), 
					ExpectedConditions.presenceOfElementLocated(By.id(CHOICE_NO_THANKS_ID))));
		}catch(NoSuchElementException e) {
			LOGGER.error("Unable to get all flight details: " + e.getMessage(), e);
		}
		
		return selectedFlight;
	}
	
}
