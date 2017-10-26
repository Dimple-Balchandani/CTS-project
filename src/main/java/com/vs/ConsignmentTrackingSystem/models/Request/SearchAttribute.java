package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchAttribute {

	@JsonProperty
	private String key;
	
	@JsonProperty
	private String value;

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "key : " + key + "value : " + value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
