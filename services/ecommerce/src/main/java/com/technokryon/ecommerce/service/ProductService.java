package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.PRODUCT;

public interface ProductService {

	List<PRODUCT> getListByCategory(String categoryId, Integer PageNumber);

	PRODUCT getDetailById(String id);

}
