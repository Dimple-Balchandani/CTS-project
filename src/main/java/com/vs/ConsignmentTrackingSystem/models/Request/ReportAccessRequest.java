package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportAccessRequest {
	
	@JsonProperty
	private String type;
	
	@JsonProperty
	private String idList;
	
	@JsonProperty
	private String customvalue;

	public String getCustomvalue() {
		return customvalue;
	}

	public void setCustomvalue(String customvalue) {
		this.customvalue = customvalue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

}
