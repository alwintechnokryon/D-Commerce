package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.UserAddress;

public interface UserAddressService {

	List<UserAddress> listUserAddress(String uId);

	void updateUserAddress(UserAddress userAddress);

	void deleteUserAddress(UserAddress userAddress);

	void addUserAddress(UserAddress userAddress);

}
