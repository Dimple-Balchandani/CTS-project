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
import com.vs.ConsignmentTrackingSystem.models.Request.CreateCategoryRequest;
import com.vs.ConsignmentTrackingSystem.models.Request.CreateUserRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.CategoryResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.UserResponse;
import com.vs.ConsignmentTrackingSystem.services.CategoryService;
import com.vs.ConsignmentTrackingSystem.utilities.Authorization;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = Constants.API_PATH)
@Api(value = Constants.CATEGORY_API_TAG, description = Constants.CATEGORY_API_TAG_DESCRIPTION)
public class CategoryController {
	
	private final static Logger LOGGER = Logger.getLogger(JobController.class.getName());
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserEntityDAO userEntityDAO;
	
	@ApiOperation(value = Constants.CREATE_CATEGORY_DESCRIPTION, response = CategoryResponse.class)
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json", value = Constants.CREATE_CATEGORY)
	public CategoryResponse create(
			@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
			@ApiParam(Constants.JOB_PARAM) @RequestBody CreateCategoryRequest category) {
		boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
		if (flag)
			return categoryService.createCategory(category);
		else {
			LOGGER.info("User not authorized to perform this operation");
			CategoryResponse categoryResponse = new CategoryResponse();
			categoryResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
			return categoryResponse;
		}
	}
		
		@ApiOperation(value = Constants.LIST_CATEGORY_DESCRIPTION, response = GetResponse.class)
		@RequestMapping(method = RequestMethod.GET, produces = "application/json", value = Constants.LIST_CATEGORY)
		public GetResponse getAllCategories(
				@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken) {
			
			boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
			if (flag)
				return categoryService.getAllCategories();
			else {
				LOGGER.info("User not authorized to perform this operation");
				GetResponse getAllCategoriesResponse = new GetResponse();
				getAllCategoriesResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
				return getAllCategoriesResponse;
			}
		}
		
		@ApiOperation(value = Constants.UPDATE_CATEGORY_DESCRIPTION, response = UserResponse.class)
		@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json", value = Constants.UPDATE_CATEGORY)
		public CategoryResponse update(
				@ApiParam(Constants.AUTH_PARAM_DESCRIPTION) @RequestHeader(value = Constants.AUTH_PARAM, required = true) String authToken,
				@ApiParam(Constants.USER_ID_PARAM) @PathVariable("id") long id,
				@ApiParam(Constants.USER_PARAM) @RequestBody CreateCategoryRequest category) {
			boolean flag = Authorization.getInstance().verifyToken(userEntityDAO, authToken);
			if (flag)
				return categoryService.updateCategory(id, category);
			else {
				LOGGER.info("User not authorized to perform this operation");
				CategoryResponse categoryResponse = new CategoryResponse();
				categoryResponse.setMessage(Constants.LOGOUT_SESSION_EXPIRED_RESPONSE);
				return categoryResponse;
			}
		}
}

	
