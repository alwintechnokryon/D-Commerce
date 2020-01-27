package com.technokryon.ecommerce.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserAddress;
import com.technokryon.ecommerce.service.UserAddressService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/address")
public class UserAddressController {

	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_ADDRESS(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserAddress userAddress) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		if (userAddress.getUadName() == null || userAddress.getUadName().isBlank()) {

			response.setMessage("Name is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadPhone() == null) {

			response.setMessage("Phone Number Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadPostalCode() == null || userAddress.getUadPostalCode().isBlank()) {

			response.setMessage("PostalCode Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadAddress() == null || userAddress.getUadAddress().isBlank()) {

			response.setMessage("Address Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadCity() == null || userAddress.getUadCity().isBlank()) {

			response.setMessage("City Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadTkectsAgId() == null) {

			response.setMessage("State Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadAddressType() == null || userAddress.getUadAddressType().isBlank()) {

			response.setMessage("Address Type Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (!userAddress.getUadAddressType().equals("Home") && !userAddress.getUadAddressType().equals("Work")) {

			response.setMessage("Address Type Is MisMatch..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		userAddress.setUadTkecmuId(userDetail.getUId());

		userAddressService.addUserAddress(userAddress);

		response.setMessage("Address Added SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST_ADDRESS(@RequestHeader(value = "apikey") String apiKey) {

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		List<UserAddress> userAddress = userAddressService.listUserAddress(userDetail.getUId());

		return new ResponseEntity<Object>(userAddress, HttpStatus.OK);

	}

	@PutMapping("/update")
	private ResponseEntity<?> UPDATE_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody UserAddress userAddress) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		if (userAddress.getUadAgId() == null) {

			response.setMessage("Address Id Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadPhone() == null) {

			response.setMessage("Phone Number Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadPostalCode() == null || userAddress.getUadPostalCode().isBlank()) {

			response.setMessage("PostalCode Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadAddress() == null || userAddress.getUadAddress().isBlank()) {

			response.setMessage("Address Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadCity() == null || userAddress.getUadCity().isBlank()) {

			response.setMessage("City Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadTkectsAgId() == null) {

			response.setMessage("State Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (userAddress.getUadAddressType() == null || userAddress.getUadAddressType().isBlank()) {

			response.setMessage("Address Type Is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (!userAddress.getUadAddressType().equals("Home") && !userAddress.getUadAddressType().equals("Work")) {

			response.setMessage("Address Type Is MisMatch..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		userAddress.setUadTkecmuId(userDetail.getUId());

		userAddressService.updateUserAddress(userAddress);

		response.setMessage("Address Updated SuccessFully");

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody UserAddress userAddress) {

		Response response = new Response();

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		userAddress.setUadTkecmuId(userDetail.getUId());

		userAddressService.deleteUserAddress(userAddress);

		response.setMessage("Address Deleted SuccessFully");

		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

}
