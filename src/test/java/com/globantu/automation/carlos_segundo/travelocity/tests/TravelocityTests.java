package com.globantu.automation.carlos_segundo.travelocity.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.globantu.automation.carlos_segundo.travelocity.FlightDetails;
import com.globantu.automation.carlos_segundo.travelocity.pages.FlightCheckoutPage;
import com.globantu.automation.carlos_segundo.travelocity.pages.FlightInformationPage;
import com.globantu.automation.carlos_segundo.travelocity.pages.FlightSearchPage;
import com.globantu.automation.carlos_segundo.travelocity.pages.HomePage;

public class TravelocityTests extends BaseTest {

	private static final Logger LOGGER = Logger.getLogger(TravelocityTests.class);
	
	private final String LOCATIONS_PATTERN = "(%s)";
	
	private final String FROM_TEST = "LAS";
	
	private final String TO_TEST = "LAX";
	
	private final int DAYS_AFTER_NOW_TEST = 60;
	
	private final int DAYS_AFTER_DEPART_TEST = 5;
	
	private final int ADULTS_SEATS_TEST = 1;
	
	private final int CHILDREN_SEATS_TEST = 0;
	
	private final String RESULTS_ORDER_TEST = "Duration (Shortest)";
	
	private final int DEPARTURE_FLIGHT_OPTION_TEST = 0;
	
	private final int RETURNING_FLIGHT_OPTION_TEST = 2;
	
	@Test
	public void testBookFlight() {
		HomePage homePage = getHomePage();
		
		// Find flights with the given parameters
		FlightSearchPage flightSearchPage =  homePage.findRoundFlight(
				FROM_TEST, TO_TEST, DAYS_AFTER_NOW_TEST, DAYS_AFTER_DEPART_TEST, ADULTS_SEATS_TEST, CHILDREN_SEATS_TEST);

		FlightDetails searchDetails = flightSearchPage.getSearchDetails();
		
		// Validate departure location
		String departure = searchDetails.getDepartureLocation();
		assertNotNull(departure);
		
		String expectedDeparture = String.format(LOCATIONS_PATTERN, FROM_TEST);
		int foundDeparture = departure.lastIndexOf(expectedDeparture);
		assertNotEquals(foundDeparture, -1);
		
		// Validate destination location
		String destination = searchDetails.getArrivalLocation();
		assertNotNull(destination);
		
		String expectedDestination = String.format(LOCATIONS_PATTERN, TO_TEST);
		int foundDestination = destination.lastIndexOf(expectedDestination);
		assertNotEquals(foundDestination, -1);
		
		// Validate departure date
		LocalDate departureDate = searchDetails.getDepartureDate();
		assertNotNull(departureDate);
		
		LocalDate today = LocalDate.now();
		long daysAfternow = ChronoUnit.DAYS.between(today, departureDate);
		assertEquals(daysAfternow, DAYS_AFTER_NOW_TEST);
		
		// Validate return date
		LocalDate returnDate = searchDetails.getReturningDate();
		assertNotNull(returnDate);
		
		long daysAfterDeparture = ChronoUnit.DAYS.between(departureDate, returnDate);
		assertEquals(daysAfterDeparture, DAYS_AFTER_DEPART_TEST);
		
		// Validate number of adult seats
		int adultCount = searchDetails.getAdultSeats();
		assertEquals(adultCount, ADULTS_SEATS_TEST);
		
		// Validate number of children seats
		int childCount = searchDetails.getChildrenSeats();
		assertEquals(childCount, CHILDREN_SEATS_TEST);
		
		// Click on every found flight details
//		flightSearchPage.viewAllFlightsDetails();
		
		// Order results
		List<String> durations = flightSearchPage.orderResultsBy(RESULTS_ORDER_TEST);
		
		// Validate order of results
		boolean validOrder = true;
		int currHoursDuration = 0;
		int currMinsDuration = 0;
		for (String duration : durations) {
			int hoursDuration = Integer.parseInt(duration.substring(0, duration.indexOf("h")));
			int minutesDuration = Integer.parseInt(duration.substring(duration.indexOf("h") + 1, duration.indexOf("m")).trim());
			
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Current duration: " + String.valueOf(currHoursDuration) + "h " + String.valueOf(currMinsDuration) + "m; Next duration: " + duration);
			}
			
			if(hoursDuration < currHoursDuration) {
				validOrder = false;
				break;
			}else if (hoursDuration == currHoursDuration && minutesDuration < currMinsDuration) {
				validOrder = false;
				break;
			}
			
			currHoursDuration = hoursDuration;
			currMinsDuration = minutesDuration;
		}
		
		assertTrue(validOrder);
		
		// Select departure flight
		FlightDetails departFlightDetails = flightSearchPage.selectDepartureFlight(DEPARTURE_FLIGHT_OPTION_TEST);
		assertNotNull(departFlightDetails);
		
		// Select returning flight
		FlightInformationPage detailsPage = flightSearchPage.selectReturningFlight(RETURNING_FLIGHT_OPTION_TEST);
		assertNotNull(detailsPage);
		detailsPage.setDepartureDetails(departFlightDetails);
		
		// Validate departure information
		boolean validFlightDetails = detailsPage.isDepartureInformationvalid();
		assertTrue(validFlightDetails);
		
		// Validate returning information
		validFlightDetails = detailsPage.isReturnInformationvalid();
		assertTrue(validFlightDetails);
		
		// Continue to the checkout page
		FlightCheckoutPage checkoutPage = detailsPage.continueBooking();
		assertNotNull(checkoutPage);
		
		validFlightDetails = checkoutPage.validateCheckoutInfo();
		assertTrue(validFlightDetails);
	}
	
//	@Test
//	public void testBookFlightWithHotelAndCar() {
//
//	}
//	
//	@Test
//	public void testSearchHotelByName() {
//
//	}
//	
//	@Test
//	public void testHotelDatesOutOfRange() {
//
//	}
//	
//	@Test
//	public void testSearchCruises() {
//
//	}
	
//	public void sample() {
//		HomePage homePage = getHomePage();
//		CoffeeFinderPage finderPage = homePage.findCoffee();
//		WebElement result;
//		
//		for(int i = 1; i < 5; i++) {
//			result = finderPage.answerQuestion(i, ANSWERS_CASE1[i - 1]);
//			Assert.assertNotNull(result);
//			Assert.assertEquals(result.getText(), ANSWERS_CASE1[i - 1]);
//		}
//		
//		result = finderPage.findCoffee();
//		Assert.assertNotNull(result);
//		Assert.assertEquals(result.getText(), EXPECTED_RECOMENDATION_CASE1);
//	}
}
