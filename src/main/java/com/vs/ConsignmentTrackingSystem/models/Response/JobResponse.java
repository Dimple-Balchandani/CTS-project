package com.vs.ConsignmentTrackingSystem.models.Response;

public class JobResponse {
	private String message;
    private JobResponseData data;
	public JobResponseData getData() {
		return data;
	}

	public void setData(JobResponseData data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
