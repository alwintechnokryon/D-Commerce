package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.pojo.USERSESSION;

public interface UserService {

	USER isUserEmailAvailable(String mail);

	String createNewUserByEmail(USER RO_UserPojo);

	String saveOTPDetails(Integer oTP, String userId);

	USER isUserPhoneNoAvailable(BigInteger phoneNo);

	String createNewUserByPhoneNo(USER RO_UserPojo);

	USER getUserDetailHash(USER RO_UserPojo);

	void changeOTPStatus(String userId);

	void updatePassword(USER o_USER_DETAIL);

	USERSESSION getApiSecretDataByNewSecret(String apisecret, String userId);

	Boolean addAuditDetail(USER O_USER_DETAIL, HttpServletRequest httpServletRequest);

	USER getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

}
