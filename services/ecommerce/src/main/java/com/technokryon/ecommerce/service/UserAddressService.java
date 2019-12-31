package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.UserAddress;

public interface UserAddressService {

	List<UserAddress> listUserAddress(String uId);

	void updateUserAddress(UserAddress RO_UserAddress);

	void deleteUserAddress(UserAddress RO_UserAddress);

	void addUserAddress(UserAddress RO_UserAddress);

}
