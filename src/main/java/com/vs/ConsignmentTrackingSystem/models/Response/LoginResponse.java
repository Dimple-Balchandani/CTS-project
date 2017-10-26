package com.vs.ConsignmentTrackingSystem.models.Response;

public class LoginResponse {

	private String message;
	private LoginResponseData data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LoginResponseData getData() {
		return data;
	}
	public void setData(LoginResponseData loginResponseData) {
		this.data = loginResponseData;
	}
	
}
