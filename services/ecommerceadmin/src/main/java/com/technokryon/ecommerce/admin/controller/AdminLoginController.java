package com.technokryon.ecommerce.admin.controller;

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
import com.technokryon.ecommerce.admin.pojo.Response;
import com.technokryon.ecommerce.admin.pojo.User;
import com.technokryon.ecommerce.admin.pojo.UserSession;
import com.technokryon.ecommerce.admin.service.AdminLoginService;
import com.technokryon.ecommerce.admin.service.MailService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminLoginController {

	@Autowired
	private AdminLoginService adminLoginService;

	@Autowired
	private MailService mailService;

	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody User user, HttpServletRequest httpServletRequest) {

		Response response = new Response();

		if (user.getUMail() == null) {

			response.setMessage("Email Id is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!SingleTon.isEmailValid(user.getUMail())) {

			response.setMessage("Invalid Email Id..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = adminLoginService.isUserEmailAvailable(user.getUMail());
		if (userDetail == null) {

			response.setMessage("Email Id Not Registered..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean checkRoleByUserId = adminLoginService.checkRoleByUserId(userDetail.getUId());

		if (!checkRoleByUserId) {

			response.setMessage("Unauthorized..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (userDetail.getUStatus().equals("N")) {

			response.setMessage("Your Account is Deactivated..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!new BCryptPasswordEncoder().matches(user.getUPassword(), userDetail.getUPassword())) {

			response.setMessage("Wrong Password..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		String apiSecret = new BCryptPasswordEncoder().encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

		UserSession userSession = adminLoginService.getApiSecretDataByNewSecret(apiSecret, userDetail.getUId());

		userDetail.setApiKey(apiSecret);

		adminLoginService.addAuditDetail(userDetail, httpServletRequest);

		return new ResponseEntity<Object>(userSession, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/forgot")
	private ResponseEntity<?> FORGOT_PASSWORD(@RequestBody User user) {

		Integer OTP = SingleTon.getRandomUserId();

		Response response = new Response();

		if (user.getUMail() == null || user.getUMail().trim().isEmpty()) {

			response.setMessage("Email Id Is Empty..!");

			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!SingleTon.isEmailValid(user.getUMail())) {

			response.setMessage("Invalid Email Id..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		User userDetail = adminLoginService.isUserEmailAvailable(user.getUMail());
		if (userDetail == null) {

			response.setMessage("Email Id Not Registered..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		mailService.sendMail(user.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

				"Your OTP is " + OTP);

		User user1 = new User();

		user1.setUHashKey(adminLoginService.saveOTPDetails(OTP, userDetail.getUId()));

		return new ResponseEntity<Object>(user1, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/forgot/otp/verify")

	private ResponseEntity<?> FORGOT_OTP_VERIFY(@RequestBody User user) {

		Response response = new Response();

		if (user.getUOtp() == 0) {

			response.setMessage("Invalid OTP..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUHashKey().trim().isEmpty()) {

			response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = adminLoginService.getUserDetailHash(user);

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
	@PostMapping("/update/password")

	private ResponseEntity<?> UPDATE_PASSWORD(@RequestBody User user) {

		Response response = new Response();

		if (user.getUPassword().length() > 14 || user.getUPassword().length() < 4) {

			response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (user.getUHashKey().trim().isEmpty()) {

			response.setMessage("Hash Key Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User userDetail = adminLoginService.getUserDetailHash(user);

		if (userDetail == null) {

			response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		userDetail.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));

		adminLoginService.updatePassword(userDetail);
		response.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody User user) {

		Response response = new Response();

		User userDetail = adminLoginService.getUserDetailAPIKey(apiKey);

		if (user.getUPassword().length() > 14 || user.getUPassword().length() < 4) {

			response.setMessage("Password Shoud be 4 to 14 Charaters ..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (!new BCryptPasswordEncoder().matches(user.getOldPassword(), userDetail.getUPassword())) {

			response.setMessage("Invalid Old Password..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (user.getOldPassword().equals(user.getUPassword())) {

			response.setMessage("New Password Is Same As Old Password..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		user.setUId(userDetail.getUId());
		user.setUPassword(new BCryptPasswordEncoder().encode(user.getUPassword()));

		adminLoginService.updatePassword(user);
		response.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/logout")
	ResponseEntity<?> LOGOUT(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		Response response = new Response();

		Boolean logoutUpdate = adminLoginService.userLogout(apiKey);

		if (!logoutUpdate) {

			response.setMessage("Logout Error..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		response.setMessage("success..!");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
