package com.vs.ConsignmentTrackingSystem.models.Request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateReportRequest {

	@JsonProperty
	private long exportedId;

	@JsonProperty
	private String reportFields;
	
	public String getReportFields() {
		return reportFields;
	}

	public void setReportFields(String reportFields) {
		this.reportFields = reportFields;
	}

	public long getExportedId() {
		return exportedId;
	}

	public void setExportedId(long exportedId) {
		this.exportedId = exportedId;
	}
}
