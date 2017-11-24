package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the home page for Travelocity
 * @author carlos.segundo
 *
 */
public class HomePage extends BasePage {
	
	private static final Logger LOGGER = Logger.getLogger(FlightInformationPage.class);

	private final String PAGE_URL = "http://www.travelocity.com/";
	
	private final String CALENDAR_PREV_BUTTON_PATH = ".//button[contains(@class,'datepicker-paging datepicker-prev')]";
	
	private final String CALENDAR_NEXT_BUTTON_PATH = ".//button[contains(@class,'datepicker-paging datepicker-next')]";
	
	private final String SELECTED_DATE_PATH = ".//button[@data-year='%s' and @data-month='%s' and @data-day='%s']";

	private final String FLIGHT_DEPARTING_CALENDAR_ID = "flight-departing-wrapper-hp-flight";
	
	private final String FLIGHT_RETURNING_CALENDAR_ID = "flight-returning-wrapper-hp-flight";
	
	private final String PACKAGE_DEPARTING_CALENDAR_ID = "package-departing-wrapper-hp-package";
	
	private final String PACKAGE_RETURNING_CALENDAR_ID = "package-returning-wrapper-hp-package";
	
	private final String FLIGHT_AND_HOTEL_OPTION_PATH = "//*[@data-gcw-sub-nav-option-name='flight-hotel']/..";
	
	@FindBy(id="tab-flight-tab-hp")
	private WebElement flightsButton;
	
	@FindBy(id="tab-package-tab-hp")
	private WebElement flightAndHotelButton;
	
	@FindBy(id="flight-type-roundtrip-label-hp-flight")
	private WebElement roundtripButton;
	
	@FindBy(id="flight-origin-hp-flight")
	private WebElement flightOriginInput;
	
	@FindBy(id="flight-destination-hp-flight")
	private WebElement flightDestinationInput;
	
	@FindBy(id="package-origin-hp-package")
	private WebElement packageOriginInput;
	
	@FindBy(id="package-destination-hp-package")
	private WebElement packageDestinationInput;
	
	@FindBy(id="flight-departing-hp-flight")
	private WebElement flightDepartingDateInput;
	
	@FindBy(id="flight-returning-hp-flight")
	private WebElement flightReturningDateInput;
	
	@FindBy(id="package-departing-hp-package")
	private WebElement packageDepartingDateInput;
	
	@FindBy(id="package-returning-hp-package")
	private WebElement packageReturningDateInput;
	
	@FindBy(id=FLIGHT_DEPARTING_CALENDAR_ID)
	private WebElement flightDepartingCalendar;
	
	@FindBy(id=FLIGHT_RETURNING_CALENDAR_ID)
	private WebElement flightReturningCalendar;
	
	@FindBy(id=PACKAGE_DEPARTING_CALENDAR_ID)
	private WebElement packageDepartingCalendar;
	
	@FindBy(id=PACKAGE_RETURNING_CALENDAR_ID)
	private WebElement packageReturningCalendar;
	
	@FindBy(id="package-rooms-hp-package")
	private WebElement roomsSelect;
	
	@FindBy(id="flight-adults-hp-flight")
	private WebElement flightAdultsSelect;
	
	@FindBy(id="package-1-adults-hp-package")
	private WebElement packageAdultSelect;
	
	@FindBy(id="flight-children-hp-flight")
	private WebElement flightChildrenSelect;
	
	@FindBy(id="package-1-children-hp-package")
	private WebElement packageChildrenSelect;
	
	@FindBy(xpath="//form[@id='gcw-flights-form-hp-flight']//button[@type='submit']")
	private WebElement flightSearchButton;
	
	@FindBy(id="search-button-hp-package")
	private WebElement packageSearchButton;
	
	public HomePage(WebDriver driver) {
		super(driver);
		driver.get(PAGE_URL);
	}
	
	/**
	 * Finds a flight with the given parameters
	 * 
	 * @param from The origin
	 * @param to The destination
	 * @param departureDaysFromNow Number of days after current date for the departure
	 * @param durationDays Number of days after departure for the return
	 * @param adults Number of adult seats
	 * @param children Number of children seats
	 * @return a {@link FlightSearchPage}
	 */
	public FlightSearchPage searchRoundFlight(String from, String to, 
			int departureDaysFromNow, int durationDays, int adults, int children) {
		
		waitUntilElementIsClickable(flightsButton);
		flightsButton.click();
		
		waitUntilElementIsClickable(roundtripButton);
		roundtripButton.click();
		
		waitUntilElementIsClickable(flightOriginInput);
		flightOriginInput.sendKeys(from);
		
		waitUntilElementIsClickable(flightDestinationInput);
		flightDestinationInput.sendKeys(to);

		waitUntilElementIsClickable(flightDepartingDateInput);
		flightDepartingDateInput.clear();
		flightDepartingDateInput.click();
		selectDate(flightDepartingCalendar, departureDaysFromNow);

		int daysFromNowToReturn = departureDaysFromNow + durationDays;
		waitUntilElementIsClickable(flightReturningDateInput);
		flightReturningDateInput.clear();
		flightReturningDateInput.click();
		selectDate(flightReturningCalendar, daysFromNowToReturn);
		
		selectElementByText(flightAdultsSelect, Integer.toString(adults));
		selectElementByText(flightChildrenSelect, Integer.toString(children));
		
		waitUntilElementIsClickable(flightSearchButton);
		flightSearchButton.click();
		
		return new FlightSearchPage(getDriver());
	}
	
