package com.vs.ConsignmentTrackingSystem.models.Response;

import java.util.List;

public class GetResponse {
	private String message;
	private List data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	
}
