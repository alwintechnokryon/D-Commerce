package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.UserAddress;


public interface UserAddressDao {

	void addUserAddress(UserAddress RO_UserAddress);

	List<UserAddress> listUserAddress(String uId);

	void updateUserAddress(UserAddress RO_UserAddress);

	void deleteUserAddress(UserAddress RO_UserAddress);

}
