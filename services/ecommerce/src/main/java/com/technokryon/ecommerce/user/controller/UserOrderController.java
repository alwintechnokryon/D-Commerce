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

import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
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

	@PostMapping("/product")
	private ResponseEntity<?> ORDER_PRODUCT(@RequestBody Order RO_Order,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		if (RO_Order.getBillingAddress() == null) {

			O_Response.setMessage("Billing Address is Mandatory..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (RO_Order.getOTkecmpptId() == null || RO_Order.getOTkecmpptId().trim().equals("")) {

			O_Response.setMessage("Payment Type Is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean checkProQuantity = O_OrderService.checkAvailableProductQuantity(RO_Order.getLO_PRODUCT());

		if (!checkProQuantity) {

			O_Response.setMessage("Out Of Stock..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (RO_Order.getOGrandTotal() == null) {

			O_Response.setMessage("Grand Total Is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_Order.getOTaxAmount() == null) {

			O_Response.setMessage("Tax Amount Is Missing..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		RO_Order.setOTkecmuId(O_User_Detail.getUId());
		RO_Order.setOCreatedUserId(O_User_Detail.getUId());
		String orderId = O_OrderService.requestOrder(RO_Order);

		O_Response.setMessage(orderId);
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@PostMapping("/payment")
	private ResponseEntity<?> ORDER_PAYMENT(@RequestBody Order RO_Order,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		if (RO_Order.getOId() == null || RO_Order.getOId().trim().equals("")) {

			O_Response.setMessage("Order Id is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		RO_Order.setUserId(O_User_Detail.getUId());

		String updateTransactionId = O_OrderService.updateTransactionId(RO_Order);

		if (updateTransactionId != null) {

			O_Response.setMessage(updateTransactionId);
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		O_Response.setMessage("Success");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}
}
