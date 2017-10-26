package com.vs.ConsignmentTrackingSystem.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.CreateUserRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.GenerateReportRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.LoginRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.ReportAccessRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.LoginResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.LogoutResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.UserResponse;
import com.vs.ConsignmentTrackingSystem.services.UserService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
 * @author bharti
 *This is the controller dealing with user apis
 */
@RestController
@RequestMapping(value = Constants.API_PATH)
@Api(value = Constants.USERS_API_TAG, description = Constants.USERS_API_TAG_DESCRIPTION)
public class UserController {

	private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserEntityDAO userEntityDAO;

	@ApiOperation(value = Constants.LOGIN_DESCRIPTION, response = LoginResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = Constants.LOGIN)
	public LoginResponse login(@ApiParam(Constants.LOGIN_PARAM) @RequestBody LoginRequest logindetails) {
		return userService.login(logindetails);
	}

	@ApiOperation(value = Constants.LOGOUT_DESCRIPTION, response = LogoutResponse.class)
	@RequestMapping(method = RequestMethod.POST, value = Constants.LOGOUT)
	public LogoutResponse logout(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken) {
		return userService.logout(authToken);

	}

	@ApiOperation(value = Constants.CREATE_USER_DESCRIPTION, response = UserResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = Constants.CREATE_USER)
	public UserResponse create(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_PARAM) @RequestBody CreateUserRequest user) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return userService.createUser(user);
		else {
			LOGGER.info("User not authorized to perform this operation");
			UserResponse userResponse = new UserResponse();
			userResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return userResponse;
		}
	}

	@ApiOperation(value = Constants.DELETE_USER_DESCRIPTION, response = UserResponse.class)
	@RequestMapping(method = RequestMethod.DELETE, produces = "application/json", value = Constants.DELETE_USER)
	public UserResponse delete(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_ID_PARAM) @PathVariable("id") long id) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return userService.deleteUser(id);
		else {
			LOGGER.info("User not authorized to perform this operation");
			UserResponse userResponse = new UserResponse();
			userResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return userResponse;
		}
	}

	@ApiOperation(value = Constants.UPDATE_USER_DESCRIPTION, response = UserResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.UPDATE_USER)
	public UserResponse update(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_ID_PARAM) @PathVariable("id") long id,
			@ApiParam(Constants.USER_PARAM) @RequestBody CreateUserRequest user) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return userService.updateUser(id, user);
		else {
			LOGGER.info("User not authorized to perform this operation");
			UserResponse userResponse = new UserResponse();
			userResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return userResponse;
		}
	}

	@ApiOperation(value = Constants.LIST_USERS_DESCRIPTION, response = UserResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.LIST_USERS)
	public GetResponse getAllUsers(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_ROLE_PARAM)@RequestParam("role") String role) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return userService.getAllUsers(role);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllUsersResponse = new GetResponse();
			getAllUsersResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllUsersResponse;
		}
	}

	@ApiOperation(value = Constants.PRIVILEDGE_REPORT_DESCRIPTION)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = Constants.PRIVILEDGE_REPORT)
	public UserResponse assignReportAccess(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.PRIVILEDGE_REPORT_PARAM) @RequestBody ReportAccessRequest reportAccess) {

		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return userService.assignReportAccess(reportAccess);
		else {
			LOGGER.info("User not authorized to perform this operation");
			UserResponse userResponse = new UserResponse();
			userResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return userResponse;
		}
	}

	@ApiOperation(value = Constants.GENERATE_REPORT_DESCRIPTION)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = Constants.GENERATE_REPORT)
	public GetResponse assignReportAccess(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.GENERATE_REPORT_PARAM) @RequestBody GenerateReportRequest reportRequest) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag) {
			return userService.generateReport(reportRequest);

		} else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}

	}
}
