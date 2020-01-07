package com.technokryon.ecommerce.admin.dao;

import com.technokryon.ecommerce.admin.pojo.Category;

public interface AdminCategoryDao {

	Boolean checkCategoryName(String cCategoryName);

	String addCategory(Category RO_Category);

}
