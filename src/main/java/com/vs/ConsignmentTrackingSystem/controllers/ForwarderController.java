package com.vs.ConsignmentTrackingSystem.controllers;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.vs.ConsignmentTrackingSystem.db.entities.UserEntityDAO;
import com.vs.ConsignmentTrackingSystem.models.Request.ForwarderRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.services.ForwarderService;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
@RequestMapping(value =Constants.API_PATH)
@Api(value = Constants.FORWARDER_API_TAG, description = Constants.FORWARDER_API_TAG_DESCRIPTION)
public class ForwarderController
{
	private final static Logger LOGGER = Logger.getLogger(JobController.class.getName());
	
	@Autowired
	private ForwarderService forwarderService;
	
	@Autowired
	private UserEntityDAO userEntityDAO;
	
	@ApiOperation(value = Constants.CREATE_FORWARDER_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value =  Constants.CREATE_FORWARDER)
	public GetResponse create(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.FORWARDER_PARAM) @RequestBody ForwarderRequest forwarderReq)
	{
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if(flag)
			return  forwarderService.createForwarder(forwarderReq);
		else{
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}
	}

	@ApiOperation(value = Constants.LIST_FORWARDER_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.LIST_FORWARDER)
	public GetResponse getAllForwarder(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return forwarderService.getAllForwarder();
		else{
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}				
	}

	@ApiOperation(value = Constants.UPDATE_FORWARDER_DESCRIPTION, response =GetResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.UPDATE_FORWARDER)
	public GetResponse update(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.FORWARDER_ID_PARAM) @PathVariable("id") long id,
			@ApiParam(Constants.FORWARDER_PARAM) @RequestBody ForwarderRequest forwarderRequest) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return forwarderService.updateForwarder(id, forwarderRequest);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}
	}

	@ApiOperation(value = Constants.DELETE_FORWARDER_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.DELETE, produces = "application/json", value = Constants.DELETE_FORWARDER)
	public GetResponse delete(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.FORWARDER_ID_PARAM) @PathVariable("id") long id) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return forwarderService.deleteForwarder(id);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}
	}
}
