package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

public interface UserService {

	User isUserEmailAvailable(String mail);

	String createNewUserByEmail(User RO_User);

	String saveOTPDetails(Integer oTP, String userId);

	User isUserPhoneNoAvailable(BigInteger phoneNo);

	String createNewUserByPhoneNo(User RO_User);

	User getUserDetailHash(User RO_User);

	void changeOTPStatus(String userId);

	void updatePassword(User O_User_Detail);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User O_USER_DETAIL, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

}
