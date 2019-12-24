package com.technokryon.ecommerce.dao;

import com.technokryon.ecommerce.pojo.ORDER;

public interface OrderDao {

	Boolean checkAvailableProductQuantity(String productId, Integer proQuantity);

	String requestOrder(ORDER RO_ORDER);

}
