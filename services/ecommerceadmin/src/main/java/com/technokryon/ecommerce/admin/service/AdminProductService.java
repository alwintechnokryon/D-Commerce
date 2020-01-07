package com.technokryon.ecommerce.admin.service;

import com.technokryon.ecommerce.admin.pojo.Product;

public interface AdminProductService {

	Boolean checkSkuAvailable(String sku);

	String addProduct(Product O_Product);

}
