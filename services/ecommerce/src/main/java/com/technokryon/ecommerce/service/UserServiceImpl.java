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
	private UserDao userDao;

	@Override
	public User isUserEmailAvailable(String mail) {

		return userDao.isUserEmailAvailable(mail);
	}

	@Override
	public String createNewUserByEmail(User user) {

		return userDao.createNewUserByEmail(user);
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {

		return userDao.saveOTPDetails(oTP, userId);
	}

	@Override
	public User isUserPhoneNoAvailable(BigInteger phoneNo) {

		return userDao.isUserPhoneNoAvailable(phoneNo);
	}

	@Override
	public String createNewUserByPhoneNo(User user) {

		return userDao.createNewUserByPhoneNo(user);
	}

	@Override
	public User getUserDetailHash(User user) {

		return userDao.getUserDetailHash(user);
	}

	@Override
	public void changeOTPStatus(String userId) {

		userDao.changeOTPStatus(userId);
	}

	@Override
	public void updatePassword(User userDetail) {

		userDao.updatePassword(userDetail);

	}

	@Override
	public UserSession getApiSecretDataByNewSecret(String apisecret, String userId) {

		return userDao.getApiSecretDataByNewSecret(apisecret, userId);
	}

	@Override
	public void addAuditDetail(User userDetail, HttpServletRequest httpServletRequest) {

		userDao.addAuditDetail(userDetail, httpServletRequest);
	}

	@Override
	public User getUserDetailAPIKey(String apiKey) {

		return userDao.getUserDetailAPIKey(apiKey);
	}

	@Override
	public Boolean userLogout(String apiKey) {

		return userDao.userLogout(apiKey);
	}

}
