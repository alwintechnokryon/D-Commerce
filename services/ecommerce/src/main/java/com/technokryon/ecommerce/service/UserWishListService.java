package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.WishList;

public interface UserWishListService {

	void addWishList(WishList wishList);

	List<WishList> listWishList(String uId);

	void deleteWishlist(WishList wishList);

}
