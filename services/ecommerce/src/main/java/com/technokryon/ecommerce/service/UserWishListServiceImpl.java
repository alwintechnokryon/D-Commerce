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
	private UserWishListDao userWishListDao;

	@Override
	public void addWishList(WishList wishList) {

		userWishListDao.addWishList(wishList);
	}

	@Override
	public List<WishList> listWishList(String uId) {

		return userWishListDao.listWishList(uId);

	}

	@Override
	public void deleteWishlist(WishList wishList) {

		userWishListDao.deleteWishlist(wishList);

	}

}
