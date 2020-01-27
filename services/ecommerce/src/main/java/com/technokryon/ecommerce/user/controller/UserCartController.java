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

import com.technokryon.ecommerce.pojo.ProductCart;
import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.service.UserCartService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/cart")
public class UserCartController {

	@Autowired
	private UserCartService userCartService;

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart productCart) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		productCart.setPcTkecmuId(userDetail.getUId());

		userCartService.addToCart(productCart);

		response.setMessage("Product Added To Cart SuccessFully..");

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST_CART(@RequestHeader(value = "apikey") String apiKey) {

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		List<ProductCart> productCart = userCartService.listCart(userDetail.getUId());

		return new ResponseEntity<Object>(productCart, HttpStatus.OK);

	}

	@PostMapping("/add/quantity")
	private ResponseEntity<?> ADD_QUANTITY(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart productCart) {

		Response response = new Response();

		// User O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		Integer totalQuantity = userCartService.checkTotalQuantity(productCart.getPcTkecmpId());

		Boolean addQuantity = userCartService.addQuantity(productCart);

		if (!addQuantity) {

			response.setMessage("Only " + totalQuantity + " Product Available");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		} else {

			response.setMessage("Quantity Added SuccessFully");
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/save/later")
	private ResponseEntity<?> UPDATE_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart productCart) {

		Response response = new Response();

//		User O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		userCartService.saveLater(productCart);

		response.setMessage("SuccessFully Product Save For Later ");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart productCart) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		productCart.setPcTkecmuId(userDetail.getUId());

		userCartService.deleteCart(productCart);

		response.setMessage("Cart Deleted SuccessFully");

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

}
