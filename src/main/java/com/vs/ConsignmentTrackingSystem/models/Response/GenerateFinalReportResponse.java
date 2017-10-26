package com.vs.ConsignmentTrackingSystem.models.Response;

import java.util.List;

public class GenerateFinalReportResponse {
	
	private String Category;
	
	private List taskData;

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public List getTaskData() {
		return taskData;
	}

	public void setTaskData(List taskData) {
		this.taskData = taskData;
	}

}
