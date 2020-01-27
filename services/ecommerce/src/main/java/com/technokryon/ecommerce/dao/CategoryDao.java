package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.Category;

public interface CategoryDao {

	List<Category> categoryList();

	List<Category> categoryListById(Category category);

}
