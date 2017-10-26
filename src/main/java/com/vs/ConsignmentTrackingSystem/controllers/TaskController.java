package com.vs.ConsignmentTrackingSystem.controllers;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vs.ConsignmentTrackingSystem.db.entities.TaskEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.AddRemarkRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.CloseTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskDetailsRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.EditTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.StartTaskRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.TaskArrayRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.TaskResponse;
import com.vs.ConsignmentTrackingSystem.services.TaskDetailsService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
 * 
 * @author bharti
 * This is the controller dealing with task apis
 *
 */
@RestController
@RequestMapping(value = Constants.API_PATH)
@Api(value = Constants.TASKS_API_TAG, description = Constants.TASKS_API_TAG_DESCRIPTION)
public class TaskController {

	private final static Logger LOGGER = Logger.getLogger(TaskController.class.getName());

	@Autowired
	private TaskDetailsService taskDetailsService;

	@Autowired
	private UserEntityDAO userEntityDAO;
	@Autowired
	private TaskEntityDAO taskEntityDAO;

	@ApiOperation(value = Constants.LIST_TASKS_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.LIST_TASKS)
	public GetResponse getAllTasks(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.getTasks();
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}

	@ApiOperation(value = Constants.START_TASK_DESCRIPTION, response = TaskResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.START_TASK)
	public TaskResponse startTask(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.START_TASK_DETAILS) @RequestBody StartTaskRequest startTaskRequest) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.startTask(startTaskRequest);
		else {
			LOGGER.info("User not authorized to perform this operation");
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return taskResponse;
		}
	}

	@ApiOperation(value = Constants.CLOSE_TASK_DESCRIPTION, response = TaskResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.CLOSE_TASK)
	public TaskResponse closeTask(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.CLOSE_TASK_DETAILS) @RequestBody CloseTaskRequest closeTaskRequest) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.closeTask(closeTaskRequest);
		else {
			LOGGER.info("User not authorized to perform this operation");
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return taskResponse;
		}
	}

	@ApiOperation(value = Constants.TASKS_BY_USERTYPE_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value =Constants.TASKS_BY_USERTYPE)
	public GetResponse getTaskByUser(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_TYPE_PARAM) @PathVariable("userType") String userType,
			@ApiParam(Constants.USER_ID_PARAM) @PathVariable("userId") long userId) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.getTaskByUser(userType, userId);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}

	@ApiOperation(value = Constants.COMPLETED_JOB_DESCRIPTION, response = TaskResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.COMPLETED_JOB)
	public TaskResponse completeTask(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_NUMBER_PARAM) @PathVariable("jobNo") int jobNo){
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.completeTask(jobNo);
		else{
			LOGGER.info("User not authorized to perform this operation");
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return taskResponse;
		}
	}

	@ApiOperation(value = Constants.USER_TASK_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces="application/json", value = Constants.TASK_STATUS)
	public GetResponse getJobStatusUserType(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_PARAM) @PathVariable("userType")String userType)
	{
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.getAllTaskByUserType(userType);
		else{
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}
	}

	@ApiOperation(value = Constants.ADD_REMARK_DESCRIPTION, response = TaskResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = Constants.ADD_REMARK)
	public TaskResponse AddRemarkToTask(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.ADD_REMARK_DETAILS) @RequestBody AddRemarkRequest addRemarkRequest){

		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.addRemark(addRemarkRequest);
		else{
			LOGGER.info("User not authorized to perform this operation");
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return taskResponse;
		}
	}

	@ApiOperation(value = Constants.EDIT_TASK_DESCRIPTION, response = TaskResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.EDIT_TASK)
	public TaskResponse EditTask(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.EDIT_TASK_DETAILS_DESCRIPTION) @RequestBody EditTaskRequest editTaskRequest){

		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return taskDetailsService.editTask(editTaskRequest);
		else{
			LOGGER.info("User not authorized to perform this operation");
			TaskResponse taskResponse = new TaskResponse();
			taskResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return taskResponse;
		}

	}

	@ApiOperation(value = Constants.EDIT_TASK_DETAIL_DESCRIPTION, response = TaskResponse.class)
	@RequestMapping(method = RequestMethod.PUT, produces = "application/json", value = Constants.EDIT_TASK_DETAILS)
	public TaskResponse EditTaskDetails(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.EDIT_TASK_DETAILS_DESCRIPTION) @RequestBody EditTaskDetailsRequest editTaskDetailsRequest){
		boolean flag=Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if(flag)
			return taskDetailsService.editTaskDetails(editTaskDetailsRequest);
		TaskResponse taskResponse=new TaskResponse();
		return taskResponse;
	}				
}


