package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.USERADDRESS;

public interface UserAddressDao {

	void addUserAddress(USERADDRESS RO_USERADDRESS);

	List<USERADDRESS> listUserAddress(String uId);

	void updateUserAddress(USERADDRESS RO_USERADDRESS);

	void deleteUserAddress(USERADDRESS RO_USERADDRESS);

}
