package com.technokryon.ecommerce.admin.dao;

import com.technokryon.ecommerce.admin.pojo.Product;

public interface AdminProductDao {

	String addProduct(Product product);

	Boolean checkSkuAvailable(String sku);

}
