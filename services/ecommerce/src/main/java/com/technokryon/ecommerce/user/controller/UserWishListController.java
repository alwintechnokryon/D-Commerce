package com.technokryon.ecommerce.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technokryon.ecommerce.pojo.ProductCart;
import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.WishList;
import com.technokryon.ecommerce.service.UserService;
import com.technokryon.ecommerce.service.UserWishListService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/wishlist")
public class UserWishListController {

	@Autowired
	private UserWishListService O_UserWishListService;

	@Autowired
	private UserService O_UserService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD(@RequestHeader(value = "apikey") String apiKey, @RequestBody WishList RO_WishList) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		RO_WishList.setWlUserId(O_User_Detail.getUId());

		O_UserWishListService.addWishList(RO_WishList);

		O_Response.setMessage("Product Added To WishList");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@PostMapping("/list")
	private ResponseEntity<?> LIST(@RequestHeader(value = "apikey") String apiKey) {

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		List<WishList> LO_WishList = O_UserWishListService.listWishList(O_User_Detail.getUId());

		return new ResponseEntity<Object>(LO_WishList, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody WishList RO_WishList) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		RO_WishList.setWlUserId(O_User_Detail.getUId());

		O_UserWishListService.deleteWishlist(RO_WishList);

		O_Response.setMessage("Deleted SuccessFully");

		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

}
