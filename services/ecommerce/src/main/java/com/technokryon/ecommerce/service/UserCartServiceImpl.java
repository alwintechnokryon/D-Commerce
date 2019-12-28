package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserCartDao;
import com.technokryon.ecommerce.pojo.PRODUCTCART;

@Service("UserCartService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class UserCartServiceImpl implements UserCartService {

	@Autowired
	private UserCartDao O_UserCartDao;

	@Override
	public void addToCart(PRODUCTCART RO_PRODUCTCART) {

		O_UserCartDao.addToCart(RO_PRODUCTCART);

	}

	@Override
	public List<PRODUCTCART> listCart(String uId) {

		return O_UserCartDao.listCart(uId);
	}

	@Override
	public Integer checkTotalQuantity(String pcTkecmpId) {

		return O_UserCartDao.checkTotalQuantity(pcTkecmpId);
	}

	@Override
	public Boolean addQuantity(PRODUCTCART RO_PRODUCTCART) {

		return O_UserCartDao.addQuantity(RO_PRODUCTCART);
	}

	@Override
	public void saveLater(PRODUCTCART RO_PRODUCTCART) {

		O_UserCartDao.saveLater(RO_PRODUCTCART);

	}

	@Override
	public void deleteCart(PRODUCTCART RO_PRODUCTCART) {

		O_UserCartDao.deleteCart(RO_PRODUCTCART);

	}

}
