package com.technokryon.ecommerce.dao;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;


public interface UserDao {

	User getUserDetailHash(User RO_User);

	void changeOTPStatus(String userId);

	User isUserEmailAvailable(String mail);

	String createNewUserByEmail(User RO_User);

	String saveOTPDetails(Integer oTP, String userId);

	User isUserPhoneNoAvailable(BigInteger phoneNo);

	String createNewUserByPhoneNo(User RO_User);

	void updatePassword(User O_USER_DETAIL);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User O_USER_DETAIL, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

}
