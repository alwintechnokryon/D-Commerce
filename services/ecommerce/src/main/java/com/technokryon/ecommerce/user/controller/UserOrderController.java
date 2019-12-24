package com.technokryon.ecommerce.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technokryon.ecommerce.pojo.ORDER;
import com.technokryon.ecommerce.pojo.RESPONSE;
import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.service.OrderService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/order")
public class UserOrderController {

	@Autowired
	private UserService O_UserService;

	@Autowired
	private OrderService O_OrderService;

	@PostMapping("/order/single/product")
	private ResponseEntity<?> ORDER(@RequestBody ORDER RO_ORDER, @RequestHeader(value = "apiKey") String apiKey) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_ORDER.getBillingAddress() == null) {

			O_RESPONSE.setMessage("Billing Address is Mandatory..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean checkProQuantity = O_OrderService.checkAvailableProductQuantity(RO_ORDER.getProductId(),
				RO_ORDER.getProQuantity());

		if (checkProQuantity == false) {

			O_RESPONSE.setMessage("Out Of Stock..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (RO_ORDER.getOGrandTotal() == null) {

			O_RESPONSE.setMessage("Grand Total Is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_ORDER.getOTaxAmount() == null) {

			O_RESPONSE.setMessage("Tax Amount Is Missing..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		RO_ORDER.setOTkecmuId(O_USER_DETAIL.getUId());
		RO_ORDER.setOCreatedUserId(O_USER_DETAIL.getUId());
		String orderId = O_OrderService.requestOrder(RO_ORDER);

		return new ResponseEntity<Object>(orderId, HttpStatus.OK);
	}
}