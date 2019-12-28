package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.PRODUCTCART;

public interface UserCartService {

	void addToCart(PRODUCTCART RO_PRODUCTCART);

	List<PRODUCTCART> listCart(String uId);

	Integer checkTotalQuantity(String pcTkecmpId);

	Boolean addQuantity(PRODUCTCART RO_PRODUCTCART);

	void saveLater(PRODUCTCART RO_PRODUCTCART);

	void deleteCart(PRODUCTCART RO_PRODUCTCART);

}
