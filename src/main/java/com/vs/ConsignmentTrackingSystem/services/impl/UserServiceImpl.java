package com.vs.ConsignmentTrackingSystem.services.impl;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserJobTaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.CreateUserRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.GenerateReportRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.LoginRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.ReportAccessRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.JobResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.LoginResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.LoginResponseData;
import com.vs.ConsignmentTrackingSystem.models.Response.LogoutResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.UserResponse;
import com.vs.ConsignmentTrackingSystem.services.UserService;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;

import java.util.logging.Logger;

@Component
public class UserServiceImpl implements UserService {

	private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserEntityDAO userEntityDAO;

	@Autowired
	private UserJobTaskEntityDAO userJobTaskEntityDAO;

	@Autowired
	private TaskEntityDAO taskEntityDAO;
	
	@Autowired
	private ResourceLoader resourceLoader;

	public LoginResponse login(LoginRequest logindetails) {

		List<UserEntity> adminDetails = userEntityDAO.getAdmin();

		if (adminDetails.size() == 0) {

			UserEntity userEntity = new UserEntity();
			userEntity.setRole("Admin");
			userEntity.setPassword("admin");
			userEntity.setUsername("admin"); 
			userEntity.setUserType("Admin");
			userEntity.setIsReportAccess(true);
			userEntityDAO.save(userEntity);	

			addTaskDetails();
		}

		UserEntity userEntity = userEntityDAO.findIfUserExists(logindetails);
		LoginResponseData loginResponseData = new LoginResponseData();
		LoginResponse loginResponse = new LoginResponse();
		if (userEntity == null)
			loginResponse.setMessage(Constants.FAILURE_RESONSE);
		else {
			long loginTime = System.currentTimeMillis();
			userEntity.setLoginTime(loginTime);
			userEntityDAO.update(userEntity);
			Authorization instance = Authorization.getInstance();
			String salt = instance.getSaltString();
			String token = logindetails.getUsername() + ":" + loginTime + salt;
			String encryptedToken;
			try {
				encryptedToken = instance.encrypt(token);
				loginResponseData.setId(userEntity.getId());
				loginResponseData.setUsername(userEntity.getUsername());
				loginResponseData.setPassword(userEntity.getPassword());
				loginResponseData.setRole(userEntity.getRole());
				loginResponseData.setIsReportAccess(userEntity.getReportAccess());
				loginResponseData.setCompanyName(userEntity.getCompanyName());
				loginResponseData.setUserType(userEntity.getUserType());
				loginResponseData.setReportFields(userEntity.getReportFields());
				loginResponseData.setAuthToken(encryptedToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			userJobTaskEntityDAO.checkPendingTask();
			
			loginResponse.setMessage(Constants.SUCCESS_RESPONSE);
			loginResponse.setData(loginResponseData);
		}
		return loginResponse;
	}

	public LogoutResponse logout(String authToken) {
		LogoutResponse logoutResponse = new LogoutResponse();
		UserEntity userEntity = Authorization.getInstance().getUserByAuthToken(userEntityDAO, authToken);
		if (userEntity != null && System.currentTimeMillis() - userEntity.getLoginTime() < (8 * 60 * 60 * 1000)) {
			userEntity.setLoginTime(0);
			userEntityDAO.update(userEntity);
			logoutResponse.setMessage(Constants.LOGOUT_SUCCESS_RESPONSE );

		} else {
			logoutResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
		}
		return logoutResponse;
	}

	public UserResponse createUser(CreateUserRequest user) {

		UserResponse userResponse = new UserResponse();
		if (userEntityDAO.findByDetails(user.getUsername(),user.getPassword()) != null) {
			userResponse.setMessage("THIS USERNAME & PASSWORD IS ALREADY USED");
		}
		else if(user.getRole().equals("User") == true && userEntityDAO.findByType(user.getUserType()) != null) {
			userResponse.setMessage("THIS USERTYPE IS ALREADY CREATED");
		}
		else {
			try {
				UserEntity userEntity = new UserEntity();
				userEntity.setUsername(user.getUsername());
				userEntity.setPassword(user.getPassword());
				userEntity.setRole(user.getRole());
				userEntity.setCompanyName(user.getCompanyName());
				userEntity.setUserType(user.getUserType());
				userEntity.setReportFields(user.getReportFields());
				
				if(user.getRole().equals("User")) {
					userEntity.setReportAccess(false);
				}
				else
					userEntity.setReportAccess(true);
				
				userEntityDAO.save(userEntity);

				userResponse.setMessage(Constants.SUCCESS_RESPONSE);
			} catch (Exception ex) {
				System.out.println("in exception");
			}
		}
		return userResponse;
	}

	public UserResponse deleteUser(long id) {
		boolean isDeleted = userEntityDAO.delete(id);
		UserResponse userResponse = new UserResponse();
		if (isDeleted)
			userResponse.setMessage(Constants.SUCCESS_RESPONSE);
		else
			userResponse.setMessage(Constants.FAILURE_RESONSE);
		return userResponse;
	}

	public UserResponse updateUser(long id, CreateUserRequest user) {
		UserEntity userEntity = userEntityDAO.findById(id);

		UserResponse userResponse = new UserResponse();
		if (userEntity == null)
			userResponse.setMessage(Constants.FAILURE_RESONSE);
		else {
			if (user.getUsername() != null)
				userEntity.setUsername(user.getUsername());
			if (user.getPassword() != null)
				userEntity.setPassword(user.getPassword());
			if (user.getCompanyName() != null)
				userEntity.setCompanyName(user.getCompanyName());
			if (user.getRole() != null)
				userEntity.setRole(user.getRole());
			if (user.getUserType() != null)
				userEntity.setUserType(user.getUserType());
			if (user.getReportFields() != null)
				userEntity.setReportFields(user.getReportFields());
			
			
			if(user.getCompanyName() != null) {
				userJobTaskEntityDAO.updateExporter(id,user.getCompanyName());
			}
			
			userEntityDAO.update(userEntity);
			userResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		return userResponse;
	}

	public GetResponse getAllUsers(String role) {

		List<UserEntity> list;
		if(role.equals("Client"))
		{
			list = userEntityDAO.getUserByRole(role);
		}
		else 
		{
			list = userEntityDAO.getAllData();
		}
		GetResponse getAllUsersResponse = new GetResponse();
		getAllUsersResponse.setData(list);
		getAllUsersResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllUsersResponse;
	}

	public UserResponse assignReportAccess(ReportAccessRequest reportAccess) {
		if (reportAccess.getType().equals("grant")) {
			userEntityDAO.grantAccess(reportAccess.getIdList());
		} else if (reportAccess.getType().equals("deny")) {
			userEntityDAO.denyAccess();
		} else {
			userEntityDAO.customizeReport(reportAccess.getIdList(), reportAccess.getCustomvalue());
		}
		UserResponse userResponse = new UserResponse();
		userResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return userResponse;

	}

	public GetResponse generateReport(GenerateReportRequest reportRequest) {
		List list = userJobTaskEntityDAO.decision(reportRequest);
		GetResponse getResponse = new GetResponse();
		getResponse.setData(list);
		getResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getResponse;

	}

	private void addTaskDetails() {
		
		List<TaskEntity> taskEntities = null;
		try {
			taskEntities = new ObjectMapper().readValue(resourceLoader.getResource(Constants.PATH_TASK_LIST_JSON).getFile(), TypeFactory.defaultInstance().constructCollectionType(List.class, TaskEntity.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < taskEntities.size() ;  i++) {
			TaskEntity taskEntity = new TaskEntity();
			taskEntity.setTaskId(taskEntities.get(i).getTaskId());
			taskEntity.setDuration(taskEntities.get(i).getDuration());
			taskEntity.setTaskTitle(taskEntities.get(i).getTaskTitle());
			taskEntity.setUserType(taskEntities.get(i).getUserType());
			taskEntity.setTaskPendingFrom(taskEntities.get(i).getTaskPendingFrom());
			taskEntity.setTaskType(taskEntities.get(i).getTaskType());
			taskEntityDAO.save(taskEntity);
		}
	}
}
