package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.WishList;

public interface UserWishListDao {

	void addWishList(WishList wishList);

	List<WishList> listWishList(String uId);

	void deleteWishlist(WishList wishList);

}
