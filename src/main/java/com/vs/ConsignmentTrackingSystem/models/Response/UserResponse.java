package com.vs.ConsignmentTrackingSystem.models.Response;

public class UserResponse {

	private String message;
	
	private UserResponseData data;

	public UserResponseData getData() {
		return data;
	}

	public void setData(UserResponseData data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	
}
