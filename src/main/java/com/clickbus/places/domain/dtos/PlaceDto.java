package com.clickbus.places.domain.dtos;

import javax.validation.constraints.NotBlank;

public class PlaceDto {

	@NotBlank(message = "Name is required.")
	private String name;
	@NotBlank(message = "City is required.")
	private String city;
	@NotBlank(message = "State is required.")
	private String state;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}	
}
