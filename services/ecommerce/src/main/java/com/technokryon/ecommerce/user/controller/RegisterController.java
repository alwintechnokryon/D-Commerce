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
import org.springframework.web.bind.annotation.PutMapping;
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
	private UserService userService;

	@Autowired
	private MailService mailService;

	@ResponseBody
	@PostMapping("/register")

	private ResponseEntity<?> USER_REGISTER(@RequestBody User user) {

		Integer OTP = SingleTon.getRandomUserId();

		Response response = new Response();

		if (user.getURegType() == null) {

			response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (user.getUName() == null || user.getUName().isBlank()) {
			response.setMessage("User Name is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getURegType().equals("E")) {

			// Null-check for user name
			if (user.getUMail() == null || user.getUMail().isBlank()) {

				response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Email Validation
			if (!SingleTon.isEmailValid(user.getUMail())) {

				response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			User userDetail = userService.isUserEmailAvailable(user.getUMail());
			if (userDetail != null) {

				response.setMessage("Email Already Exist..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (user.getUPassword().length() > 14 || user.getUPassword().length() < 4) {

				response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			user.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));

			String userId = userService.createNewUserByEmail(user);

			mailService.sendMail(user.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			User user1 = new User();

			user1.setUHashKey(userService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(user1, HttpStatus.OK);

		} else if (user.getURegType().equals("M")) {

			if (user.getUPhone() == null) {

				response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (user.getUPhoneCode() == null) {

				response.setMessage("Country Code Is Empty..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			User userDetail = userService.isUserPhoneNoAvailable(user.getUPhone());
			if (userDetail != null) {

				response.setMessage("Mobile Number Already Exist..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Password Validation
			if (user.getUPassword().length() > 14 || user.getUPassword().length() < 4) {

				response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			user.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));

			String userId = userService.createNewUserByPhoneNo(user);

			mailService.sendSMS(user.getUPhone(), user.getUPhoneCode(), "Your OTP is " + OTP);

			System.err.println("OTP---->" + OTP);

			User user1 = new User();

			user1.setUHashKey(userService.saveOTPDetails(OTP, userId));

			return new ResponseEntity<Object>(user1, HttpStatus.OK);

		} else {

			response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@ResponseBody
	@PostMapping("/otp/verify")

	private ResponseEntity<?> OTP_VERIFY(@RequestBody User user) {

		Response response = new Response();

		if (user.getUOtp() == 0) {

			response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUHashKey().isBlank()) {

			response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = userService.getUserDetailHash(user);

		if (userDetail == null) {

			response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (userDetail.getUOtpExp().isBefore(OffsetDateTime.now())) {

			response.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUOtp() != userDetail.getUOtp()) {

			response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		userService.changeOTPStatus(userDetail.getUId());

		response.setMessage("Success..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/forgot/otp/verify")

	private ResponseEntity<?> FORGOT_OTP_VERIFY(@RequestBody User user) {

		Response response = new Response();

		if (user.getUOtp() == 0) {

			response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUHashKey().isBlank()) {

			response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = userService.getUserDetailHash(user);

		if (userDetail == null) {

			response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (userDetail.getUOtpExp().isBefore(OffsetDateTime.now())) {

			response.setMessage("OTP Expired..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUOtp() != userDetail.getUOtp()) {

			response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		response.setMessage("Success..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody User user, HttpServletRequest httpServletRequest) {

		Integer OTP = SingleTon.getRandomUserId();

		Response response = new Response();

		if (user.getURegType() == null) {

			response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (user.getURegType().equals("E")) {

			// Null-check for Email Id
			if (user.getUMail() == null || user.getUMail().isBlank()) {

				response.setMessage("Email Id is Empty..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Validation
			if (!SingleTon.isEmailValid(user.getUMail())) {

				response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Email Registered Or Not
			User userDetail = userService.isUserEmailAvailable(user.getUMail());
			if (userDetail == null) {

				response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// OTP STATUS
			if (userDetail.getUOtpStatus().equals("N")) {

				mailService.sendMail(user.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

						"Your OTP is " + OTP);

				User user1 = new User();
				user1.setUHashKey(userService.saveOTPDetails(OTP, userDetail.getUId()));

				return new ResponseEntity<Object>(user1, HttpStatus.OK);

			}

			// Validate STATUS
			if (userDetail.getUStatus().equals("N")) {

				response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(user.getUPassword(), userDetail.getUPassword())) {

				response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			UserSession userSession = userService.getApiSecretDataByNewSecret(apisecret, userDetail.getUId());

			userDetail.setApiKey(apisecret);

			userService.addAuditDetail(userDetail, httpServletRequest);

			return new ResponseEntity<Object>(userSession, HttpStatus.OK);

		} else if (user.getURegType().equals("M")) {

			// Null-check for Phone
			if (user.getUPhone() == null) {

				response.setMessage("Phone Number Is Empty..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// Phone Number Registered Or Not
			User userDetail = userService.isUserPhoneNoAvailable(user.getUPhone());
			if (userDetail == null) {

				response.setMessage("Phone Number Not Registered..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// OTP STATUS
			if (userDetail.getUOtpStatus().equals("N")) {

				mailService.sendSMS(user.getUPhone(), user.getUPhoneCode(), "Your OTP is " + OTP);

				User user1 = new User();

				user1.setUHashKey(userService.saveOTPDetails(OTP, userDetail.getUId()));

				return new ResponseEntity<Object>(user1, HttpStatus.OK);

			} // Validate STATUS
			if (userDetail.getUStatus().equals("N")) {

				response.setMessage("Your Account is Deactivated..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate Password
			if (!new BCryptPasswordEncoder().matches(user.getUPassword(), userDetail.getUPassword())) {

				response.setMessage("Wrong Password..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// API SECRET KEY LOGIC HERE
			String apisecret = new BCryptPasswordEncoder()
					.encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

			UserSession userSession = userService.getApiSecretDataByNewSecret(apisecret, userDetail.getUId());

			userDetail.setApiKey(apisecret);

			userService.addAuditDetail(userDetail, httpServletRequest);

			return new ResponseEntity<Object>(userSession, HttpStatus.OK);
		} else {

			response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@ResponseBody
	@PostMapping("/forgot")
	private ResponseEntity<?> FORGOT(@RequestBody User user) {

		Integer OTP = SingleTon.getRandomUserId();

		Response response = new Response();

		if (user.getURegType() == null) {

			response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (user.getURegType().equals("E")) {

			if (user.getUMail() == null || user.getUMail().isBlank()) {

				response.setMessage("Email Id Is Empty..!");

				return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (!SingleTon.isEmailValid(user.getUMail())) {

				response.setMessage("Invalid Email Id..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			User userDetail = userService.isUserEmailAvailable(user.getUMail());
			if (userDetail == null) {

				response.setMessage("Email Id Not Registered..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			mailService.sendMail(user.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

					"Your OTP is " + OTP);

			User user1 = new User();

			user1.setUHashKey(userService.saveOTPDetails(OTP, userDetail.getUId()));

			return new ResponseEntity<Object>(user1, HttpStatus.OK);

		} else if (user.getURegType().equals("M")) {

			if (user.getUPhone() == null) {

				response.setMessage("Mobile Number Is Empty..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			User userDetail = userService.isUserPhoneNoAvailable(user.getUPhone());
			if (userDetail == null) {

				response.setMessage("Mobile Number Not Registered..!");
				return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			mailService.sendSMS(user.getUPhone(), user.getUPhoneCode(), "Your OTP is " + OTP);

			User user1 = new User();

			user1.setUHashKey(userService.saveOTPDetails(OTP, userDetail.getUId()));

			return new ResponseEntity<Object>(user1, HttpStatus.OK);

		} else {

			response.setMessage("Registration Type is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@ResponseBody
	@PutMapping("/update/password")

	private ResponseEntity<?> UPDATE_PASSWORD(@RequestBody User user) {

		Response response = new Response();

		if (user.getUPassword().length() > 14 || user.getUPassword().length() < 4) {

			response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUHashKey().isBlank()) {

			response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = userService.getUserDetailHash(user);

		if (userDetail == null) {

			response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		userDetail.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));

		userService.updatePassword(userDetail);
		response.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestBody User user,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response response = new Response();

		if (user.getUPassword().length() > 14 || user.getUPassword().length() < 4) {

			response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getOldPassword().isBlank()) {

			response.setMessage("Old Password Is Empty ..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		if (!new BCryptPasswordEncoder().matches(user.getOldPassword(), userDetail.getUPassword())) {

			response.setMessage("Invalid Old Password..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (user.getOldPassword().equals(user.getUPassword())) {

			response.setMessage("New Password Is Same As Above..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		user.setUId(userDetail.getUId());
		user.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));

		userService.updatePassword(user);
		response.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/logout")
	ResponseEntity<?> LOGOUT(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		Response response = new Response();

		Boolean logoutUpdate = userService.userLogout(apiKey);

		if (!logoutUpdate) {

			response.setMessage("Logout Error..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		response.setMessage("success..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
