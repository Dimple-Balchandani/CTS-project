package com.vs.ConsignmentTrackingSystem.models.Request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditTaskRequest {

	@JsonProperty
	private int jobNo;
	
	@JsonProperty
	private long taskId;
	
	@JsonProperty
	private Date taskDate;
	
	@JsonProperty
	private String taskText;

	@JsonProperty
	private String taskStatus;

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public int getJobNo() {
		return jobNo;
	}

	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskText() {
		return taskText;
	}

//	public String getTaskDate() {
//		return taskDate;
//	}
//
//	public void setTaskDate(String taskDate) {
//		this.taskDate = taskDate;
//	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	
	@Override
	public String toString() {
		return "jobNo :" + jobNo + "taskid :" + taskId + "taskDate :" +taskDate + "taskText : " +taskText;
	}

}
