package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.UserAddress;

public interface UserAddressDao {

	void addUserAddress(UserAddress userAddress);

	List<UserAddress> listUserAddress(String uId);

	void updateUserAddress(UserAddress userAddress);

	void deleteUserAddress(UserAddress userAddress);

}
