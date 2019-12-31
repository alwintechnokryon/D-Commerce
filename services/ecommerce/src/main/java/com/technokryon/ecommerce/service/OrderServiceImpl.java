package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.OrderDao;
import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.Product;


@Service("OrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao O_OrderDao;

	@Override
	public String requestOrder(Order RO_Order) {

		return O_OrderDao.requestOrder(RO_Order);
	}

	@Override
	public Boolean checkAvailableProductQuantity(List<Product> LO_PRODUCT) {

		return O_OrderDao.checkAvailableProductQuantity(LO_PRODUCT);
	}

	@Override
	public String updateTransactionId(Order RO_Order) {

		return O_OrderDao.updateTransactionId(RO_Order);
	}

}
