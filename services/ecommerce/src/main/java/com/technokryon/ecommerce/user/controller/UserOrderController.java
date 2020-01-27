package com.technokryon.ecommerce.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.OrderItem;
import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.service.OrderService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/order")
public class UserOrderController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@PostMapping("/product")
	private ResponseEntity<?> ORDER_PRODUCT(@RequestBody Order order,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		if (order.getBillingAddress() == null) {

			response.setMessage("Billing Address is Mandatory..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (order.getOTkecmpptId() == null || order.getOTkecmpptId().isBlank()) {

			response.setMessage("Payment Type Is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean checkProQuantity = orderService.checkAvailableProductQuantity(order.getLO_PRODUCT());

		if (!checkProQuantity) {

			response.setMessage("Out Of Stock..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (order.getOGrandTotal() == null) {

			response.setMessage("Grand Total Is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (order.getOTaxAmount() == null) {

			response.setMessage("Tax Amount Is Missing..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		order.setOTkecmuId(userDetail.getUId());
		order.setOCreatedUserId(userDetail.getUId());
		String orderId = orderService.requestOrder(order);

		response.setMessage(orderId);
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@PostMapping("/payment")
	private ResponseEntity<?> ORDER_PAYMENT(@RequestBody Order order,
			@RequestHeader(value = "apiKey") String apiKey) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		if (order.getOId() == null || order.getOId().isBlank()) {

			response.setMessage("Order Id is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		order.setUserId(userDetail.getUId());

		String updateTransactionId = orderService.updateTransactionId(order);

		if (updateTransactionId != null) {

			response.setMessage(updateTransactionId);
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		response.setMessage("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@GetMapping("/list")
	private ResponseEntity<?> ORDER_LIST(@RequestHeader(value = "apiKey") String apiKey) {

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		List<OrderItem> orderItem = orderService.orderList(userDetail.getUId());

		return new ResponseEntity<Object>(orderItem, HttpStatus.OK);

	}

	@GetMapping("/item/by/id")
	private ResponseEntity<?> ORDER_ITEM_BY_ID(@RequestParam(name = "oiAgId", required = true) Integer oiAgId) {

		OrderItem orderItem = orderService.orderItemById(oiAgId);

		return new ResponseEntity<Object>(orderItem, HttpStatus.OK);
	}

	@PostMapping("/cancel")
	private ResponseEntity<?> ORDER_CANCEL(@RequestHeader(value = "apiKey") String apiKey,
			@RequestParam(name = "oshAgId", required = true) Integer oshAgId) {

		Response response = new Response();
		User userDetail = userService.getUserDetailAPIKey(apiKey);

		orderService.orderCancel(oshAgId,userDetail.getUId());
		response.setMessage("Order Canceled Successfully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

}
