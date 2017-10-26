package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCategoryRequest {
	
	@JsonProperty
	private String categoryName;
	
	@JsonProperty
	private String taskIdList;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTaskIdList() {
		return taskIdList;
	}

	public void setTaskIdList(String taskIdList) {
		this.taskIdList = taskIdList;
	}

}
