package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.Product;


public interface ProductService {

	List<Product> getListByCategory(String categoryId, Integer PageNumber);

	Product getDetailById(String id);

}
