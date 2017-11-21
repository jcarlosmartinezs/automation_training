package com.globantu.automation.carlos_segundo.travelocity;

import java.math.BigDecimal;

public class CarDetails {
	
	private int passengers;
	
	private int bags;

	private String description;
	
	private BigDecimal price;
	
	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("\npassengers: ").append(passengers);
		str.append("\nbags: ").append(bags);
		str.append("\ndescription: ").append(description);
		str.append("\nprice: ").append(price);
		
		return str.toString();
	}
	
}
