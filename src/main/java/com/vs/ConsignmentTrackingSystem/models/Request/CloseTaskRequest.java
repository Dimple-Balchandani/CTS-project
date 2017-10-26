package com.vs.ConsignmentTrackingSystem.models.Request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CloseTaskRequest {

	@JsonProperty
	private int jobNo;

	@JsonProperty
	private String userType;
	
	@JsonProperty
	private long taskId;

	@JsonProperty
	private Date taskDate;
	
	@JsonProperty
	private String taskText;

	@JsonProperty
	private Date endDate;
	
	@JsonProperty
	private long categoryId;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getJobNo() {
		return jobNo;
	}

	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
}
