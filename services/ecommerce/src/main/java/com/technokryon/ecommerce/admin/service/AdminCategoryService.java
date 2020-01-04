package com.technokryon.ecommerce.admin.service;

import com.technokryon.ecommerce.pojo.Category;

public interface AdminCategoryService {

	Boolean checkCategoryName(String cCategoryName);

	String addCategory(Category RO_Category);

}
