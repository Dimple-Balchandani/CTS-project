package com.vs.ConsignmentTrackingSystem.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vs.ConsignmentTrackingSystem.utilities.Constants;
/**
 * 
 * @author bharti
 * Class that maps to user database table
 */
@Entity
@Table(name = Constants.USER_TABLE_NAME)
public class UserEntity implements Serializable {
	@Id
	@Column(name = Constants.USER_ID)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(name = "username")
	private String username;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "role")
	private String role;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "is_report_access", columnDefinition = "boolean default false")
	private Boolean isReportAccess;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "report_fields")
	private String reportFields;

	@Column(name = "login_time")
	private long loginTime;

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public Boolean getIsReportAccess() {
		return isReportAccess;
	}

	public void setIsReportAccess(Boolean isReportAccess) {
		this.isReportAccess = isReportAccess;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

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

	public Boolean getReportAccess() {
		return isReportAccess;
	}

	public void setReportAccess(Boolean isReportAccess) {
		this.isReportAccess = isReportAccess;
	}

	@Override
	public String toString() {
		return "Id: " + this.id + "\nName:" + this.username + "\nPassword: " + this.password + "\nRole: " + this.role
				+ "\nReportAccess: " + this.isReportAccess + "\nCompanyName: " + this.companyName;
	}
}
