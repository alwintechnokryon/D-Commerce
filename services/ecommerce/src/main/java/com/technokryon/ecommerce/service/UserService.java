package com.technokryon.ecommerce.service;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import com.technokryon.ecommerce.pojo.PJ_TKECMUSER;
import com.technokryon.ecommerce.pojo.PJ_TKECTUSERSESSION;

public interface UserService {

	PJ_TKECMUSER isUserEmailAvailable(String mail);

	String createNewUserByEmail(PJ_TKECMUSER RO_UserPojo);

	String saveOTPDetails(Integer oTP, String userId);

	PJ_TKECMUSER isUserPhoneNoAvailable(BigInteger phoneNo);

	String createNewUserByPhoneNo(PJ_TKECMUSER RO_UserPojo);

	PJ_TKECMUSER getUserDetailHash(PJ_TKECMUSER RO_UserPojo);

	void changeOTPStatus(String userId);

	void updatePassword(PJ_TKECMUSER o_PJ_TKECMUSER_DETAIL);

	PJ_TKECTUSERSESSION getApiSecretDataByNewSecret(String apisecret, String userId);

	Boolean addAuditDetail(PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL, HttpServletRequest httpServletRequest);

	PJ_TKECMUSER getUserDetailAPIKey(String apiKey);

	Boolean userLogout(String apiKey);

}
