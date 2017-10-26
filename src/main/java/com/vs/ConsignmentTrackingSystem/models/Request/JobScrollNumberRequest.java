package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobScrollNumberRequest {
	@JsonProperty
	private long id;
	
	@JsonProperty
	private String scrollNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScrollNumber() {
		return scrollNumber;
	}

	public void setScrollNumber(String scrollNumber) {
		this.scrollNumber = scrollNumber;
	}

	@Override
	public String toString() {
		return "id :" + id + "scrollNumber :" + scrollNumber;
	}

}
