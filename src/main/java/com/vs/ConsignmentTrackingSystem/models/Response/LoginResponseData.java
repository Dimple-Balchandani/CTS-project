package com.vs.ConsignmentTrackingSystem.models.Response;

public class LoginResponseData {

	private long id;
	
	private String username;
	
	private String password;
	
	private String role;
	
	private Boolean isReportAccess;
	
	private String companyName;
	
	private String userType;
	
	private String reportFields;
	
	private String authToken;
	
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
	
	
	//private String message;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getIsReportAccess() {
		return isReportAccess;
	}
	public void setIsReportAccess(Boolean isReportAccess) {
		this.isReportAccess = isReportAccess;
	}
	
	
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
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
}
