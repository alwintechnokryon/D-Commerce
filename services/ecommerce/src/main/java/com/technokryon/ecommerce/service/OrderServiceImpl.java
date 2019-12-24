package com.technokryon.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.OrderDao;
import com.technokryon.ecommerce.pojo.ORDER;
import com.technokryon.ecommerce.pojo.PRODUCT;

@Service("OrderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao O_OrderDao;

	@Override
	public Boolean checkAvailableProductQuantity(String productId, Integer proQuantity) {
		// TODO Auto-generated method stub
		return O_OrderDao.checkAvailableProductQuantity(productId, proQuantity);
	}

	@Override
	public String requestOrder(ORDER RO_ORDER) {
		// TODO Auto-generated method stub
		return O_OrderDao.requestOrder(RO_ORDER);
	}

}
