package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserAddressDao;
import com.technokryon.ecommerce.pojo.UserAddress;

@Service("UserAddressService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserAddressServiceImpl implements UserAddressService {

	@Autowired
	private UserAddressDao userAddressDao;

	@Override
	public void addUserAddress(UserAddress userAddress) {

		userAddressDao.addUserAddress(userAddress);
	}

	@Override
	public List<UserAddress> listUserAddress(String uId) {

		return userAddressDao.listUserAddress(uId);
	}

	@Override
	public void updateUserAddress(UserAddress userAddress) {

		userAddressDao.updateUserAddress(userAddress);
	}

	@Override
	public void deleteUserAddress(UserAddress userAddress) {

		userAddressDao.deleteUserAddress(userAddress);
	}

}
