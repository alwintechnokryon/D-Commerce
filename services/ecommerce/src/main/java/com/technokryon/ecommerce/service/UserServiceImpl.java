package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserDao;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

@Service("UserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao O_UserDao;

	@Override
	public User isUserEmailAvailable(String mail) {

		return O_UserDao.isUserEmailAvailable(mail);
	}

	@Override
	public String createNewUserByEmail(User RO_User) {

		return O_UserDao.createNewUserByEmail(RO_User);
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {

		return O_UserDao.saveOTPDetails(oTP, userId);
	}

	@Override
	public User isUserPhoneNoAvailable(BigInteger phoneNo) {

		return O_UserDao.isUserPhoneNoAvailable(phoneNo);
	}

	@Override
	public String createNewUserByPhoneNo(User RO_User) {

		return O_UserDao.createNewUserByPhoneNo(RO_User);
	}

	@Override
	public User getUserDetailHash(User RO_User) {

		return O_UserDao.getUserDetailHash(RO_User);
	}

	@Override
	public void changeOTPStatus(String userId) {

		O_UserDao.changeOTPStatus(userId);
	}

	@Override
	public void updatePassword(User O_User_Detail) {

		O_UserDao.updatePassword(O_User_Detail);

	}

	@Override
	public UserSession getApiSecretDataByNewSecret(String apisecret, String userId) {

		return O_UserDao.getApiSecretDataByNewSecret(apisecret, userId);
	}

	@Override
	public void addAuditDetail(User O_USER_DETAIL, HttpServletRequest httpServletRequest) {

		O_UserDao.addAuditDetail(O_USER_DETAIL, httpServletRequest);
	}

	@Override
	public User getUserDetailAPIKey(String apiKey) {

		return O_UserDao.getUserDetailAPIKey(apiKey);
	}

	@Override
	public Boolean userLogout(String apiKey) {

		return O_UserDao.userLogout(apiKey);
	}

}
