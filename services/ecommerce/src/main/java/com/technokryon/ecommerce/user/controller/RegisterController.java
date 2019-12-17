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

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.technokryon.ecommerce.SingleTon;
import com.technokryon.ecommerce.pojo.Response;
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

		Response O_Response = new Response();

		if (RO_USER.getRegType() == null || RO_USER.getRegType().isEmpty()) {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USER.getName() == null || RO_USER.getName().trim().equals("")) {
			O_Response.setMessage("User Name is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getRegType().equals("E")) {

			// Null-check for user name
			if (RO_USER.getMail() == null || RO_USER.getMail().trim().equals("")) {

				O_Response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Email Validation
			if (!SingleTon.isEmailValid(RO_USER.getMail())) {

				O_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			USER O_USER_DETAIL = O_UserService.isUserEmailAvailable(RO_USER.getMail());
			if (O_USER_DETAIL != null) {

				O_Response.setMessage("Email Already Exist..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_USER.getPassword().length() > 14 || RO_USER.getPassword().length() < 4) {

				O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			RO_USER.setPassword(new BCryptPasswordEncoder().encode(RO_USER.getPassword()));

			String userId = O_UserService.createNewUserByEmail(RO_USER);

			O_MailService.sendMail(RO_USER.getMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			USER O_USER1 = new USER();

			O_USER1.setHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		} else {

			if (RO_USER.getPhone() == null) {

				O_Response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (RO_USER.getCountryCode() == null) {

				O_Response.setMessage("Country Code Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			USER O_USER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_USER.getPhone());
			if (O_USER_DETAIL != null) {

				O_Response.setMessage("Mobile Number Already Exist..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_USER.getPassword().length() > 14 || RO_USER.getPassword().length() < 4) {

				O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			RO_USER.setPassword(new BCryptPasswordEncoder().encode(RO_USER.getPassword()));

			String userId = O_UserService.createNewUserByPhoneNo(RO_USER);

			O_MailService.sendSMS(RO_USER.getPhone(), RO_USER.getCountryCode(), "Your OTP is " + OTP);

			System.err.println("OTP---->" + OTP);

			USER O_USER1 = new USER();

			O_USER1.setHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		}

	}

	@ResponseBody
	@PostMapping("/otp/verify")

	private ResponseEntity<?> OTP_VERIFY(@RequestBody USER RO_USER) {

		Response O_Response = new Response();

		if (RO_USER.getOtp() == 0) {

			O_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getHashKey().isEmpty()) {

			O_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		USER O_USER_DETAIL = O_UserService.getUserDetailHash(RO_USER);

		if (O_USER_DETAIL == null) {

			O_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (O_USER_DETAIL.getOtpExp().isBefore(OffsetDateTime.now())) {

			O_Response.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getOtp() != O_USER_DETAIL.getOtp()) {

			O_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_UserService.changeOTPStatus(O_USER_DETAIL.getId());

		O_Response.setMessage("Success..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody USER RO_USER, HttpServletRequest httpServletRequest) {

		Integer OTP = SingleTon.getRandomUserId();

		Response O_Response = new Response();

		if (RO_USER.getRegType() == null || RO_USER.getRegType().isEmpty()) {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USER.getRegType().equals("E")) {

			// Null-check for Email Id
			if (RO_USER.getMail() == null || RO_USER.getMail().trim().equals("")) {

				O_Response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Validation
			if (!SingleTon.isEmailValid(RO_USER.getMail())) {

				O_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Registered Or Not
			USER O_USER_DETAIL = O_UserService.isUserEmailAvailable(RO_USER.getMail());
			if (O_USER_DETAIL == null) {

				O_Response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// OTP STATUS
			if (O_USER_DETAIL.getOtpStatus().equals("N")) {

				O_MailService.sendMail(RO_USER.getMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

						"Your OTP is " + OTP);

				USER O_USER1 = new USER();
				O_USER1.setHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getId()));

				return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

			}

			// Validate STATUS
			if (O_USER_DETAIL.getStatus().equals("N")) {

				O_Response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_USER.getPassword(),
					O_USER_DETAIL.getPassword())) {

				O_Response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			USERSESSION O_USERSESSION = O_UserService.getApiSecretDataByNewSecret(apisecret,
					O_USER_DETAIL.getId());

			O_USER_DETAIL.setApiKey(apisecret);

			Boolean auditDetail = O_UserService.addAuditDetail(O_USER_DETAIL, httpServletRequest);

			return new ResponseEntity<Object>(O_USERSESSION, HttpStatus.OK);

		} else {

			// Null-check for Phone
			if (RO_USER.getPhone() == null) {

				O_Response.setMessage("Phone Number Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Phone Number Registered Or Not
			USER O_USER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_USER.getPhone());
			if (O_USER_DETAIL == null) {

				O_Response.setMessage("Phone Number Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// OTP STATUS
			if (O_USER_DETAIL.getOtpStatus().equals("N")) {

				O_MailService.sendSMS(RO_USER.getPhone(), RO_USER.getCountryCode(),
						"Your OTP is " + OTP);

				USER O_USER1 = new USER();

				O_USER1.setHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getId()));

				return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

			} // Validate STATUS
			if (O_USER_DETAIL.getStatus().equals("N")) {

				O_Response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_USER.getPassword(),
					O_USER_DETAIL.getPassword())) {

				O_Response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// API SECRET KEY LOGIC HERE
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			USERSESSION O_PJ_TKECTUSERSESSION = O_UserService.getApiSecretDataByNewSecret(apisecret,
					O_USER_DETAIL.getId());

			O_USER_DETAIL.setApiKey(apisecret);

			Boolean auditDetail = O_UserService.addAuditDetail(O_USER_DETAIL, httpServletRequest);

			return new ResponseEntity<Object>(O_PJ_TKECTUSERSESSION, HttpStatus.OK);
		}
	}

	@ResponseBody
	@PostMapping("/forget/password")
	private ResponseEntity<?> FORGET_PASSWORD(@RequestBody USER RO_USER) {

		Integer OTP = SingleTon.getRandomUserId();

		Response O_Response = new Response();

		if (RO_USER.getRegType() == null || RO_USER.getRegType().isEmpty()) {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USER.getRegType().equals("E")) {

			if (RO_USER.getMail() == null || RO_USER.getMail().trim().equals("")) {

				O_Response.setMessage("Email Id Is Empty..!");

				return new ResponseEntity<>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (!SingleTon.isEmailValid(RO_USER.getMail())) {

				O_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			USER O_USER_DETAIL = O_UserService.isUserEmailAvailable(RO_USER.getMail());
			if (O_USER_DETAIL == null) {

				O_Response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendMail(RO_USER.getMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			USER O_USER1 = new USER();

			O_USER1.setHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getId()));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		} else {
			if (RO_USER.getPhone() == null) {

				O_Response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			USER O_USER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_USER.getPhone());
			if (O_USER_DETAIL == null) {

				O_Response.setMessage("Mobile Number Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendSMS(RO_USER.getPhone(), RO_USER.getCountryCode(), "Your OTP is " + OTP);

			USER O_USER1 = new USER();

			O_USER1.setHashKey(O_UserService.saveOTPDetails(OTP, O_USER_DETAIL.getId()));

			return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

		}
	}

	@ResponseBody
	@PostMapping("/update/password")

	private ResponseEntity<?> UPDATE_PASSWORD(@RequestBody USER RO_USER) {

		Response O_Response = new Response();

		if (RO_USER.getPassword().length() > 14 || RO_USER.getPassword().length() < 4) {

			O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USER.getHashKey().isEmpty()) {

			O_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		USER O_USER_DETAIL = O_UserService.getUserDetailHash(RO_USER);

		if (O_USER_DETAIL == null) {

			O_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		O_USER_DETAIL.setPassword(new BCryptPasswordEncoder().encode(RO_USER.getPassword()));

		O_UserService.updatePassword(O_USER_DETAIL);
		O_Response.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestBody USER RO_USER,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response O_Response = new Response();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_Response.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (RO_USER.getPassword().length() > 14 || RO_USER.getPassword().length() < 4) {

			O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!new BCryptPasswordEncoder().matches(RO_USER.getOldPassword(),
				O_USER_DETAIL.getPassword())) {

			O_Response.setMessage("Invalid Old Password..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		RO_USER.setId(O_USER_DETAIL.getId());
		RO_USER.setPassword(new BCryptPasswordEncoder().encode(RO_USER.getPassword()));

		O_UserService.updatePassword(RO_USER);
		O_Response.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = { "/logout" })
	ResponseEntity<?> LOGOUT(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		Response O_Response = new Response();

		Boolean logoutUpdate = O_UserService.userLogout(apiKey);

		if (!logoutUpdate) {

			O_Response.setMessage("Logout Error..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_Response.setMessage("success..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

}
