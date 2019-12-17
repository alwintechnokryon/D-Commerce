package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.pojo.CATEGORY;

public interface CategoryService {

	String addCategory(CATEGORY rO_CATEGORY);

	List<CATEGORY> categoryList();

	List<CATEGORY> categoryListById(CATEGORY rO_CATEGORY);

}
