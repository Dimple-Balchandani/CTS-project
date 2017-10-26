package com.vs.ConsignmentTrackingSystem.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vs.ConsignmentTrackingSystem.db.entities.CategoryEntity;
import com.vs.ConsignmentTrackingSystem.db.entities.CategoryEntityDAO;
import com.vs.ConsignmentTrackingSystem.db.entities.JobEntity;
import com.vs.ConsignmentTrackingSystem.models.Request.CreateCategoryRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.CategoryResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.UserResponse;
import com.vs.ConsignmentTrackingSystem.services.CategoryService;
import com.vs.ConsignmentTrackingSystem.utilities.Constants;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryEntityDAO categoryEntityDAO;

	public CategoryResponse createCategory(CreateCategoryRequest category) {
		CategoryResponse categoryResponse = new CategoryResponse();
		
		try{
			CategoryEntity categoryEntity = new CategoryEntity();
			categoryEntity.setCategoryName(category.getCategoryName());
			categoryEntity.setTaskIdList(category.getTaskIdList());
			categoryEntityDAO.save(categoryEntity);
		
			categoryResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		catch(Exception e){
			categoryResponse.setMessage(Constants.FAILURE_RESONSE);
		}
		return categoryResponse;
	}


	public GetResponse getAllCategories() {
		List<CategoryEntity> categories = categoryEntityDAO.getCategories();
		GetResponse getAllCategoriesResponse = new GetResponse();
		getAllCategoriesResponse.setData(categories);
		getAllCategoriesResponse.setMessage(Constants.SUCCESS_RESPONSE);
		return getAllCategoriesResponse;
	}

	public CategoryResponse updateCategory(long id, CreateCategoryRequest category) {
		CategoryEntity categoryEntity = categoryEntityDAO.findById(id);
		CategoryResponse categoryResponse = new CategoryResponse();
		if (categoryEntity == null)
			categoryResponse.setMessage(Constants.FAILURE_RESONSE);
		else {
			if (category.getCategoryName() != null)
				categoryEntity.setCategoryName(category.getCategoryName());
			if(category.getTaskIdList() != null)
				categoryEntity.setTaskIdList(category.getTaskIdList());

			categoryEntityDAO.update(categoryEntity);
			categoryResponse.setMessage(Constants.SUCCESS_RESPONSE);
		}
		return categoryResponse;
	}

}
