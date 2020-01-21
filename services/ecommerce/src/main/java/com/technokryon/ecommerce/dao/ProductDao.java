package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.Product;

public interface ProductDao {

	List<Product> getListByCategory(String categoryId, Integer pageNumber, String uId);

	Product getDetailById(String tkecmpId, String uId);

}
