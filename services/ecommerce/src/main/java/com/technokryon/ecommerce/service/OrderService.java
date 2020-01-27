package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.OrderItem;
import com.technokryon.ecommerce.pojo.Product;

public interface OrderService {

	String requestOrder(Order order);

	Boolean checkAvailableProductQuantity(List<Product> product);

	String updateTransactionId(Order order);

	List<OrderItem> orderList(String uId);

	OrderItem orderItemById(Integer oiAgId);

	void orderCancel(Integer oshAgId, String uId);

}
