package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.WishList;

public interface UserWishListService {

	void addWishList(WishList RO_WishList);

	List<WishList> listWishList(String uId);

	void deleteWishlist(WishList RO_WishList);

}
