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
import com.technokryon.ecommerce.pojo.PJ_Response;
import com.technokryon.ecommerce.pojo.PJ_TKECMUSER;
import com.technokryon.ecommerce.pojo.PJ_TKECTUSERSESSION;
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

	private ResponseEntity<?> USER_REGISTER(@RequestBody PJ_TKECMUSER RO_PJ_TKECMUSER) {

		Integer OTP = SingleTon.getRandomUserId();

		PJ_Response O_PJ_Response = new PJ_Response();

		if (RO_PJ_TKECMUSER.getTkecmuRegType() == null || RO_PJ_TKECMUSER.getTkecmuRegType().isEmpty()) {

			O_PJ_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_PJ_TKECMUSER.getTkecmuName() == null || RO_PJ_TKECMUSER.getTkecmuName().trim().equals("")) {
			O_PJ_Response.setMessage("User Name is Empty..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_PJ_TKECMUSER.getTkecmuRegType().equals("E")) {

			// Null-check for user name
			if (RO_PJ_TKECMUSER.getTkecmuMail() == null || RO_PJ_TKECMUSER.getTkecmuMail().trim().equals("")) {

				O_PJ_Response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Email Validation
			if (!SingleTon.isEmailValid(RO_PJ_TKECMUSER.getTkecmuMail())) {

				O_PJ_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.isUserEmailAvailable(RO_PJ_TKECMUSER.getTkecmuMail());
			if (O_PJ_TKECMUSER_DETAIL != null) {

				O_PJ_Response.setMessage("Email Already Exist..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_PJ_TKECMUSER.getTkecmuPassword().length() > 14 || RO_PJ_TKECMUSER.getTkecmuPassword().length() < 4) {

				O_PJ_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			RO_PJ_TKECMUSER.setTkecmuPassword(new BCryptPasswordEncoder().encode(RO_PJ_TKECMUSER.getTkecmuPassword()));

			String userId = O_UserService.createNewUserByEmail(RO_PJ_TKECMUSER);

			O_MailService.sendMail(RO_PJ_TKECMUSER.getTkecmuMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			PJ_TKECMUSER O_PJ_TKECMUSER1 = new PJ_TKECMUSER();

			O_PJ_TKECMUSER1.setTkecmuHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_PJ_TKECMUSER1, HttpStatus.OK);

		} else {

			if (RO_PJ_TKECMUSER.getTkecmuPhone() == null) {

				O_PJ_Response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (RO_PJ_TKECMUSER.getTkecmuCountryCode() == null) {

				O_PJ_Response.setMessage("Country Code Is Empty..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_PJ_TKECMUSER.getTkecmuPhone());
			if (O_PJ_TKECMUSER_DETAIL != null) {

				O_PJ_Response.setMessage("Mobile Number Already Exist..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_PJ_TKECMUSER.getTkecmuPassword().length() > 14 || RO_PJ_TKECMUSER.getTkecmuPassword().length() < 4) {

				O_PJ_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			RO_PJ_TKECMUSER.setTkecmuPassword(new BCryptPasswordEncoder().encode(RO_PJ_TKECMUSER.getTkecmuPassword()));

			String userId = O_UserService.createNewUserByPhoneNo(RO_PJ_TKECMUSER);

			O_MailService.sendSMS(RO_PJ_TKECMUSER.getTkecmuPhone(), RO_PJ_TKECMUSER.getTkecmuCountryCode(), "Your OTP is " + OTP);

			System.err.println("OTP---->" + OTP);

			PJ_TKECMUSER O_PJ_TKECMUSER1 = new PJ_TKECMUSER();

			O_PJ_TKECMUSER1.setTkecmuHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_PJ_TKECMUSER1, HttpStatus.OK);

		}

	}

	@ResponseBody
	@PostMapping("/otp/verify")

	private ResponseEntity<?> OTP_VERIFY(@RequestBody PJ_TKECMUSER RO_PJ_TKECMUSER) {

		PJ_Response O_PJ_Response = new PJ_Response();

		if (RO_PJ_TKECMUSER.getTkecmuOtp() == 0) {

			O_PJ_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_PJ_TKECMUSER.getTkecmuHashKey().isEmpty()) {

			O_PJ_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.getUserDetailHash(RO_PJ_TKECMUSER);

		if (O_PJ_TKECMUSER_DETAIL == null) {

			O_PJ_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (O_PJ_TKECMUSER_DETAIL.getTkecmuOtpExp().isBefore(OffsetDateTime.now())) {

			O_PJ_Response.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_PJ_TKECMUSER.getTkecmuOtp() != O_PJ_TKECMUSER_DETAIL.getTkecmuOtp()) {

			O_PJ_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_UserService.changeOTPStatus(O_PJ_TKECMUSER_DETAIL.getTkecmuId());

		O_PJ_Response.setMessage("Success..!");
		return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody PJ_TKECMUSER RO_PJ_TKECMUSER, HttpServletRequest httpServletRequest) {

		Integer OTP = SingleTon.getRandomUserId();

		PJ_Response O_PJ_Response = new PJ_Response();

		if (RO_PJ_TKECMUSER.getTkecmuRegType() == null || RO_PJ_TKECMUSER.getTkecmuRegType().isEmpty()) {

			O_PJ_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_PJ_TKECMUSER.getTkecmuRegType().equals("E")) {

			// Null-check for Email Id
			if (RO_PJ_TKECMUSER.getTkecmuMail() == null || RO_PJ_TKECMUSER.getTkecmuMail().trim().equals("")) {

				O_PJ_Response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Validation
			if (!SingleTon.isEmailValid(RO_PJ_TKECMUSER.getTkecmuMail())) {

				O_PJ_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Registered Or Not
			PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.isUserEmailAvailable(RO_PJ_TKECMUSER.getTkecmuMail());
			if (O_PJ_TKECMUSER_DETAIL == null) {

				O_PJ_Response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// OTP STATUS
			if (O_PJ_TKECMUSER_DETAIL.getTkecmuOtpStatus().equals("N")) {

				O_MailService.sendMail(RO_PJ_TKECMUSER.getTkecmuMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

						"Your OTP is " + OTP);

				PJ_TKECMUSER O_PJ_TKECMUSER1 = new PJ_TKECMUSER();
				O_PJ_TKECMUSER1.setTkecmuHashKey(O_UserService.saveOTPDetails(OTP, O_PJ_TKECMUSER_DETAIL.getTkecmuId()));

				return new ResponseEntity<Object>(O_PJ_TKECMUSER1, HttpStatus.OK);

			}

			// Validate STATUS
			if (O_PJ_TKECMUSER_DETAIL.getTkecmuStatus().equals("N")) {

				O_PJ_Response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_PJ_TKECMUSER.getTkecmuPassword(),
					O_PJ_TKECMUSER_DETAIL.getTkecmuPassword())) {

				O_PJ_Response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			PJ_TKECTUSERSESSION O_PJ_TKECTUSERSESSION = O_UserService.getApiSecretDataByNewSecret(apisecret,
					O_PJ_TKECMUSER_DETAIL.getTkecmuId());

			O_PJ_TKECMUSER_DETAIL.setApiKey(apisecret);

			Boolean auditDetail = O_UserService.addAuditDetail(O_PJ_TKECMUSER_DETAIL, httpServletRequest);

			return new ResponseEntity<Object>(O_PJ_TKECTUSERSESSION, HttpStatus.OK);

		} else {

			// Null-check for Phone
			if (RO_PJ_TKECMUSER.getTkecmuPhone() == null) {

				O_PJ_Response.setMessage("Phone Number Is Empty..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Phone Number Registered Or Not
			PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_PJ_TKECMUSER.getTkecmuPhone());
			if (O_PJ_TKECMUSER_DETAIL == null) {

				O_PJ_Response.setMessage("Phone Number Not Registered..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// OTP STATUS
			if (O_PJ_TKECMUSER_DETAIL.getTkecmuOtpStatus().equals("N")) {

				O_MailService.sendSMS(RO_PJ_TKECMUSER.getTkecmuPhone(), RO_PJ_TKECMUSER.getTkecmuCountryCode(),
						"Your OTP is " + OTP);

				PJ_TKECMUSER O_PJ_TKECMUSER1 = new PJ_TKECMUSER();

				O_PJ_TKECMUSER1.setTkecmuHashKey(O_UserService.saveOTPDetails(OTP, O_PJ_TKECMUSER_DETAIL.getTkecmuId()));

				return new ResponseEntity<Object>(O_PJ_TKECMUSER1, HttpStatus.OK);

			} // Validate STATUS
			if (O_PJ_TKECMUSER_DETAIL.getTkecmuStatus().equals("N")) {

				O_PJ_Response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_PJ_TKECMUSER.getTkecmuPassword(),
					O_PJ_TKECMUSER_DETAIL.getTkecmuPassword())) {

				O_PJ_Response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// API SECRET KEY LOGIC HERE
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			PJ_TKECTUSERSESSION O_PJ_TKECTUSERSESSION = O_UserService.getApiSecretDataByNewSecret(apisecret,
					O_PJ_TKECMUSER_DETAIL.getTkecmuId());

			O_PJ_TKECMUSER_DETAIL.setApiKey(apisecret);

			Boolean auditDetail = O_UserService.addAuditDetail(O_PJ_TKECMUSER_DETAIL, httpServletRequest);

			return new ResponseEntity<Object>(O_PJ_TKECTUSERSESSION, HttpStatus.OK);
		}
	}

	@ResponseBody
	@PostMapping("/forget/password")
	private ResponseEntity<?> FORGET_PASSWORD(@RequestBody PJ_TKECMUSER RO_PJ_TKECMUSER) {

		Integer OTP = SingleTon.getRandomUserId();

		PJ_Response O_PJ_Response = new PJ_Response();

		if (RO_PJ_TKECMUSER.getTkecmuRegType() == null || RO_PJ_TKECMUSER.getTkecmuRegType().isEmpty()) {

			O_PJ_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_PJ_TKECMUSER.getTkecmuRegType().equals("E")) {

			if (RO_PJ_TKECMUSER.getTkecmuMail() == null || RO_PJ_TKECMUSER.getTkecmuMail().trim().equals("")) {

				O_PJ_Response.setMessage("Email Id Is Empty..!");

				return new ResponseEntity<>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (!SingleTon.isEmailValid(RO_PJ_TKECMUSER.getTkecmuMail())) {

				O_PJ_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.isUserEmailAvailable(RO_PJ_TKECMUSER.getTkecmuMail());
			if (O_PJ_TKECMUSER_DETAIL == null) {

				O_PJ_Response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendMail(RO_PJ_TKECMUSER.getTkecmuMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			PJ_TKECMUSER O_PJ_TKECMUSER1 = new PJ_TKECMUSER();

			O_PJ_TKECMUSER1.setTkecmuHashKey(O_UserService.saveOTPDetails(OTP, O_PJ_TKECMUSER_DETAIL.getTkecmuId()));

			return new ResponseEntity<Object>(O_PJ_TKECMUSER1, HttpStatus.OK);

		} else {
			if (RO_PJ_TKECMUSER.getTkecmuPhone() == null) {

				O_PJ_Response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.isUserPhoneNoAvailable(RO_PJ_TKECMUSER.getTkecmuPhone());
			if (O_PJ_TKECMUSER_DETAIL == null) {

				O_PJ_Response.setMessage("Mobile Number Not Registered..!");
				return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendSMS(RO_PJ_TKECMUSER.getTkecmuPhone(), RO_PJ_TKECMUSER.getTkecmuCountryCode(), "Your OTP is " + OTP);

			PJ_TKECMUSER O_PJ_TKECMUSER1 = new PJ_TKECMUSER();

			O_PJ_TKECMUSER1.setTkecmuHashKey(O_UserService.saveOTPDetails(OTP, O_PJ_TKECMUSER_DETAIL.getTkecmuId()));

			return new ResponseEntity<Object>(O_PJ_TKECMUSER1, HttpStatus.OK);

		}
	}

	@ResponseBody
	@PostMapping("/update/password")

	private ResponseEntity<?> UPDATE_PASSWORD(@RequestBody PJ_TKECMUSER RO_PJ_TKECMUSER) {

		PJ_Response O_PJ_Response = new PJ_Response();

		if (RO_PJ_TKECMUSER.getTkecmuPassword().length() > 14 || RO_PJ_TKECMUSER.getTkecmuPassword().length() < 4) {

			O_PJ_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_PJ_TKECMUSER.getTkecmuHashKey().isEmpty()) {

			O_PJ_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.getUserDetailHash(RO_PJ_TKECMUSER);

		if (O_PJ_TKECMUSER_DETAIL == null) {

			O_PJ_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		O_PJ_TKECMUSER_DETAIL.setTkecmuPassword(new BCryptPasswordEncoder().encode(RO_PJ_TKECMUSER.getTkecmuPassword()));

		O_UserService.updatePassword(O_PJ_TKECMUSER_DETAIL);
		O_PJ_Response.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestBody PJ_TKECMUSER RO_PJ_TKECMUSER,
			@RequestHeader(value = "apiKey") String apiKey) {

		PJ_Response O_PJ_Response = new PJ_Response();

		PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_PJ_TKECMUSER_DETAIL == null) {

			O_PJ_Response.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (RO_PJ_TKECMUSER.getTkecmuPassword().length() > 14 || RO_PJ_TKECMUSER.getTkecmuPassword().length() < 4) {

			O_PJ_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!new BCryptPasswordEncoder().matches(RO_PJ_TKECMUSER.getOldPassword(),
				O_PJ_TKECMUSER_DETAIL.getTkecmuPassword())) {

			O_PJ_Response.setMessage("Invalid Old Password..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		RO_PJ_TKECMUSER.setTkecmuId(O_PJ_TKECMUSER_DETAIL.getTkecmuId());
		RO_PJ_TKECMUSER.setTkecmuPassword(new BCryptPasswordEncoder().encode(RO_PJ_TKECMUSER.getTkecmuPassword()));

		O_UserService.updatePassword(RO_PJ_TKECMUSER);
		O_PJ_Response.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = { "/logout" })
	ResponseEntity<?> LOGOUT(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		PJ_Response O_PJ_Response = new PJ_Response();

		Boolean logoutUpdate = O_UserService.userLogout(apiKey);

		if (!logoutUpdate) {

			O_PJ_Response.setMessage("Logout Error..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_PJ_Response.setMessage("success..!");
		return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.OK);
	}

}
