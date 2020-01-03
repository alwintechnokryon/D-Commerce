package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.Category;

public interface CategoryService {

	List<Category> categoryList();

	List<Category> categoryListById(Category RO_Category);

}
