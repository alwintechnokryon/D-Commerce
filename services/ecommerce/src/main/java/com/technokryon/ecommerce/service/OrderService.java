package com.technokryon.ecommerce.service;

import com.technokryon.ecommerce.pojo.ORDER;

public interface OrderService {

	Boolean checkAvailableProductQuantity(String productId, Integer proQuantity);

	String requestOrder(ORDER RO_ORDER);

}
