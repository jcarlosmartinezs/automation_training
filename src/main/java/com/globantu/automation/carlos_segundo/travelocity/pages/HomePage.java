package com.globantu.automation.carlos_segundo.travelocity.pages;

import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the home page for Travelocity
 * @author carlos.segundo
 *
 */
public class HomePage extends BasePage {

	private final String PAGE_URL = "http://www.travelocity.com/";
	
	private final String CALENDAR_NEXT_BUTTON_PATH = ".//button[contains(@class,'datepicker-paging datepicker-next')]";
	
	private final String SELECTED_DATE_PATH = ".//button[@data-year='%s' and @data-month='%s' and @data-day='%s']";

	private final String DEPARTING_CALENDAR_ID = "flight-departing-wrapper-hp-flight";
	
	private final String RETURNING_CALENDAR_ID = "flight-returning-wrapper-hp-flight";
	
	@FindBy(id="tab-flight-tab-hp")
	private WebElement flightsButton;
	
	@FindBy(id="flight-type-roundtrip-label-hp-flight")
	private WebElement roundtripButton;
	
	@FindBy(id="flight-origin-hp-flight")
	private WebElement originInput;
	
	@FindBy(id="flight-destination-hp-flight")
	private WebElement destinationInput;
	
	@FindBy(id="flight-departing-hp-flight")
	private WebElement departingDateInput;
	
	@FindBy(id="flight-returning-hp-flight")
	private WebElement returningDateInput;
	
	@FindBy(id=DEPARTING_CALENDAR_ID)
	private WebElement departingCalendar;
	
	@FindBy(id=RETURNING_CALENDAR_ID)
	private WebElement returningCalendar;
	
	@FindBy(id="flight-adults-hp-flight")
	private WebElement adultsSelect;
	
	@FindBy(id="flight-children-hp-flight")
	private WebElement childrenSelect;
	
	@FindBy(xpath="//form[@id='gcw-flights-form-hp-flight']//button[@type='submit']")
	private WebElement searchButton;
	
	public HomePage(WebDriver driver) {
		super(driver);
		driver.get(PAGE_URL);
	}
	
	/**
	 * Finds a flight with the given parameters
	 * 
	 * @param from The origin
	 * @param to The destination
	 * @param departDaysFromNow Number of days after current date for the departure
	 * @param returnDaysFromDepart Number of days after departure for the return
	 * @param adults Number of adult seats
	 * @param children Number of children seats
	 * @return a {@link FlightSearchPage}
	 */
	public FlightSearchPage findRoundFlight(String from, String to, 
			int departDaysFromNow, int returnDaysFromDepart, int adults, int children) {
		
		waitUntilElementIsClickable(flightsButton);
		flightsButton.click();
		
		waitUntilElementIsClickable(roundtripButton);
		roundtripButton.click();
		
		waitUntilElementIsClickable(originInput);
		originInput.sendKeys(from);
		
		waitUntilElementIsClickable(destinationInput);
		destinationInput.sendKeys(to);

		Date departDate = selectDepartDate(departDaysFromNow);
		selectReturnDate(departDate, returnDaysFromDepart);
		
		selectElementByText(adultsSelect, Integer.toString(adults));
		selectElementByText(childrenSelect, Integer.toString(children));
		
		waitUntilElementIsClickable(searchButton);
		searchButton.click();
		
		return new FlightSearchPage(getDriver());
	}
	
	/**
	 * Selects the depart date in this page
	 * @param departDaysFromNow Number of days after current date for the depart date
	 * @return a {@link Date} representing the selected depart date
	 */
	private Date selectDepartDate(int departDaysFromNow) {
		waitUntilElementIsClickable(departingDateInput);
		departingDateInput.click();
		
		waitUntilElementIsVisible(departingCalendar);

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(new Date());
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(new Date());
		endCalendar.add(Calendar.DAY_OF_MONTH, departDaysFromNow);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		WebElement nextMonthBtn = departingCalendar.findElement(By.xpath(CALENDAR_NEXT_BUTTON_PATH));
		for (int i = 1; i < diffMonth; i++) {
			nextMonthBtn.click();			
		}
		
		int departYear = endCalendar.get(Calendar.YEAR);
		int departMonth = endCalendar.get(Calendar.MONTH);
		int departDay = endCalendar.get(Calendar.DAY_OF_MONTH);
				
		String selectedDatePath = String.format(SELECTED_DATE_PATH, departYear, departMonth, departDay);
		WebElement selectedDateBtn = departingCalendar.findElement(By.xpath(selectedDatePath));
		selectedDateBtn.click();
		
		return endCalendar.getTime();
	}
	
	/**
	 * Selects the return date in this page
	 * @param departDate The depart date
	 * @param departDaysFromNow Number of days after the depart date for the return date
	 * @return a {@link Date} representing the selected return date
	 */
	private Date selectReturnDate(Date departDate, int returnDaysFromDepart) {
		waitUntilElementIsClickable(returningDateInput);
		returningDateInput.click();
		
		waitUntilElementIsVisible(returningCalendar);

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(new Date());
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(departDate);
		endCalendar.add(Calendar.DAY_OF_MONTH, returnDaysFromDepart);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		WebElement nextMonthBtn = returningCalendar.findElement(By.xpath(CALENDAR_NEXT_BUTTON_PATH));
		for (int i = 1; i < diffMonth; i++) {
			nextMonthBtn.click();			
		}
		
		int departYear = endCalendar.get(Calendar.YEAR);
		int departMonth = endCalendar.get(Calendar.MONTH);
		int departDay = endCalendar.get(Calendar.DAY_OF_MONTH);
				
		String selectedDatePath = String.format(SELECTED_DATE_PATH, departYear, departMonth, departDay);
		WebElement selectedDateBtn = returningCalendar.findElement(By.xpath(selectedDatePath));
		selectedDateBtn.click();
		
		return endCalendar.getTime();
	}
}
