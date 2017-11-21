package com.globantu.automation.carlos_segundo.travelocity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HotelDetails {
	
	private String name;
	
	private float stars;
	
	private int adultCount;
	
	private int childrenCount;
	
	private int rooms;
	
	private String roomType;
	
	private LocalDate arrivalDate;
	
	private LocalDate returningDate;
	
	private BigDecimal pricePerTraveler;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalDate getReturningDate() {
		return returningDate;
	}

	public void setReturningDate(LocalDate returningDate) {
		this.returningDate = returningDate;
	}

	public BigDecimal getPricePerTraveler() {
		return pricePerTraveler;
	}

	public void setPricePerTraveler(BigDecimal pricePerTraveler) {
		this.pricePerTraveler = pricePerTraveler;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("\nname: ").append(name);
		str.append("\nstars: ").append(stars);
		str.append("\nadultCount: ").append(adultCount);
		str.append("\nchildrenCount: ").append(childrenCount);
		str.append("\nrooms: ").append(rooms);
		str.append("\nroomType: ").append(roomType);
		str.append("\narrivalDate: ").append(arrivalDate);
		str.append("\nreturningDate: ").append(returningDate);
		str.append("\npricePerTraveler: ").append(pricePerTraveler);
		
		return str.toString();
	}
	
}
