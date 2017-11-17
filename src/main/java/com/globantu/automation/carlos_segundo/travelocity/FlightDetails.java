package com.globantu.automation.carlos_segundo.travelocity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FlightDetails {
	
	private String departureLocation;
	
	private String arrivalLocation;
	
	private LocalDate departureDate;
	
	private LocalDate returningDate;
	
	private int adultSeats;
	
	private int childrenSeats;
	
	private int rooms;
	
	private String departureTime;
	
	private String arrivalTime;
	
	private String duration;
	
	private BigDecimal pricePerTraveler;
	
	private String airlineName;
	
	private String airports;
	
	private int numStops;

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public BigDecimal getPricePerTraveler() {
		return pricePerTraveler;
	}

	public void setPricePerTraveler(BigDecimal pricePerTraveler) {
		this.pricePerTraveler = pricePerTraveler;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public int getNumStops() {
		return numStops;
	}

	public void setNumStops(int numStops) {
		this.numStops = numStops;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAirports() {
		return airports;
	}

	public void setAirports(String airports) {
		this.airports = airports;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public void setDepartureLocation(String departureLocation) {
		this.departureLocation = departureLocation;
	}

	public String getArrivalLocation() {
		return arrivalLocation;
	}

	public void setArrivalLocation(String destinationLocation) {
		this.arrivalLocation = destinationLocation;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalDate getReturningDate() {
		return returningDate;
	}

	public void setReturningDate(LocalDate returningDate) {
		this.returningDate = returningDate;
	}

	public int getAdultSeats() {
		return adultSeats;
	}

	public void setAdultSeats(int adultSeats) {
		this.adultSeats = adultSeats;
	}

	public int getChildrenSeats() {
		return childrenSeats;
	}

	public void setChildrenSeats(int childrenSeats) {
		this.childrenSeats = childrenSeats;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\ndepartureLocation: ").append(departureLocation);
		str.append("\narrivalLocation: ").append(arrivalLocation);
		str.append("\ndepartureDate: ").append(departureDate);
		str.append("\nreturningDate: ").append(returningDate);
		str.append("\nrooms: ").append(rooms);
		str.append("\nadultSeats: ").append(adultSeats);
		str.append("\nchildrenSeats: ").append(childrenSeats);
		str.append("\ndepartureTime: ").append(departureTime);
		str.append("\narrivalTime: ").append(arrivalTime);
		str.append("\nduration: ").append(duration);
		str.append("\npricePerTraveler: ").append(pricePerTraveler);
		str.append("\nairlineName: ").append(airlineName);
		str.append("\nairports: ").append(airports);
		str.append("\nnumStops: ").append(numStops);
		
		return str.toString();
	}

}
