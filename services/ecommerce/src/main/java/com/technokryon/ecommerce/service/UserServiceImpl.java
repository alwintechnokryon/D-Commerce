package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.UserDao;
import com.technokryon.ecommerce.pojo.PJ_TKECMUSER;
import com.technokryon.ecommerce.pojo.PJ_TKECTUSERSESSION;

@Service("UserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao O_UserDao;

	@Override
	public PJ_TKECMUSER isUserEmailAvailable(String mail) {
		// TODO Auto-generated method stub
		return O_UserDao.isUserEmailAvailable(mail);
	}

	@Override
	public String createNewUserByEmail(PJ_TKECMUSER RO_UserPojo) {
		// TODO Auto-generated method stub
		return O_UserDao.createNewUserByEmail(RO_UserPojo);
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {
		// TODO Auto-generated method stub
		return O_UserDao.saveOTPDetails(oTP, userId);
	}

	@Override
	public PJ_TKECMUSER isUserPhoneNoAvailable(BigInteger phoneNo) {
		// TODO Auto-generated method stub
		return O_UserDao.isUserPhoneNoAvailable(phoneNo);
	}

	@Override
	public String createNewUserByPhoneNo(PJ_TKECMUSER RO_UserPojo) {
		// TODO Auto-generated method stub
		return O_UserDao.createNewUserByPhoneNo(RO_UserPojo);
	}

	@Override
	public PJ_TKECMUSER getUserDetailHash(PJ_TKECMUSER RO_UserPojo) {

		return O_UserDao.getUserDetailHash(RO_UserPojo);
	}

	@Override
	public void changeOTPStatus(String userId) {

		O_UserDao.changeOTPStatus(userId);
	}

	@Override
	public void updatePassword(PJ_TKECMUSER o_PJ_TKECMUSER_DETAIL) {

		O_UserDao.updatePassword(o_PJ_TKECMUSER_DETAIL);

	}

	@Override
	public PJ_TKECTUSERSESSION getApiSecretDataByNewSecret(String apisecret, String userId) {
		// TODO Auto-generated method stub
		return O_UserDao.getApiSecretDataByNewSecret(apisecret, userId);
	}

	@Override
	public Boolean addAuditDetail(PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		return O_UserDao.addAuditDetail(O_PJ_TKECMUSER_DETAIL, httpServletRequest);
	}

	@Override
	public PJ_TKECMUSER getUserDetailAPIKey(String apiKey) {
		// TODO Auto-generated method stub
		return O_UserDao.getUserDetailAPIKey(apiKey);
	}

	@Override
	public Boolean userLogout(String apiKey) {
		// TODO Auto-generated method stub
		return O_UserDao.userLogout(apiKey);
	}

}
