package com.technokryon.ecommerce.admin.dao;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.admin.pojo.User;
import com.technokryon.ecommerce.admin.pojo.UserSession;

public interface AdminLoginDao {

	Boolean checkRoleByUserId(String uId);

	User getUserDetailHash(User user);

	User isUserEmailAvailable(String mail);

	String saveOTPDetails(Integer oTP, String userId);

	void updatePassword(User userDetail);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User userDetail, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

	void changeOTPStatus(String uId);

}
