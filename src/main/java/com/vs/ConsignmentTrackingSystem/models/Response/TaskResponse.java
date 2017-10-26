package com.vs.ConsignmentTrackingSystem.models.Response;

public class TaskResponse {

	private String message;

	private TaskResponseData data;
	
	public TaskResponseData getData() {
		return data;
	}

	public void setData(TaskResponseData data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
