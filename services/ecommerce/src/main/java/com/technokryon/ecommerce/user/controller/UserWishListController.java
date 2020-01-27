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
	private UserWishListService userWishListService;

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD(@RequestHeader(value = "apikey") String apiKey, @RequestBody WishList wishList) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		wishList.setWlUserId(userDetail.getUId());

		userWishListService.addWishList(wishList);

		response.setMessage("Product Added To WishList");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST(@RequestHeader(value = "apikey") String apiKey) {

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		List<WishList> wishList = userWishListService.listWishList(userDetail.getUId());

		return new ResponseEntity<Object>(wishList, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody WishList wishList) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		wishList.setWlUserId(userDetail.getUId());

		userWishListService.deleteWishlist(wishList);

		response.setMessage("Deleted SuccessFully");

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

}
