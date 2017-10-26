package com.vs.ConsignmentTrackingSystem.models.Request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartTaskRequest {

	@JsonProperty
	private int jobNo;
	
	@JsonProperty
	private String userType;
	
	@JsonProperty
	private Date startDate;
	
	@JsonProperty
	private long userId;
	
	@JsonProperty
	private long taskId;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
}
