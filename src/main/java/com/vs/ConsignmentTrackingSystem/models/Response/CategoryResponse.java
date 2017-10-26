package com.vs.ConsignmentTrackingSystem.models.Response;

public class CategoryResponse {

	private String message;
	
	private CategoryResponseData data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CategoryResponseData getData() {
		return data;
	}

	public void setData(CategoryResponseData data) {
		this.data = data;
	} 
}
