package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserWishListDao;
import com.technokryon.ecommerce.pojo.WishList;

@Service("UserWishListService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class UserWishListServiceImpl implements UserWishListService {

	@Autowired
	private UserWishListDao O_UserWishListDao;

	@Override
	public void addWishList(WishList RO_WishList) {

		O_UserWishListDao.addWishList(RO_WishList);
	}

	@Override
	public List<WishList> listWishList(String uId) {

		return O_UserWishListDao.listWishList(uId);

	}

	@Override
	public void deleteWishlist(WishList RO_WishList) {

		O_UserWishListDao.deleteWishlist(RO_WishList);

	}

}
