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
	private UserCartService O_UserCartService;

	@Autowired
	private UserService O_UserService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart RO_ProductCart) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		RO_ProductCart.setPcTkecmuId(O_User_Detail.getUId());

		O_UserCartService.addToCart(RO_ProductCart);

		O_Response.setMessage("Product Added To Cart SuccessFully..");

		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST_CART(@RequestHeader(value = "apikey") String apiKey) {

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		List<ProductCart> LO_ProductCart = O_UserCartService.listCart(O_User_Detail.getUId());

		return new ResponseEntity<Object>(LO_ProductCart, HttpStatus.OK);

	}

	@PostMapping("/add/quantity")
	private ResponseEntity<?> ADD_QUANTITY(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart RO_ProductCart) {

		Response O_Response = new Response();

		// User O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		Integer totalQuantity = O_UserCartService.checkTotalQuantity(RO_ProductCart.getPcTkecmpId());

		Boolean addQuantity = O_UserCartService.addQuantity(RO_ProductCart);

		if (!addQuantity) {

			O_Response.setMessage("Only " + totalQuantity + " Product Available");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		} else {

			O_Response.setMessage("Quantity Added SuccessFully");
			return new ResponseEntity<Object>(O_Response, HttpStatus.OK);
		}
	}

	@PostMapping("/save/later")
	private ResponseEntity<?> UPDATE_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart RO_ProductCart) {

		Response O_Response = new Response();

//		User O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		O_UserCartService.saveLater(RO_ProductCart);

		O_Response.setMessage("SuccessFully Product Save For Later ");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE_CART(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody ProductCart RO_ProductCart) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		RO_ProductCart.setPcTkecmuId(O_User_Detail.getUId());

		O_UserCartService.deleteCart(RO_ProductCart);

		O_Response.setMessage("Cart Deleted SuccessFully");

		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

}
