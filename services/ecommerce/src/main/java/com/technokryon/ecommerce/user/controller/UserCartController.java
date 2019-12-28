package com.technokryon.ecommerce.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technokryon.ecommerce.pojo.PRODUCTCART;
import com.technokryon.ecommerce.pojo.RESPONSE;
import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.service.UserCartService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/cart")
public class UserCartController {

	@Autowired
	private UserCartService O_UserCartService;

	@Autowired
	private UserService O_UserService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody PRODUCTCART RO_PRODUCTCART) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		RO_PRODUCTCART.setPcTkecmuId(O_USER_DETAIL.getUId());

		O_UserCartService.addToCart(RO_PRODUCTCART);

		O_RESPONSE.setMessage("Product Added To Cart SuccessFully..");

		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);
	}

	@PostMapping("/list")
	private ResponseEntity<?> LIST_CART(@RequestHeader(value = "apikey") String apiKey) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		List<PRODUCTCART> LO_PRODUCTCART = O_UserCartService.listCart(O_USER_DETAIL.getUId());

		return new ResponseEntity<Object>(LO_PRODUCTCART, HttpStatus.OK);

	}

	@PostMapping("/add/quantity")
	private ResponseEntity<?> ADD_QUANTITY(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody PRODUCTCART RO_PRODUCTCART) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Integer totalQuantity = O_UserCartService.checkTotalQuantity(RO_PRODUCTCART.getPcTkecmpId());

		Boolean addQuantity = O_UserCartService.addQuantity(RO_PRODUCTCART);

		if (!addQuantity) {

			O_RESPONSE.setMessage(totalQuantity + " Quantity Only Available In this Product");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		} else {

			O_RESPONSE.setMessage("Quantity Added SuccessFully");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);
		}
	}

	@PostMapping("/save/later")
	private ResponseEntity<?> UPDATE_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody PRODUCTCART RO_PRODUCTCART) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		O_UserCartService.saveLater(RO_PRODUCTCART);

		O_RESPONSE.setMessage("SuccessFully Product Save For Later ");
		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody PRODUCTCART RO_PRODUCTCART) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		RO_PRODUCTCART.setPcTkecmuId(O_USER_DETAIL.getUId());

		O_UserCartService.deleteCart(RO_PRODUCTCART);

		O_RESPONSE.setMessage("Cart Deleted SuccessFully");

		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);

	}

}
