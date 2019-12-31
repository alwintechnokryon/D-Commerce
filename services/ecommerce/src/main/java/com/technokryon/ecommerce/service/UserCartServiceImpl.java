package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserCartDao;
import com.technokryon.ecommerce.pojo.ProductCart;

@Service("UserCartService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class UserCartServiceImpl implements UserCartService {

	@Autowired
	private UserCartDao O_UserCartDao;

	@Override
	public void addToCart(ProductCart RO_ProductCart) {

		O_UserCartDao.addToCart(RO_ProductCart);

	}

	@Override
	public List<ProductCart> listCart(String uId) {

		return O_UserCartDao.listCart(uId);
	}

	@Override
	public Integer checkTotalQuantity(String pcTkecmpId) {

		return O_UserCartDao.checkTotalQuantity(pcTkecmpId);
	}

	@Override
	public Boolean addQuantity(ProductCart RO_ProductCart) {

		return O_UserCartDao.addQuantity(RO_ProductCart);
	}

	@Override
	public void saveLater(ProductCart RO_ProductCart) {

		O_UserCartDao.saveLater(RO_ProductCart);

	}

	@Override
	public void deleteCart(ProductCart RO_ProductCart) {

		O_UserCartDao.deleteCart(RO_ProductCart);

	}

}
