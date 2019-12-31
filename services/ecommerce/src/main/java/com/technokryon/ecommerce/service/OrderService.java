package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.Product;

public interface OrderService {

	String requestOrder(Order RO_Order);

	Boolean checkAvailableProductQuantity(List<Product> LO_PRODUCT);

	String updateTransactionId(Order RO_Order);

}
