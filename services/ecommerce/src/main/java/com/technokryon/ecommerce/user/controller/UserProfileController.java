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
import com.technokryon.ecommerce.pojo.USER;
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

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_Response.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_USER_DETAIL.setPassword(null);
		O_USER_DETAIL.setId(null);;

		O_Response.setMessage("Success!");
		return new ResponseEntity<Object>(O_USER_DETAIL, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
