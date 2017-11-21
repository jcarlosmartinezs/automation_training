package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.globantu.automation.carlos_segundo.travelocity.HotelDetails;

/**
 * A representation of the travelocity hotel search page.
 * @author carlos.segundo
 *
 */
public class HotelDetailsPage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(HotelDetailsPage.class);
	
	private final By HOTEL_NAME_SELECTOR = By.id("hotel-name");
	
	private final By HOTEL_STARS_SELECTOR = By.xpath("//*[@id='license-plate']//span[contains(@class, 'stars-grey')]");
	
	private final By PRICE_SELECTOR = By.xpath("//div[@class='lead-price-wrapper']/a[contains(@class, 'price')]");
	
	private final By ROOMS_AND_RATES_LIST_SELECTOR = By.id("rooms-and-rates");
	
	private final By BOOK_BUTTON_SELECTOR = By.xpath(".//a[contains(@class, 'book-button')]");
	
	private HotelDetails hotelDetails;
	
	public HotelDetailsPage(WebDriver driver) {
		super(driver);
	}

	public HotelDetails getHotelDetails() {
		return hotelDetails;
	}

	public void setHotelDetails(HotelDetails hotelDetails) {
		this.hotelDetails = hotelDetails;
	}
	
	/**
	 * Validates the information shown in page corresponds to the selected previously.
	 * @return
	 */
	public boolean isHotelInformationValid() {
		boolean valid = true;
		
		waitUntilElementIsPresent(HOTEL_NAME_SELECTOR, true);
		
		StringBuilder strb = new StringBuilder();
		
		strb.append("\nHotel details: ");
		
		WebElement hotelNameElement = getDriver().findElement(HOTEL_NAME_SELECTOR);
		String hotelName = hotelNameElement.getText();
		strb.append("\nSelected hotel: [").append(hotelDetails.getName())
				.append("], found in page[").append(hotelName).append("]");
		valid = valid && hotelDetails.getName().equalsIgnoreCase(hotelName);
		
		WebElement starsElement = getDriver().findElement(HOTEL_STARS_SELECTOR);
		String stars = starsElement.getAttribute("title");
		strb.append("\nSelected stars: [").append(hotelDetails.getStars())
				.append("], found in page[").append(stars).append("]");
		valid = valid && (hotelDetails.getStars() == Float.parseFloat(stars));
		
		WebElement priceElement = getDriver().findElement(PRICE_SELECTOR);
		String price = priceElement.getText();
		strb.append("\nSelected price: [").append(hotelDetails.getPricePerTraveler())
				.append("], found in page[").append(price).append("]");
		valid = valid && (hotelDetails.getPricePerTraveler().compareTo(new BigDecimal(price.substring(1).replaceAll(",","").trim())) == 0);

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(strb.toString());
		}
		
		return valid;
	}
	
	/**
	 * Clicks the selected hotel room option.
	 * @param option the hotel option, the first option is 0.
	 * @return a {@link FlightSearchPage}
	 */
	public FlightSearchPage selectHotelRoom(int option) {
		LOGGER.info("Select room, option: " + option);
		waitUntilElementIsPresent(ROOMS_AND_RATES_LIST_SELECTOR, true);
		WebElement hotelsList = getDriver().findElement(ROOMS_AND_RATES_LIST_SELECTOR);
		
		List<WebElement> bookButtons = hotelsList.findElements(BOOK_BUTTON_SELECTOR);
		WebElement selectedLink = bookButtons.get(option);
		waitUntilElementIsClickable(selectedLink);
		selectedLink.click();
		
		return new FlightSearchPage(getDriver());
	}
}
