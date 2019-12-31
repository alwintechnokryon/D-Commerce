package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.ProductCart;


public interface UserCartDao {

	void addToCart(ProductCart RO_ProductCart);

	List<ProductCart> listCart(String uId);

	Integer checkTotalQuantity(String pcTkecmpId);

	Boolean addQuantity(ProductCart RO_ProductCart);

	void saveLater(ProductCart RO_ProductCart);

	void deleteCart(ProductCart RO_ProductCart);

}
