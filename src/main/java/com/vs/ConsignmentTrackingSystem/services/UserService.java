package com.vs.ConsignmentTrackingSystem.services;

import org.springframework.stereotype.Service;

import com.vs.ConsignmentTrackingSystem.models.Request.CreateUserRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.GenerateReportRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.LoginRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.ReportAccessRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.LoginResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.LogoutResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.UserResponse;

@Service
public interface UserService {

	public LoginResponse login(LoginRequest logindetails);

	public LogoutResponse logout(String authToken);

	public UserResponse createUser(CreateUserRequest user);

	public UserResponse deleteUser(long id);

	public UserResponse updateUser(long id, CreateUserRequest user);

	public GetResponse getAllUsers(String role);

	public UserResponse assignReportAccess(ReportAccessRequest reportAccess);

	public GetResponse generateReport(GenerateReportRequest reportRequest);

}
