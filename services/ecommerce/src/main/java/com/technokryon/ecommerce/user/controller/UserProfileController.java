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

import com.technokryon.ecommerce.pojo.PJ_Response;
import com.technokryon.ecommerce.pojo.PJ_TKECMUSER;
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

		PJ_Response O_PJ_Response = new PJ_Response();

		PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_PJ_TKECMUSER_DETAIL == null) {

			O_PJ_Response.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_PJ_TKECMUSER_DETAIL.setTkecmuPassword(null);
		O_PJ_TKECMUSER_DETAIL.setTkecmuId(null);

		O_PJ_Response.setMessage("Success!");
		return new ResponseEntity<Object>(O_PJ_TKECMUSER_DETAIL, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
