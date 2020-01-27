package com.technokryon.ecommerce.admin.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.dao.AdminLoginDao;
import com.technokryon.ecommerce.admin.pojo.User;
import com.technokryon.ecommerce.admin.pojo.UserSession;

@Service("AdminLoginService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	private AdminLoginDao adminLoginDao;

	@Override
	public Boolean checkRoleByUserId(String uId) {

		return adminLoginDao.checkRoleByUserId(uId);
	}

	@Override
	public User isUserEmailAvailable(String mail) {

		return adminLoginDao.isUserEmailAvailable(mail);
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {

		return adminLoginDao.saveOTPDetails(oTP, userId);
	}

	@Override
	public User getUserDetailHash(User user) {

		return adminLoginDao.getUserDetailHash(user);
	}

	@Override
	public void updatePassword(User userDetail) {

		adminLoginDao.updatePassword(userDetail);

	}

	@Override
	public UserSession getApiSecretDataByNewSecret(String apisecret, String userId) {

		return adminLoginDao.getApiSecretDataByNewSecret(apisecret, userId);
	}

	@Override
	public void addAuditDetail(User userDetail, HttpServletRequest httpServletRequest) {

		adminLoginDao.addAuditDetail(userDetail, httpServletRequest);
	}

	@Override
	public User getUserDetailAPIKey(String apiKey) {

		return adminLoginDao.getUserDetailAPIKey(apiKey);
	}

	@Override
	public Boolean userLogout(String apiKey) {

		return adminLoginDao.userLogout(apiKey);
	}

	@Override
	public void changeOTPStatus(String uId) {

		adminLoginDao.changeOTPStatus(uId);

	}

}
