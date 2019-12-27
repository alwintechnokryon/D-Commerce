package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.USERADDRESS;

public interface UserAddressService {

	List<USERADDRESS> listUserAddress(String uId);

	void updateUserAddress(USERADDRESS RO_USERADDRESS);

	void deleteUserAddress(USERADDRESS RO_USERADDRESS);

	void addUserAddress(USERADDRESS rO_USERADDRESS);

}
