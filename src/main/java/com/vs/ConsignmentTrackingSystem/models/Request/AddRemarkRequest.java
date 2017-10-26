package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddRemarkRequest {
	
	@JsonProperty
	private long taskId;
	
	@JsonProperty
	private int jobNo;
	
	@JsonProperty
	private String remark;
	
	@JsonProperty
	private String type;

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public int getJobNo() {
		return jobNo;
	}

	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}

	
}
