package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.PRODUCT;

public interface ProductDao {

	List<PRODUCT> getListByCategory(String categoryId, Integer pageNumber);

	PRODUCT getDetailById(String id);


}
