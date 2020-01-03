package com.technokryon.ecommerce.admin.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.dao.AdminLoginDao;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

@Service("AdminLoginService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	private AdminLoginDao O_AdminLoginDao;

	@Override
	public Boolean checkRoleByUserId(String uId) {
		// TODO Auto-generated method stub
		return O_AdminLoginDao.checkRoleByUserId(uId);
	}

	@Override
	public User isUserEmailAvailable(String mail) {

		return O_AdminLoginDao.isUserEmailAvailable(mail);
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {

		return O_AdminLoginDao.saveOTPDetails(oTP, userId);
	}

	@Override
	public User getUserDetailHash(User RO_User) {

		return O_AdminLoginDao.getUserDetailHash(RO_User);
	}

	@Override
	public void updatePassword(User O_User_Detail) {

		O_AdminLoginDao.updatePassword(O_User_Detail);

	}

	@Override
	public UserSession getApiSecretDataByNewSecret(String apisecret, String userId) {

		return O_AdminLoginDao.getApiSecretDataByNewSecret(apisecret, userId);
	}

	@Override
	public void addAuditDetail(User O_USER_DETAIL, HttpServletRequest httpServletRequest) {

		O_AdminLoginDao.addAuditDetail(O_USER_DETAIL, httpServletRequest);
	}

	@Override
	public User getUserDetailAPIKey(String apiKey) {

		return O_AdminLoginDao.getUserDetailAPIKey(apiKey);
	}

	@Override
	public Boolean userLogout(String apiKey) {

		return O_AdminLoginDao.userLogout(apiKey);
	}

	@Override
	public void changeOTPStatus(String uId) {

		O_AdminLoginDao.changeOTPStatus(uId);

	}

}
