package com.technokryon.ecommerce.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserProfileController {

	@Autowired
	private UserService O_UserService;

	@ResponseBody
	@GetMapping(value = { "/detail" })
	ResponseEntity<?> USER_DETAIL(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

//		if (O_USER_DETAIL == null) {
//
//			O_Response.setMessage("Session Expired..!");
//			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
//		}

		O_User_Detail.setUPassword(null);
		O_User_Detail.setUId(null);

		O_Response.setMessage("Success!");
		return new ResponseEntity<Object>(O_User_Detail, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
