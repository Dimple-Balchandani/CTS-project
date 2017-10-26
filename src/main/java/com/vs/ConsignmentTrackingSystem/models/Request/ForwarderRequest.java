package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForwarderRequest {
	
	@JsonProperty
	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
