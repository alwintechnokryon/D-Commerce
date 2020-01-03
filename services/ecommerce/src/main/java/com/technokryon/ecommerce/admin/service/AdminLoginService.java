package com.technokryon.ecommerce.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

public interface AdminLoginService {

	Boolean checkRoleByUserId(String uId);

	User isUserEmailAvailable(String mail);

	String saveOTPDetails(Integer oTP, String userId);

	User getUserDetailHash(User RO_User);

	void updatePassword(User O_User_Detail);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User O_USER_DETAIL, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

	void changeOTPStatus(String uId);

}