	/**
	 * Finds a flight and hotel with the given parameters
	 * 
	 * @param from The origin
	 * @param to The destination
	 * @param departDaysFromNow Number of days after current date for the departure
	 * @param durationDays Number of days after departure for the return
	 * @param rooms Number of rooms
	 * @param adults Number of adult seats
	 * @param children Number of children seats
	 * @return a {@link FlightSearchPage}
	 */
	public HotelSearchPage searchRoundFlightWithHotel(String from, String to, 
			int departureDaysFromNow, int durationDays, int rooms, int adults, int children) {
		
		waitUntilElementIsClickable(flightAndHotelButton);
		flightAndHotelButton.click();
		
		WebElement flightAndHotelOption = getDriver().findElement(By.xpath(FLIGHT_AND_HOTEL_OPTION_PATH));
		waitUntilElementIsClickable(flightAndHotelOption);
		flightAndHotelOption.click();
		
		waitUntilElementIsClickable(packageOriginInput);
		packageOriginInput.sendKeys(from);
		
		waitUntilElementIsClickable(packageDestinationInput);
		packageDestinationInput.sendKeys(to);

		waitUntilElementIsClickable(packageDepartingDateInput);
		packageDepartingDateInput.clear();
		packageDepartingDateInput.click();
		selectDate(packageDepartingCalendar, departureDaysFromNow);
		
		int daysFromNowToReturn = departureDaysFromNow + durationDays;
		waitUntilElementIsClickable(packageReturningDateInput);
		packageReturningDateInput.clear();
		packageReturningDateInput.click();
		selectDate(packageReturningCalendar, daysFromNowToReturn);
		
		selectElementByText(roomsSelect, Integer.toString(rooms));
		selectElementByText(packageAdultSelect, Integer.toString(adults));
		selectElementByText(packageChildrenSelect, Integer.toString(children));
		
		waitUntilElementIsClickable(packageSearchButton);
		packageSearchButton.click();
		
		return new HotelSearchPage(getDriver());
	}
	
	/**
	 * Selects a date in the given calendar
	 * @param daysAfterNow Number of days after current date, the calculated date will be the selected
	 * @return a {@link Date} representing the selected date
	 */
	private Date selectDate(WebElement calendar, int daysAfterNow) {
		Date selectedDate = null;
		waitUntilElementIsVisible(calendar);

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(new Date());
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(new Date());
		endCalendar.add(Calendar.DAY_OF_MONTH, daysAfterNow);

		boolean firstMonth = false;
		
		// Go to the first page first
		do {
			try {
				WebElement prevMonth = calendar.findElement(By.xpath(CALENDAR_PREV_BUTTON_PATH));
				if(prevMonth.isDisplayed()) {
					waitUntilElementIsClickable(prevMonth);
					prevMonth.click();
				}else {
					firstMonth = true;
				}
			}catch(NoSuchElementException e) {
				firstMonth = true;
			}
			
		}while(!firstMonth);
		
		int departYear = endCalendar.get(Calendar.YEAR);
		int departMonth = endCalendar.get(Calendar.MONTH);
		int departDay = endCalendar.get(Calendar.DAY_OF_MONTH);
				
		String selectedDatePath = String.format(SELECTED_DATE_PATH, departYear, departMonth, departDay);
		WebElement selectedDateBtn = null;
		boolean noDatesAvailable = false;
		
		// Validate if current page contains the required date
		do {
			try {
				selectedDateBtn = calendar.findElement(By.xpath(selectedDatePath));
			}catch(NoSuchElementException e) {
				try {
					WebElement nextMonthBtn = calendar.findElement(By.xpath(CALENDAR_NEXT_BUTTON_PATH));
					waitUntilElementIsClickable(nextMonthBtn);
					nextMonthBtn.click();
				}catch(NoSuchElementException ex) {
					LOGGER.error("Required date " + daysAfterNow + " is not available");
					noDatesAvailable = true;
				}
			}
			
		}while(selectedDateBtn == null && !noDatesAvailable);
		
		if(selectedDateBtn != null) {
			selectedDateBtn.click();
			selectedDate = endCalendar.getTime();
		}
		
		return selectedDate;
	}
	
}
