package com.technokryon.ecommerce.admin.dao;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

public interface AdminLoginDao {

	Boolean checkRoleByUserId(String uId);

	User getUserDetailHash(User RO_User);

	User isUserEmailAvailable(String mail);

	String saveOTPDetails(Integer oTP, String userId);

	void updatePassword(User O_USER_DETAIL);

	UserSession getApiSecretDataByNewSecret(String apisecret, String userId);

	void addAuditDetail(User O_USER_DETAIL, HttpServletRequest httpServletRequest);

	User getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

	void changeOTPStatus(String uId);

}
