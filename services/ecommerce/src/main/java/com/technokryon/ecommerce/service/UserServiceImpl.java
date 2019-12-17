package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserDao;
import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.pojo.USERSESSION;

@Service("UserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao O_UserDao;

	@Override
	public USER isUserEmailAvailable(String mail) {
		// TODO Auto-generated method stub
		return O_UserDao.isUserEmailAvailable(mail);
	}

	@Override
	public String createNewUserByEmail(USER RO_UserPojo) {
		// TODO Auto-generated method stub
		return O_UserDao.createNewUserByEmail(RO_UserPojo);
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {
		// TODO Auto-generated method stub
		return O_UserDao.saveOTPDetails(oTP, userId);
	}

	@Override
	public USER isUserPhoneNoAvailable(BigInteger phoneNo) {
		// TODO Auto-generated method stub
		return O_UserDao.isUserPhoneNoAvailable(phoneNo);
	}

	@Override
	public String createNewUserByPhoneNo(USER RO_UserPojo) {
		// TODO Auto-generated method stub
		return O_UserDao.createNewUserByPhoneNo(RO_UserPojo);
	}

	@Override
	public USER getUserDetailHash(USER RO_UserPojo) {

		return O_UserDao.getUserDetailHash(RO_UserPojo);
	}

	@Override
	public void changeOTPStatus(String userId) {

		O_UserDao.changeOTPStatus(userId);
	}

	@Override
	public void updatePassword(USER o_USER_DETAIL) {

		O_UserDao.updatePassword(o_USER_DETAIL);

	}

	@Override
	public USERSESSION getApiSecretDataByNewSecret(String apisecret, String userId) {
		// TODO Auto-generated method stub
		return O_UserDao.getApiSecretDataByNewSecret(apisecret, userId);
	}

	@Override
	public Boolean addAuditDetail(USER O_USER_DETAIL, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return O_UserDao.addAuditDetail(O_USER_DETAIL, httpServletRequest);
	}

	@Override
	public USER getUserDetailAPIKey(String apiKey) {
		// TODO Auto-generated method stub
		return O_UserDao.getUserDetailAPIKey(apiKey);
	}

	@Override
	public Boolean userLogout(String apiKey) {
		// TODO Auto-generated method stub
		return O_UserDao.userLogout(apiKey);
	}

}
