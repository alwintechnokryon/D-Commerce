package com.technokryon.ecommerce.user.controller;

import java.time.OffsetDateTime;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.SingleTon;
import com.technokryon.ecommerce.pojo.RESPONSE;
import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.pojo.USERSESSION;
import com.technokryon.ecommerce.service.MailService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user")
public class RegisterController {

	@Autowired
	private UserService O_UserService;

	@Autowired
	private MailService O_MailService;

	@ResponseBody
	@PostMapping("/register")

	private ResponseEntity<?> USER_REGISTER(@RequestBody USER RO_USER) {

		Integer OTP = SingleTon.getRandomUserId();

		RESPONSE O_RESPONSE = new RESPONSE();

		if (RO_USER.getURegType() == null) {

			O_RESPONSE.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USER.getUName() == null || RO_USER.getUName().trim().equals("")) {
			O_RESPONSE.setMessage("User Name is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getURegType().equals("E")) {

			// Null-check for user name
			if (RO_USER.getUMail() == null || RO_USER.getUMail().trim().equals("")) {

				O_RESPONSE.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Email Validation
			if (!SingleTon.isEmailValid(RO_USER.getUMail())) {

				O_RESPONSE.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			USER O_USER_DETAIL = O_UserService.isUserEmailAvailable(RO_USER.getUMail());
			if (O_USER_DETAIL != null) {

				O_RESPONSE.setMessage("Email Already Exist..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_USER.getUPassword().length() > 14 || RO_USER.getUPassword().length() < 4) {

				O_RESPONSE.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			RO_USER.setUPassword(new BCryptPasswordEncoder().encode(RO_USER.getUPassword()));

			String userId = O_UserService.createNewUserByEmail(RO_USER);

			O_MailService.sendMail(RO_USER.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			USER O_USER1 = new USER();

			O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		} else if(RO_USER.getURegType().equals("M")){

			if (RO_USER.getUPhone() == null) {

				O_RESPONSE.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (RO_USER.getUCountryCode() == null) {

				O_RESPONSE.setMessage("Country Code Is Empty..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			USER O_USER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_USER.getUPhone());
			if (O_USER_DETAIL != null) {

				O_RESPONSE.setMessage("Mobile Number Already Exist..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_USER.getUPassword().length() > 14 || RO_USER.getUPassword().length() < 4) {

				O_RESPONSE.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			RO_USER.setUPassword(new BCryptPasswordEncoder().encode(RO_USER.getUPassword()));

			String userId = O_UserService.createNewUserByPhoneNo(RO_USER);

			O_MailService.sendSMS(RO_USER.getUPhone(), RO_USER.getUCountryCode(), "Your OTP is " + OTP);

			System.err.println("OTP---->" + OTP);

			USER O_USER1 = new USER();

			O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		}else {

			O_RESPONSE.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@ResponseBody
	@PostMapping("/otp/verify")

	private ResponseEntity<?> OTP_VERIFY(@RequestBody USER RO_USER) {

		RESPONSE O_RESPONSE = new RESPONSE();

		if (RO_USER.getUOtp() == 0) {

			O_RESPONSE.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getUHashKey().isEmpty()) {

			O_RESPONSE.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		USER O_USER_DETAIL = O_UserService.getUserDetailHash(RO_USER);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (O_USER_DETAIL.getUOtpExp().isBefore(OffsetDateTime.now())) {

			O_RESPONSE.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getUOtp() != O_USER_DETAIL.getUOtp()) {

			O_RESPONSE.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_UserService.changeOTPStatus(O_USER_DETAIL.getUId());

		O_RESPONSE.setMessage("Success..!");
		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody USER RO_USER, HttpServletRequest httpServletRequest) {

		Integer OTP = SingleTon.getRandomUserId();

		RESPONSE O_RESPONSE = new RESPONSE();

		if (RO_USER.getURegType() == null) {

			O_RESPONSE.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USER.getURegType().equals("E")) {

			// Null-check for Email Id
			if (RO_USER.getUMail() == null || RO_USER.getUMail().trim().equals("")) {

				O_RESPONSE.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Validation
			if (!SingleTon.isEmailValid(RO_USER.getUMail())) {

				O_RESPONSE.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Registered Or Not
			USER O_USER_DETAIL = O_UserService.isUserEmailAvailable(RO_USER.getUMail());
			if (O_USER_DETAIL == null) {

				O_RESPONSE.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// OTP STATUS
			if (O_USER_DETAIL.getUOtpStatus().equals("N")) {

				O_MailService.sendMail(RO_USER.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

						"Your OTP is " + OTP);

				USER O_USER1 = new USER();
				O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getUId()));

				return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

			}

			// Validate STATUS
			if (O_USER_DETAIL.getUStatus().equals("N")) {

				O_RESPONSE.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_USER.getUPassword(), O_USER_DETAIL.getUPassword())) {

				O_RESPONSE.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			USERSESSION O_USERSESSION = O_UserService.getApiSecretDataByNewSecret(apisecret, O_USER_DETAIL.getUId());

			O_USER_DETAIL.setApiKey(apisecret);

			Boolean auditDetail = O_UserService.addAuditDetail(O_USER_DETAIL, httpServletRequest);

			return new ResponseEntity<Object>(O_USERSESSION, HttpStatus.OK);

		} else if (RO_USER.getURegType().equals("M")) {

			// Null-check for Phone
			if (RO_USER.getUPhone() == null) {

				O_RESPONSE.setMessage("Phone Number Is Empty..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Phone Number Registered Or Not
			USER O_USER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_USER.getUPhone());
			if (O_USER_DETAIL == null) {

				O_RESPONSE.setMessage("Phone Number Not Registered..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// OTP STATUS
			if (O_USER_DETAIL.getUOtpStatus().equals("N")) {

				O_MailService.sendSMS(RO_USER.getUPhone(), RO_USER.getUCountryCode(), "Your OTP is " + OTP);

				USER O_USER1 = new USER();

				O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getUId()));

				return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

			} // Validate STATUS
			if (O_USER_DETAIL.getUStatus().equals("N")) {

				O_RESPONSE.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_USER.getUPassword(), O_USER_DETAIL.getUPassword())) {

				O_RESPONSE.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// API SECRET KEY LOGIC HERE
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			USERSESSION O_PJ_TKECTUSERSESSION = O_UserService.getApiSecretDataByNewSecret(apisecret,
					O_USER_DETAIL.getUId());

			O_USER_DETAIL.setApiKey(apisecret);

			Boolean auditDetail = O_UserService.addAuditDetail(O_USER_DETAIL, httpServletRequest);

			return new ResponseEntity<Object>(O_PJ_TKECTUSERSESSION, HttpStatus.OK);
		} else {

			O_RESPONSE.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@ResponseBody
	@PostMapping("/forget/password")
	private ResponseEntity<?> FORGET_PASSWORD(@RequestBody USER RO_USER) {

		Integer OTP = SingleTon.getRandomUserId();

		RESPONSE O_RESPONSE = new RESPONSE();

		if (RO_USER.getURegType() == null) {

			O_RESPONSE.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USER.getURegType().equals("E")) {

			if (RO_USER.getUMail() == null || RO_USER.getUMail().trim().equals("")) {

				O_RESPONSE.setMessage("Email Id Is Empty..!");

				return new ResponseEntity<>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (!SingleTon.isEmailValid(RO_USER.getUMail())) {

				O_RESPONSE.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			USER O_USER_DETAIL = O_UserService.isUserEmailAvailable(RO_USER.getUMail());
			if (O_USER_DETAIL == null) {

				O_RESPONSE.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendMail(RO_USER.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			USER O_USER1 = new USER();

			O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getUId()));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		} else if(RO_USER.getURegType().equals("M")) {
			
			if(RO_USER.getUPhone() == null) {

				O_RESPONSE.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			USER O_USER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_USER.getUPhone());
			if (O_USER_DETAIL == null) {

				O_RESPONSE.setMessage("Mobile Number Not Registered..!");
				return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendSMS(RO_USER.getUPhone(), RO_USER.getUCountryCode(), "Your OTP is " + OTP);

			USER O_USER1 = new USER();

			O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getUId()));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		} else {

			O_RESPONSE.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@ResponseBody
	@PostMapping("/update/password")

	private ResponseEntity<?> UPDATE_PASSWORD(@RequestBody USER RO_USER) {

		RESPONSE O_RESPONSE = new RESPONSE();

		if (RO_USER.getUPassword().length() > 14 || RO_USER.getUPassword().length() < 4) {

			O_RESPONSE.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getUHashKey().isEmpty()) {

			O_RESPONSE.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		USER O_USER_DETAIL = O_UserService.getUserDetailHash(RO_USER);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		O_USER_DETAIL.setUPassword(new BCryptPasswordEncoder().encode(RO_USER.getUPassword()));

		O_UserService.updatePassword(O_USER_DETAIL);
		O_RESPONSE.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestBody USER RO_USER,
			@RequestHeader(value = "apiKey") String apiKey) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (RO_USER.getUPassword().length() > 14 || RO_USER.getUPassword().length() < 4) {

			O_RESPONSE.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!new BCryptPasswordEncoder().matches(RO_USER.getOldPassword(), O_USER_DETAIL.getUPassword())) {

			O_RESPONSE.setMessage("Invalid Old Password..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		RO_USER.setUId(O_USER_DETAIL.getUId());
		RO_USER.setUPassword(new BCryptPasswordEncoder().encode(RO_USER.getUPassword()));

		O_UserService.updatePassword(RO_USER);
		O_RESPONSE.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = { "/logout" })
	ResponseEntity<?> LOGOUT(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		RESPONSE O_RESPONSE = new RESPONSE();

		Boolean logoutUpdate = O_UserService.userLogout(apiKey);

		if (!logoutUpdate) {

			O_RESPONSE.setMessage("Logout Error..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_RESPONSE.setMessage("success..!");
		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);
	}

}
