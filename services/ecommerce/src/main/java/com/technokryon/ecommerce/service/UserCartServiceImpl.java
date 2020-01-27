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
	private UserCartDao userCartDao;

	@Override
	public void addToCart(ProductCart productCart) {

		userCartDao.addToCart(productCart);

	}

	@Override
	public List<ProductCart> listCart(String uId) {

		return userCartDao.listCart(uId);
	}

	@Override
	public Integer checkTotalQuantity(String pcTkecmpId) {

		return userCartDao.checkTotalQuantity(pcTkecmpId);
	}

	@Override
	public Boolean addQuantity(ProductCart productCart) {

		return userCartDao.addQuantity(productCart);
	}

	@Override
	public void saveLater(ProductCart productCart) {

		userCartDao.saveLater(productCart);

	}

	@Override
	public void deleteCart(ProductCart productCart) {

		userCartDao.deleteCart(productCart);

	}

}
