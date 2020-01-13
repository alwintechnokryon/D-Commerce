package com.technokryon.ecommerce.admin.dao;

import java.util.List;

import com.technokryon.ecommerce.admin.pojo.Category;

public interface AdminCategoryDao {

	Boolean checkCategoryName(String cCategoryName);

	String addCategory(Category RO_Category);

	List<Category> categoryList();

	List<Category> categoryListById(Category RO_Category);

	Boolean updateCategory(Category RO_Category);

}
