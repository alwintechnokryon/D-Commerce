package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.ProductCart;


public interface UserCartDao {

	void addToCart(ProductCart productCart);

	List<ProductCart> listCart(String uId);

	Integer checkTotalQuantity(String pcTkecmpId);

	Boolean addQuantity(ProductCart productCart);

	void saveLater(ProductCart productCart);

	void deleteCart(ProductCart productCart);

}
