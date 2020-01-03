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
import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;
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

	private ResponseEntity<?> USER_REGISTER(@RequestBody User RO_User) {

		Integer OTP = SingleTon.getRandomUserId();

		Response O_Response = new Response();

		if (RO_User.getURegType() == null) {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_User.getUName() == null || RO_User.getUName().trim().equals("")) {
			O_Response.setMessage("User Name is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_User.getURegType().equals("E")) {

			// Null-check for user name
			if (RO_User.getUMail() == null || RO_User.getUMail().trim().equals("")) {

				O_Response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Email Validation
			if (!SingleTon.isEmailValid(RO_User.getUMail())) {

				O_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			User O_User_Detail = O_UserService.isUserEmailAvailable(RO_User.getUMail());
			if (O_User_Detail != null) {

				O_Response.setMessage("Email Already Exist..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_User.getUPassword().length() > 14 || RO_User.getUPassword().length() < 4) {

				O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			RO_User.setUPassword(new BCryptPasswordEncoder().encode(RO_User.getUPassword()));

			String userId = O_UserService.createNewUserByEmail(RO_User);

			O_MailService.sendMail(RO_User.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			User O_User1 = new User();

			O_User1.setUHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_User1, HttpStatus.OK);

		} else if (RO_User.getURegType().equals("M")) {

			if (RO_User.getUPhone() == null) {

				O_Response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (RO_User.getUPhoneCode() == null) {

				O_Response.setMessage("Country Code Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			User O_User_Detail = O_UserService.isUserPhoneNoAvailable(RO_User.getUPhone());
			if (O_User_Detail != null) {

				O_Response.setMessage("Mobile Number Already Exist..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (RO_User.getUPassword().length() > 14 || RO_User.getUPassword().length() < 4) {

				O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			RO_User.setUPassword(new BCryptPasswordEncoder().encode(RO_User.getUPassword()));

			String userId = O_UserService.createNewUserByPhoneNo(RO_User);

			O_MailService.sendSMS(RO_User.getUPhone(), RO_User.getUPhoneCode(), "Your OTP is " + OTP);

			System.err.println("OTP---->" + OTP);

			User O_User1 = new User();

			O_User1.setUHashKey(O_UserService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(O_User1, HttpStatus.OK);

		} else {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@ResponseBody
	@PostMapping("/otp/verify")

	private ResponseEntity<?> OTP_VERIFY(@RequestBody User RO_User) {

		Response O_Response = new Response();

		if (RO_User.getUOtp() == 0) {

			O_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_User.getUHashKey().isEmpty()) {

			O_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User O_User_Detail = O_UserService.getUserDetailHash(RO_User);

		if (O_User_Detail == null) {

			O_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (O_User_Detail.getUOtpExp().isBefore(OffsetDateTime.now())) {

			O_Response.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_User.getUOtp() != O_User_Detail.getUOtp()) {

			O_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_UserService.changeOTPStatus(O_User_Detail.getUId());

		O_Response.setMessage("Success..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/forgot/otp/verify")

	private ResponseEntity<?> FORGOT_OTP_VERIFY(@RequestBody User RO_User) {

		Response O_Response = new Response();

		if (RO_User.getUOtp() == 0) {

			O_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_User.getUHashKey().isEmpty()) {

			O_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User O_User_Detail = O_UserService.getUserDetailHash(RO_User);

		if (O_User_Detail == null) {

			O_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (O_User_Detail.getUOtpExp().isBefore(OffsetDateTime.now())) {

			O_Response.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_User.getUOtp() != O_User_Detail.getUOtp()) {

			O_Response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_Response.setMessage("Success..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	
	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody User RO_User, HttpServletRequest httpServletRequest) {

		Integer OTP = SingleTon.getRandomUserId();

		Response O_Response = new Response();

		if (RO_User.getURegType() == null) {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_User.getURegType().equals("E")) {

			// Null-check for Email Id
			if (RO_User.getUMail() == null || RO_User.getUMail().trim().equals("")) {

				O_Response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Validation
			if (!SingleTon.isEmailValid(RO_User.getUMail())) {

				O_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Registered Or Not
			User O_User_Detail = O_UserService.isUserEmailAvailable(RO_User.getUMail());
			if (O_User_Detail == null) {

				O_Response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// OTP STATUS
			if (O_User_Detail.getUOtpStatus().equals("N")) {

				O_MailService.sendMail(RO_User.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

						"Your OTP is " + OTP);

				User O_USER1 = new User();
				O_USER1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_User_Detail.getUId()));

				return new ResponseEntity<Object>(O_USER1, HttpStatus.OK);

			}

			// Validate STATUS
			if (O_User_Detail.getUStatus().equals("N")) {

				O_Response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_User.getUPassword(), O_User_Detail.getUPassword())) {

				O_Response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			UserSession O_UserSession = O_UserService.getApiSecretDataByNewSecret(apisecret, O_User_Detail.getUId());

			O_User_Detail.setApiKey(apisecret);

			O_UserService.addAuditDetail(O_User_Detail, httpServletRequest);

			return new ResponseEntity<Object>(O_UserSession, HttpStatus.OK);

		} else if (RO_User.getURegType().equals("M")) {

			// Null-check for Phone
			if (RO_User.getUPhone() == null) {

				O_Response.setMessage("Phone Number Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Phone Number Registered Or Not
			User O_User_Detail = O_UserService.isUserPhoneNoAvailable(RO_User.getUPhone());
			if (O_User_Detail == null) {

				O_Response.setMessage("Phone Number Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// OTP STATUS
			if (O_User_Detail.getUOtpStatus().equals("N")) {

				O_MailService.sendSMS(RO_User.getUPhone(), RO_User.getUPhoneCode(), "Your OTP is " + OTP);

				User O_User1 = new User();

				O_User1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_User_Detail.getUId()));

				return new ResponseEntity<Object>(O_User1, HttpStatus.OK);

			} // Validate STATUS
			if (O_User_Detail.getUStatus().equals("N")) {

				O_Response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(RO_User.getUPassword(), O_User_Detail.getUPassword())) {

				O_Response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// API SECRET KEY LOGIC HERE
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			UserSession O_UserSession = O_UserService.getApiSecretDataByNewSecret(apisecret, O_User_Detail.getUId());

			O_User_Detail.setApiKey(apisecret);

			O_UserService.addAuditDetail(O_User_Detail, httpServletRequest);

			return new ResponseEntity<Object>(O_UserSession, HttpStatus.OK);
		} else {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@ResponseBody
	@PostMapping("/forgot")
	private ResponseEntity<?> FORGOT(@RequestBody User RO_User) {

		Integer OTP = SingleTon.getRandomUserId();

		Response O_Response = new Response();

		if (RO_User.getURegType() == null) {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_User.getURegType().equals("E")) {

			if (RO_User.getUMail() == null || RO_User.getUMail().trim().equals("")) {

				O_Response.setMessage("Email Id Is Empty..!");

				return new ResponseEntity<>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (!SingleTon.isEmailValid(RO_User.getUMail())) {

				O_Response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			User O_User_Detail = O_UserService.isUserEmailAvailable(RO_User.getUMail());
			if (O_User_Detail == null) {

				O_Response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendMail(RO_User.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			User O_User1 = new User();

			O_User1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_User_Detail.getUId()));

			return new ResponseEntity<Object>(O_User1, HttpStatus.OK);

		} else if (RO_User.getURegType().equals("M")) {

			if (RO_User.getUPhone() == null) {

				O_Response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			User O_User_Detail = O_UserService.isUserPhoneNoAvailable(RO_User.getUPhone());
			if (O_User_Detail == null) {

				O_Response.setMessage("Mobile Number Not Registered..!");
				return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			O_MailService.sendSMS(RO_User.getUPhone(), RO_User.getUPhoneCode(), "Your OTP is " + OTP);

			User O_User1 = new User();

			O_User1.setUHashKey(O_UserService.saveOTPDetails(OTP, O_User_Detail.getUId()));

			return new ResponseEntity<Object>(O_User1, HttpStatus.OK);

		} else {

			O_Response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@ResponseBody
	@PostMapping("/update/password")

	private ResponseEntity<?> UPDATE_PASSWORD(@RequestBody User RO_User) {

		Response O_Response = new Response();

		if (RO_User.getUPassword().length() > 14 || RO_User.getUPassword().length() < 4) {

			O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_User.getUHashKey().isEmpty()) {

			O_Response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User O_User_Detail = O_UserService.getUserDetailHash(RO_User);

		if (O_User_Detail == null) {

			O_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		O_User_Detail.setUPassword(new BCryptPasswordEncoder().encode(RO_User.getUPassword()));

		O_UserService.updatePassword(O_User_Detail);
		O_Response.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestBody User RO_User,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		if (RO_User.getUPassword().length() > 14 || RO_User.getUPassword().length() < 4) {

			O_Response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!new BCryptPasswordEncoder().matches(RO_User.getOldPassword(), O_User_Detail.getUPassword())) {

			O_Response.setMessage("Invalid Old Password..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		RO_User.setUId(O_User_Detail.getUId());
		RO_User.setUPassword(new BCryptPasswordEncoder().encode(RO_User.getUPassword()));

		O_UserService.updatePassword(RO_User);
		O_Response.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value ="/logout")
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
