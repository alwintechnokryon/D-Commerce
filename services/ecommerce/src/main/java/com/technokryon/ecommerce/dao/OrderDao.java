package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.ORDER;
import com.technokryon.ecommerce.pojo.PRODUCT;

public interface OrderDao {

	// Boolean checkAvailableProductQuantity(String productId, Integer proQuantity);

	String requestOrder(ORDER RO_ORDER);

	Boolean checkAvailableProductQuantity(List<PRODUCT> LO_PRODUCT);

	String updateTransactionId(ORDER RO_ORDER);

}
