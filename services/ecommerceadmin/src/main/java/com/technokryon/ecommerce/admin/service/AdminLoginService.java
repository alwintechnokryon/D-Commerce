package com.technokryon.ecommerce.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.admin.pojo.User;
import com.technokryon.ecommerce.admin.pojo.UserSession;

public interface AdminLoginService {

	Boolean checkRoleByUserId(String uId);

	User isUserEmailAvailable(String mail);

	String saveOTPDetails(Integer oTP, String userId);

	User getUserDetailHash(User user);

	void updatePassword(User userDetail);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User userDetail, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

	void changeOTPStatus(String uId);

}
