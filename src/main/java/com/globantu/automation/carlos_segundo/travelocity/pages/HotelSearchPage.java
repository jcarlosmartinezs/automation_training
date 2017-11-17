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

import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;
import com.globantu.automation.carlos_segundo.travelocity.HotelDetails;

/**
 * A representation of the travelocity hotel search page.
 * @author carlos.segundo
 *
 */
public class HotelSearchPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(HotelSearchPage.class);
	
	private final By LOADING_MASK_SELECTOR = By.id("page-interstitial");
	
	private final By ORIGIN_BUTTON_SELECTOR = By.cssSelector("button.origin.fakeLink");
	
	private final By ORIGIN_INPUT_SELECTOR = By.id("inpSearchFrom");
	
	private final By DESTINATION_INPUT_SELECTOR = By.id("inpSearchNear");
	
	private final By DEPARTURE_DATE_INPUT_SELECTOR = By.id("inpStartDate");
	
	private final By RETURN_DATE_INPUT_SELECTOR = By.id("inpEndDate");
	
	private final By ROOMS_SPAN_SELECTOR = By.id("wizardTravellerSummaryRoom");
	
	private final By ADULT_COUNT_SPAN_SELECTOR = By.id("wizardTravellerSummaryAdults");
	
	private final By CHILDREN_COUNT_SPAN_SELECTOR = By.id("wizardTravellerSummaryChild");
	
	private final By SORT_CONTAINER_SELECTOR = By.id("sortContainer");
	
	private final By RESULTS_CONTAINER_SELECTOR = By.id("resultsContainer");
	
	private final By HOTEL_CARD_SELECTOR = By.xpath(".//article[@data-hotelid]");
	
	private final By PRICE_ITEMS_SELECTOR = By.xpath(".//article[@data-hotelid]//li[contains(@class,'actualPrice') and contains(@class, 'price')]");
	
	private final By STARS_ITEM_SELECTOR = By.xpath(".//li[@class='starRating secondary']//span[contains(@class,'stars-grey')]");
	
	private final By ORDERING_MASK_SELECTOR = By.xpath("//div[@class='opi-overlay']");
	
	private final String SORT_OPTION_PATH = ".//button[@data-opt-group='%s']";
	
	private final By NEXT_PAGE_BUTTON_SELECTOR = By.xpath("//button[@class='pagination-next']");
	
	private final By HOTEL_NAME_SELECTOR = By.xpath(".//h4[contains(@class,'hotelName')]");
	
	private final By PRICE_PER_TRAVELER_SELECTOR = By.xpath(".//li[contains(@class,'actualPrice') and contains(@class, 'price')]");
	
	private final By SELECT_HOTEL_LINK = By.xpath(".//a[@class='flex-link']");
	
	private boolean hasMorePages;
	
	public HotelSearchPage(WebDriver driver) {
		super(driver);
		hasMorePages = true;
	}
	
	/**
	 * Gets the information used to get this page
	 * @return a {@link FlightDetails}
	 */
	public FlightDetails getSearchDetails() {
		FlightDetails flightDetails = new FlightDetails();
		
		waitUntilElementIsPresent(LOADING_MASK_SELECTOR, true);
		WebElement loadingMask = getDriver().findElement(LOADING_MASK_SELECTOR);
		waitUntilElementIsNotVisible(loadingMask, true);
		
		waitUntilElementIsPresent(ORIGIN_BUTTON_SELECTOR, true);
		WebElement originFakeButton = getDriver().findElement(ORIGIN_BUTTON_SELECTOR);
		waitUntilElementIsClickable(originFakeButton);
		originFakeButton.click();
		
		flightDetails.setRooms(getRooms());
		flightDetails.setDepartureLocation(getDepartureLocation());
		flightDetails.setArrivalLocation(getArrivalLocation());
		flightDetails.setAdultSeats(getAdultCount());
		flightDetails.setChildrenSeats(getChildrenCount());
		flightDetails.setDepartureDate(getArrivalDate());
		flightDetails.setReturningDate(getReturnDate());
		
		LOGGER.info("\nSearch details: " + flightDetails);
		
		return flightDetails;
	}
	
	/**
	 * Gets the departure location
	 * @return
	 */
	public String getDepartureLocation() {
		String result = null;
		WebElement departureInput = getDriver().findElement(ORIGIN_INPUT_SELECTOR);
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
		WebElement arrivalInput = getDriver().findElement(DESTINATION_INPUT_SELECTOR);
		waitUntilElementIsVisible(arrivalInput);
		
		result = arrivalInput.getAttribute("value");
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Arrival: " + result);
		}
		
		return result;
	}
	
	/**
	 * Gets the number of rooms selected
	 * @return
	 */
	public int getRooms() {
		String rooms;
		WebElement roomsSpan = getDriver().findElement(ROOMS_SPAN_SELECTOR);
		waitUntilElementIsVisible(roomsSpan);
		
		rooms = roomsSpan.getText();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Rooms: " + rooms);	
		}
		
		rooms = rooms.split(" ")[0];
		
		return Integer.parseInt(rooms.trim());
	}
	
	/**
	 * Gets the number of adult seats selected
	 * @return
	 */
	public int getAdultCount() {
		String adultTravelers;
		WebElement adultTravelersSpan = getDriver().findElement(ADULT_COUNT_SPAN_SELECTOR);
		waitUntilElementIsVisible(adultTravelersSpan);
		
		adultTravelers = adultTravelersSpan.getText();
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Adult seats: " + adultTravelers);	
		}
		
		adultTravelers = adultTravelers.split(" ")[0];
		
		return Integer.parseInt(adultTravelers.trim());
	}
	
	/**
	 * Gets the number of children seats selected
	 * @return
	 */
	public int getChildrenCount() {
		String childrenTravelers;
		try {
			WebElement childrenTravelersSpan = getDriver().findElement(CHILDREN_COUNT_SPAN_SELECTOR);
			waitUntilElementIsVisible(childrenTravelersSpan);
			
			childrenTravelers = childrenTravelersSpan.getText();
			childrenTravelers = childrenTravelers.split(" ")[0];
		}catch(NoSuchElementException e) {
			childrenTravelers = "0";
		}
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Child seats: " + childrenTravelers);	
		}
		
		return Integer.parseInt(childrenTravelers.trim());
	}

	/**
	 * Gets the selected departure date
	 * @return
	 */
	public LocalDate getArrivalDate() {
		WebElement departureDateInput = getDriver().findElement(DEPARTURE_DATE_INPUT_SELECTOR);
		waitUntilElementIsVisible(departureDateInput);
		String format = departureDateInput.getAttribute("placeholder");
		format = format.replaceAll("m", "M");
		String value = departureDateInput.getAttribute("value");
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Departure date: " + value);
		}
		
		// FIXME
		String[] dateElems = value.split("/");
		if(dateElems[0].length() == 1) {
			value = "0" + dateElems[0] + "/" + dateElems[1] + "/" + dateElems[2];
		}
		
		if(dateElems[1].length() == 1) {
			value = dateElems[0] + "/0" + dateElems[1] + "/" + dateElems[2];
		}
		
		if(dateElems[2].length() == 1) {
			value = dateElems[0] + "/" + dateElems[1] + "/0" + dateElems[2];
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
		WebElement returnDateInput = getDriver().findElement(RETURN_DATE_INPUT_SELECTOR);
		waitUntilElementIsVisible(returnDateInput);
		String format = returnDateInput.getAttribute("placeholder");
		format = format.replaceAll("m", "M");
		String value = returnDateInput.getAttribute("value");
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Return date: " + value);
		}
		
		// FIXME
		String[] dateElems = value.split("/");
		if(dateElems[0].length() == 1) {
			value = "0" + dateElems[0] + "/" + dateElems[1] + "/" + dateElems[2];
		}
		
		if(dateElems[1].length() == 1) {
			value = dateElems[0] + "/0" + dateElems[1] + "/" + dateElems[2];
		}
		
		if(dateElems[2].length() == 1) {
			value = dateElems[0] + "/" + dateElems[1] + "/0" + dateElems[2];
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
	 * Orders the hotels results by the given option text
	 * @param orderText The option visible text to order by
	 * @return a List of String with the price of the ordered results (TODO: Should return the resulting list of hotels details)
	 */
	public List<String> orderResultsBy(String orderText) {
		LOGGER.info("Order results by: " + orderText);
		waitUntilElementIsPresent(SORT_CONTAINER_SELECTOR, true);
		WebElement sortContainer = getDriver().findElement(SORT_CONTAINER_SELECTOR);
		
		String sortButtonPath = String.format(SORT_OPTION_PATH, orderText);
		WebElement sortOption = sortContainer.findElement(By.xpath(sortButtonPath));
		
		waitUntilElementIsClickable(sortOption);
		sortOption.click();
		
		try {
			waitUntilElementIsPresent(ORDERING_MASK_SELECTOR, false);
		}catch(TimeoutException e) {
			LOGGER.info("Mask doesn't appeared");
		}
		
		try {
			WebElement maskElement = getDriver().findElement(ORDERING_MASK_SELECTOR);
			waitUntilElementIsNotPresent(maskElement);
		}catch(NoSuchElementException e) {
			LOGGER.info("Mask already disappeared");
		}
		
		WebElement resultsContainer = getDriver().findElement(RESULTS_CONTAINER_SELECTOR);
		List<WebElement> priceElements = resultsContainer.findElements(PRICE_ITEMS_SELECTOR);
		List<String> prices = priceElements.stream().map(p -> p.getText()).collect(Collectors.toList());
		
		return prices;
	}

	/**
	 * Selects a hotel by the number of stars and fills a {@link HotelDetails} with the information of the selected hotel.
	 * @param numberOfStars The number of stars for the hotel to select.
	 * @return A {@link HotelDetails} with the information of the selected hotel.
	 */
	public HotelDetailsPage selectHotel(float numberOfStars) {
		HotelDetails hotelDetails = new HotelDetails();
		WebElement resultsContainer = getDriver().findElement(RESULTS_CONTAINER_SELECTOR);
		
		WebElement selectedCard = null;
		List<WebElement> hotelsCards;
		do {
			hotelsCards = resultsContainer.findElements(HOTEL_CARD_SELECTOR);
			for (WebElement hotelCard : hotelsCards) {
				WebElement starsSpan = hotelCard.findElement(STARS_ITEM_SELECTOR);
				String stars  = starsSpan.getAttribute("title");
				if(LOGGER.isDebugEnabled()) {
					LOGGER.debug("Stars found: " + stars);						
				}
				
				float foundStars = Float.parseFloat(stars);
				
				if(foundStars >= numberOfStars) {
					selectedCard = hotelCard;
					getActions().moveToElement(selectedCard).perform();
					break;
				}
			}
			
			if(selectedCard == null) {
				clickNextPage();
			}
		} while(selectedCard == null && hasMorePages);

		if(selectedCard != null) {
			waitUntilElementIsPresent(ORIGIN_BUTTON_SELECTOR, true);
			WebElement originFakeButton = getDriver().findElement(ORIGIN_BUTTON_SELECTOR);
			waitUntilElementIsClickable(originFakeButton);
			originFakeButton.click();
			
			hotelDetails.setAdultCount(getAdultCount());
			hotelDetails.setChildrenCount(getChildrenCount());
			hotelDetails.setArrivalDate(getArrivalDate());
			hotelDetails.setReturningDate(getReturnDate());
			hotelDetails.setRooms(getRooms());

			WebElement nameElement = selectedCard.findElement(HOTEL_NAME_SELECTOR);
			hotelDetails.setName(nameElement.getText());
			
			WebElement priceElement = selectedCard.findElement(PRICE_PER_TRAVELER_SELECTOR);
			hotelDetails.setPricePerTraveler(new BigDecimal(priceElement.getText().substring(1).replace(",", "").trim()));
			
			WebElement starsElement = selectedCard.findElement(STARS_ITEM_SELECTOR);
			hotelDetails.setStars(Float.parseFloat(starsElement.getAttribute("title")));
			
			WebElement selectLink = selectedCard.findElement(SELECT_HOTEL_LINK);
			waitUntilElementIsClickable(selectLink);
			
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("\nSelected hotel information: " + hotelDetails);
			}
			
			String mainWindowHandle = getDriver().getWindowHandle();
			selectLink.click();
			
			for(String windowHandle : getDriver().getWindowHandles()) {
				if(!windowHandle.equals(mainWindowHandle)) {
					getDriver().switchTo().window(windowHandle);
					break;
				}
			}
		}
		
		HotelDetailsPage hotelPage = new HotelDetailsPage(getDriver());
		hotelPage.setHotelDetails(hotelDetails);
		
		return hotelPage;
	}
	
	protected void clickNextPage() {
		WebElement nextButton = getDriver().findElement(NEXT_PAGE_BUTTON_SELECTOR);
		boolean isDisabled = nextButton.getAttribute("disabled") != null;
		
		if(isDisabled) {
			LOGGER.info("No more pages found...");
			hasMorePages = false;
		}else {
			waitUntilElementIsClickable(nextButton);
			nextButton.click();
			try {
				waitUntilElementIsPresent(ORDERING_MASK_SELECTOR, false);
			}catch(TimeoutException e) {
				LOGGER.info("Mask doesn't appeared");
			}
			
			try {
				WebElement maskElement = getDriver().findElement(ORDERING_MASK_SELECTOR);
				waitUntilElementIsNotPresent(maskElement);
			}catch(NoSuchElementException e) {
				LOGGER.info("Mask already disappeared");
			}
		}
	}
	
}
