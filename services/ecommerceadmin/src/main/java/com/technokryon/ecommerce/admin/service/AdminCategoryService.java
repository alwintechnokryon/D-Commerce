package com.technokryon.ecommerce.admin.service;

import java.util.List;

import com.technokryon.ecommerce.admin.pojo.Category;

public interface AdminCategoryService {

	Boolean checkCategoryName(String cCategoryName);

	String addCategory(Category category);

	List<Category> categoryList();

	List<Category> categoryListById(Category category);

	Boolean updateCategory(Category category);

}
