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
import com.vs.ConsignmentTrackingSystem.models.Request.CountryRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.services.CountryService;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value =Constants.API_PATH)
@Api(value = Constants.COUNTRY_API_TAG, description = Constants.COUNTRY_API_TAG_DESCRIPTION)
public class CountryController 
{
	private final static Logger LOGGER = Logger.getLogger(JobController.class.getName());
	
  @Autowired
  private CountryService countryService;
  
  @Autowired
  private UserEntityDAO userEntityDAO;
  
	@ApiOperation(value = Constants.CREATE_COUNTRY_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value =  Constants.CREATE_COUNTRY)
	public GetResponse create(
		@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
		@ApiParam(Constants.COUNTRY_PARAM) @RequestBody CountryRequest countryRequest) {
	 boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
	 if(flag)
		return  countryService.createCountry(countryRequest);
	 else{
		LOGGER.info("User not authorized to perform this operation");
		GetResponse getResponse = new GetResponse();
		getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
		return getResponse;
	  }
    }
	
	@ApiOperation(value = Constants.UPDATE_COUNTRY_DESCRIPTION, response =GetResponse.class)
	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.UPDATE_COUNTRY)
	public GetResponse update(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.COUNTRY_ID_PARAM) @PathVariable("id") long id,
			@ApiParam(Constants.COUNTRY_PARAM) @RequestBody CountryRequest countryRequest) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return countryService.updateCountry(id, countryRequest);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}
	}
	
	@ApiOperation(value = Constants.DELETE_COUNTRY_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.DELETE, produces = "application/json", value = Constants.DELETE_COUNTRY)
	public GetResponse delete(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.COUNTRY_ID_PARAM) @PathVariable("id") long id) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return countryService.deleteCountry(id);
		else {
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}
	}
	
	@ApiOperation(value = Constants.LIST_COUNTRY_DESCRIPTION, response = GetResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.LIST_COUNTRY)
	public GetResponse getAllCountry(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return countryService.getAllCountries();
		else{
			LOGGER.info("User not authorized to perform this operation");
			GetResponse getResponse = new GetResponse();
			getResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return getResponse;
		}				
	}	
}
