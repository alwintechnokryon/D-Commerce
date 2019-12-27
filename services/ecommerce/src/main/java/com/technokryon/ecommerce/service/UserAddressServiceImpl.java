package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserAddressDao;
import com.technokryon.ecommerce.pojo.USERADDRESS;

@Service("UserAddressService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserAddressServiceImpl implements UserAddressService {

	@Autowired
	private UserAddressDao O_UserAddressDao;

	@Override
	public void addUserAddress(USERADDRESS RO_USERADDRESS) {
		// TODO Auto-generated method stub
		O_UserAddressDao.addUserAddress(RO_USERADDRESS);
	}

	@Override
	public List<USERADDRESS> listUserAddress(String uId) {
		// TODO Auto-generated method stub
		return O_UserAddressDao.listUserAddress(uId);
	}

	@Override
	public void updateUserAddress(USERADDRESS RO_USERADDRESS) {
		// TODO Auto-generated method stub
		O_UserAddressDao.updateUserAddress(RO_USERADDRESS);
	}

	@Override
	public void deleteUserAddress(USERADDRESS RO_USERADDRESS) {
		// TODO Auto-generated method stub
		O_UserAddressDao.deleteUserAddress(RO_USERADDRESS);
	}

}
