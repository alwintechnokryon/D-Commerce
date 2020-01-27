package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.OrderDao;
import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.OrderItem;
import com.technokryon.ecommerce.pojo.Product;

@Service("OrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderDao;

	@Override
	public String requestOrder(Order order) {

		return orderDao.requestOrder(order);
	}

	@Override
	public Boolean checkAvailableProductQuantity(List<Product> product) {

		return orderDao.checkAvailableProductQuantity(product);
	}

	@Override
	public String updateTransactionId(Order order) {

		return orderDao.updateTransactionId(order);
	}

	@Override
	public List<OrderItem> orderList(String uId) {
		// TODO Auto-generated method stub
		return orderDao.orderList(uId);
	}

	@Override
	public OrderItem orderItemById(Integer oiAgId) {
		// TODO Auto-generated method stub
		return orderDao.orderItemById(oiAgId);
	}

	@Override
	public void orderCancel(Integer oshAgId, String uId) {
		// TODO Auto-generated method stub
		orderDao.orderCancel(oshAgId, uId);
	}

}
