package com.vs.ConsignmentTrackingSystem.models.Request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JobSearchRequest {
	
	@JsonProperty
	private List<SearchAttribute> info;

	public List<SearchAttribute> getInfo() {
		return info;
	}

	public void setInfo(List<SearchAttribute> info) {
		this.info = info;
	}
   
	@Override
	public String toString(){
		return info.toString();
	}

	
}
