package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

public interface UserService {

	User isUserEmailAvailable(String mail);

	String createNewUserByEmail(User user);

	String saveOTPDetails(Integer oTP, String userId);

	User isUserPhoneNoAvailable(BigInteger phoneNo);

	String createNewUserByPhoneNo(User user);

	User getUserDetailHash(User user);

	void changeOTPStatus(String userId);

	void updatePassword(User userDetail);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User userDetail, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

}
