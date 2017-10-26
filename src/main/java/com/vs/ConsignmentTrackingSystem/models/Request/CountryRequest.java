package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryRequest {
	@JsonProperty
	String countryName;
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
