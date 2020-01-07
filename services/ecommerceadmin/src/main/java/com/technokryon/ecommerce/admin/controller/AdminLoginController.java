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
	private AdminLoginService O_AdminLoginService;

	@Autowired
	private MailService O_MailService;

	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<?> LOGIN(@RequestBody User RO_User, HttpServletRequest httpServletRequest) {

		Response O_Response = new Response();

		if (RO_User.getUMail() == null) {

			O_Response.setMessage("Email Id is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!SingleTon.isEmailValid(RO_User.getUMail())) {

			O_Response.setMessage("Invalid Email Id..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User O_User_Detail = O_AdminLoginService.isUserEmailAvailable(RO_User.getUMail());
		if (O_User_Detail == null) {

			O_Response.setMessage("Email Id Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean checkRoleByUserId = O_AdminLoginService.checkRoleByUserId(O_User_Detail.getUId());

		if (!checkRoleByUserId) {

			O_Response.setMessage("Unauthorized..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (O_User_Detail.getUStatus().equals("N")) {

			O_Response.setMessage("Your Account is Deactivated..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!new BCryptPasswordEncoder().matches(RO_User.getUPassword(), O_User_Detail.getUPassword())) {

			O_Response.setMessage("Wrong Password..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		String apiSecret = new BCryptPasswordEncoder().encode(String.valueOf(Calendar.getInstance().getTimeInMillis()));

		UserSession O_UserSession = O_AdminLoginService.getApiSecretDataByNewSecret(apiSecret, O_User_Detail.getUId());

		O_User_Detail.setApiKey(apiSecret);

		O_AdminLoginService.addAuditDetail(O_User_Detail, httpServletRequest);

		return new ResponseEntity<Object>(O_UserSession, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/forgot")
	private ResponseEntity<?> FORGOT_PASSWORD(@RequestBody User RO_User) {

		Integer OTP = SingleTon.getRandomUserId();

		Response O_Response = new Response();

		if (RO_User.getUMail() == null || RO_User.getUMail().isEmpty()) {

			O_Response.setMessage("Email Id Is Empty..!");

			return new ResponseEntity<>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!SingleTon.isEmailValid(RO_User.getUMail())) {

			O_Response.setMessage("Invalid Email Id..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		User O_User_Detail = O_AdminLoginService.isUserEmailAvailable(RO_User.getUMail());
		if (O_User_Detail == null) {

			O_Response.setMessage("Email Id Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_MailService.sendMail(RO_User.getUMail(), SingleTon.PASSWORD_RESET_MAIL_HEADER,

				"Your OTP is " + OTP);

		User O_User1 = new User();

		O_User1.setUHashKey(O_AdminLoginService.saveOTPDetails(OTP, O_User_Detail.getUId()));

		return new ResponseEntity<Object>(O_User1, HttpStatus.OK);

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

		User O_User_Detail = O_AdminLoginService.getUserDetailHash(RO_User);

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

		User O_User_Detail = O_AdminLoginService.getUserDetailHash(RO_User);

		if (O_User_Detail == null) {

			O_Response.setMessage("Not Registered..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		O_User_Detail.setUPassword(new BCryptPasswordEncoder().encode(RO_User.getUPassword()));

		O_AdminLoginService.updatePassword(O_User_Detail);
		O_Response.setMessage("Password Updated Successfully..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/change/password")
	private ResponseEntity<?> CHANGE_PASSWORD(@RequestBody User RO_User,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response O_Response = new Response();

		User O_User_Detail = O_AdminLoginService.getUserDetailAPIKey(apiKey);

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

		O_AdminLoginService.updatePassword(RO_User);
		O_Response.setMessage("Password Changed Successfully..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/logout")
	ResponseEntity<?> LOGOUT(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		Response O_Response = new Response();

		Boolean logoutUpdate = O_AdminLoginService.userLogout(apiKey);

		if (!logoutUpdate) {

			O_Response.setMessage("Logout Error..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_Response.setMessage("success..!");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

}
