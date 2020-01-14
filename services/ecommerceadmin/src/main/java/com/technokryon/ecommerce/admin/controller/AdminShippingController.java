package com.technokryon.ecommerce.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.admin.pojo.Response;
import com.technokryon.ecommerce.admin.pojo.ShippingCost;
import com.technokryon.ecommerce.admin.service.AdminShippingService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/auth/shipping")
public class AdminShippingController {

	@Autowired
	private AdminShippingService O_AdminShippingService;

	@ResponseBody
	@PostMapping("/add/cost")
	private ResponseEntity<?> ADD_COST(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody ShippingCost RO_ShippingCost) {

		Response O_Response = new Response();

		O_AdminShippingService.addShippingCost(RO_ShippingCost);
		O_Response.setMessage("Shipping Cost Added SuccessFully");
		return new ResponseEntity<>(O_Response, HttpStatus.OK);

	}
}