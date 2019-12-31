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
	private UserAddressDao O_UserAddressDao;

	@Override
	public void addUserAddress(UserAddress RO_UserAddress) {
		
		O_UserAddressDao.addUserAddress(RO_UserAddress);
	}

	@Override
	public List<UserAddress> listUserAddress(String uId) {
		
		return O_UserAddressDao.listUserAddress(uId);
	}

	@Override
	public void updateUserAddress(UserAddress RO_UserAddress) {
		
		O_UserAddressDao.updateUserAddress(RO_UserAddress);
	}

	@Override
	public void deleteUserAddress(UserAddress RO_UserAddress) {
		
		O_UserAddressDao.deleteUserAddress(RO_UserAddress);
	}

}
