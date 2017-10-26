package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditTaskDetailsRequest {
	
	@JsonProperty
	private String taskTitle;
	
	@JsonProperty
	private String taskType;
	
	@JsonProperty
	private long taskPendingFrom;
	
	@JsonProperty
	private int duration;
	
	@JsonProperty
	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public long getTaskPendingFrom() {
		return taskPendingFrom;
	}

	public void setTaskPendingFrom(long taskPendingFrom) {
		this.taskPendingFrom = taskPendingFrom;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	

}
