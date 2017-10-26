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
import com.vs.ConsignmentTrackingSystem.models.Request.CreateJobRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.JobScrollNumberRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.JobSearchRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.JobResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.TaskResponse;
import com.vs.ConsignmentTrackingSystem.services.JobService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 
 * @author bharti
 * This is the controller dealing with job apis
 *
 */
@RestController
@RequestMapping(value = Constants.API_PATH)
@Api(value = Constants.JOBS_API_TAG, description = Constants.JOBS_API_TAG_DESCRIPTION)
public class JobController {

	private final static Logger LOGGER = Logger.getLogger(JobController.class.getName());
	
	@Autowired
	private JobService jobService;

	@Autowired
	private UserEntityDAO userEntityDAO;

	@ApiOperation(value = Constants.CREATE_JOB_DESCRIPTION, response = JobResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = Constants.CREATE_JOB)
	public JobResponse create(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_PARAM) @RequestBody CreateJobRequest job) {

		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.createJob(job);
		else {
			LOGGER.info("User not authorized to perform this operation");
			JobResponse jobResponse = new JobResponse();
			jobResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return jobResponse;
		}
	}
	
  @ApiOperation(value = Constants.UPDATE_JOB_SCROLL_NUMBER_DESCRIPTION, response = JobResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.UPDATE_JOB_SCROLL_NUMBER)
	public JobResponse setScrollNumber(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_PARAM) @RequestBody JobScrollNumberRequest job)
	{
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
		{
			return jobService.updateJobScrollNumber(job);
		}
	    else {
			LOGGER.info("User not authorized to perform this operation");
			JobResponse jobResponse = new JobResponse();
			jobResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return jobResponse;
		}
  }

	@ApiOperation(value = Constants.DELETE_JOB_DESCRIPTION, response = JobResponse.class)
	@RequestMapping(method = RequestMethod.DELETE, produces = "application/json", value = Constants.DELETE_JOB)
	public JobResponse delete(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_ID_PARAM) @PathVariable("id") long id) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.deleteJob(id);
		else {
			LOGGER.info("User not authorized to perform this operation");
			JobResponse jobResponse = new JobResponse();
			jobResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return jobResponse;
		}
	}

	@ApiOperation(value = Constants.UPDATE_JOB_DESCRIPTION, response = JobResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.UPDATE_JOB)
	public JobResponse update(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_NUMBER_PARAM) @PathVariable("jobNo") int jobNo,
			@ApiParam(Constants.JOB_PARAM) @RequestBody CreateJobRequest job) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
		{
			return jobService.updateJob(jobNo, job);
		}
		else {
			LOGGER.info("User not authorized to perform this operation");
			JobResponse jobResponse = new JobResponse();
			jobResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return jobResponse;
		}
	}

	@ApiOperation(value = Constants.TASKS_FOR_JOB_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.TASKS_FOR_JOB)
	public GetResponse getTasksForJobs(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_NUMBER_PARAM) @PathVariable("jobNo") int jobNo) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.getTaskDetailsForJob(jobNo);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}
	
	@ApiOperation(value = Constants.JOB_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.JOB_Info)
	public GetResponse getJobById(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_NUMBER_PARAM) @PathVariable("jobNo") int jobNo) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.getJobDetails(jobNo);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}

	@ApiOperation(value = Constants.LIST_JOBS_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.LIST_JOBS)
	public GetResponse getAllJobs(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_STATUS_PARAM) @RequestParam("status") String status) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.getJobs(status);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}
	
	@ApiOperation(value = Constants.LIST_JOBS_BY_ATTRIBUTE_DESCRIPTION,response = GetResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes="application/json", value = Constants.LIST_JOBS_BY_ATTRIBUTE)
	public GetResponse getJobsByAttributes(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_ATTRIBUTE_PARAM) @RequestBody JobSearchRequest searchRequest) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.getJobsByAttributes(searchRequest);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}
	
	//View Delay Job Of User
	@ApiOperation(value = Constants.DELAY_JOBS_OF_USER_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.DELAY_JOB_OF_USER)
	public GetResponse getDelayJobOfUser(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.USER_TYPE_PARAM) @PathVariable("userType") String userType,
			@ApiParam(Constants.USER_ID_PARAM) @PathVariable("userId") long userId) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return jobService.getDelayJobsOfUser(userType, userId);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getAllJobsResponse = new GetResponse();
			getAllJobsResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getAllJobsResponse;
		}
	}
	
	@ApiOperation(value=Constants.JOB_BY_TASK_ID_DESCRIPTION, response=JobResponse.class)
    @RequestMapping(method = RequestMethod.GET, produces="application/json", value = Constants.JOB_BY_TASK_ID)
    public GetResponse getJobByTaskId(
		    @ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.TASK_ID_PARAM) @PathVariable("taskId")long taskId){
        boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
        if (flag)
	        return jobService.getJobByTaskId(taskId); 
         else {
	         LOGGER.info("User not authorized to perform this operation");
	         GetResponse getResponse = new GetResponse();
	         getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
	         return getResponse;
      }
   }
}
