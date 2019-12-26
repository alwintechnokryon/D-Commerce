package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.ORDER;
import com.technokryon.ecommerce.pojo.PRODUCT;

public interface OrderService {

	String requestOrder(ORDER RO_ORDER);

	Boolean checkAvailableProductQuantity(List<PRODUCT> LO_PRODUCT);

	String updateTransactionId(ORDER RO_ORDER);

}
