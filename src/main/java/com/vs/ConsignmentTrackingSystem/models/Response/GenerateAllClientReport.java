package com.vs.ConsignmentTrackingSystem.models.Response;

import java.util.List;

public class GenerateAllClientReport {

	private String clientName;
	
	private List jobData;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List getJobData() {
		return jobData;
	}

	public void setJobData(List jobData) {
		this.jobData = jobData;
	}
	
}
