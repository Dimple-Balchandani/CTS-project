package com.vs.ConsignmentTrackingSystem.services;

import org.springframework.stereotype.Service;

import com.vs.ConsignmentTrackingSystem.models.Request.CreateCategoryRequest;
import com.vs.ConsignmentTrackingSystem.models.Response.CategoryResponse;
import com.vs.ConsignmentTrackingSystem.models.Response.GetResponse;

@Service
public interface CategoryService {
	
	public CategoryResponse createCategory(CreateCategoryRequest category);

	public GetResponse getAllCategories();

	public CategoryResponse updateCategory(long id, CreateCategoryRequest category);
}
