package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.Category;


public interface CategoryDao {

	String addCategory(Category RO_Category);

	List<Category> categoryList();

	List<Category> categoryListById(Category RO_Category);

}
