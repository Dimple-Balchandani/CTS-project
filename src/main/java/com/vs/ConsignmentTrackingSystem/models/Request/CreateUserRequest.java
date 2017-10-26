package com.vs.ConsignmentTrackingSystem.models.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
	@JsonProperty
	private String username;
	@JsonProperty
	private String password;
	@JsonProperty
	private String role;
	@JsonProperty
	private String companyName;
	@JsonProperty
	private String userType;
	@JsonProperty
	private String reportFields;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getReportFields() {
		return reportFields;
	}

	public void setReportFields(String reportFields) {
		this.reportFields = reportFields;
	}

}
