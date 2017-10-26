package com.vs.ConsignmentTrackingSystem.models.Response;

public class UserResponseData {

	private long id;
	private String username;
	private String password;
	private String role;
	private Boolean isReportAccess;
	private String companyName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
