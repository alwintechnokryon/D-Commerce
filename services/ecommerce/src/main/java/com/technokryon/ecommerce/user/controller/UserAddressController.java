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
	private UserAddressService O_UserAddressService;

	@Autowired
	private UserService O_UserService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody UserAddress RO_UserAddress) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		if (RO_UserAddress.getUadName() == null || RO_UserAddress.getUadName().isEmpty()) {

			O_Response.setMessage("Name is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadPhone() == null) {

			O_Response.setMessage("Phone Number Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadPostalCode() == null || RO_UserAddress.getUadPostalCode().isEmpty()) {

			O_Response.setMessage("PostalCode Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadAddress() == null || RO_UserAddress.getUadAddress().isEmpty()) {

			O_Response.setMessage("Address Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadCity() == null || RO_UserAddress.getUadCity().isEmpty()) {

			O_Response.setMessage("City Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadTkectsAgId() == null) {

			O_Response.setMessage("State Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadAddressType() == null || RO_UserAddress.getUadAddressType().isEmpty()) {

			O_Response.setMessage("Address Type Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (!RO_UserAddress.getUadAddressType().equals("Home") && !RO_UserAddress.getUadAddressType().equals("Work")) {

			O_Response.setMessage("Address Type Is MisMatch..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		RO_UserAddress.setUadTkecmuId(O_User_Detail.getUId());

		O_UserAddressService.addUserAddress(RO_UserAddress);

		O_Response.setMessage("Address Added SuccessFully");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@PostMapping("/list")
	private ResponseEntity<?> LIST_ADDRESS(@RequestHeader(value = "apikey") String apiKey) {

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		List<UserAddress> LO_UserAddress = O_UserAddressService.listUserAddress(O_User_Detail.getUId());

		return new ResponseEntity<Object>(LO_UserAddress, HttpStatus.OK);

	}

	@PutMapping("/update")
	private ResponseEntity<?> UPDATE_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody UserAddress RO_UserAddress) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		if (RO_UserAddress.getUadAgId() == null) {

			O_Response.setMessage("Address Id Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadPhone() == null) {

			O_Response.setMessage("Phone Number Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadPostalCode() == null || RO_UserAddress.getUadPostalCode().isEmpty()) {

			O_Response.setMessage("PostalCode Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadAddress() == null || RO_UserAddress.getUadAddress().isEmpty()) {

			O_Response.setMessage("Address Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadCity() == null || RO_UserAddress.getUadCity().isEmpty()) {

			O_Response.setMessage("City Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadTkectsAgId() == null) {

			O_Response.setMessage("State Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_UserAddress.getUadAddressType() == null || RO_UserAddress.getUadAddressType().isEmpty()) {

			O_Response.setMessage("Address Type Is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (!RO_UserAddress.getUadAddressType().equals("Home") && !RO_UserAddress.getUadAddressType().equals("Work")) {

			O_Response.setMessage("Address Type Is MisMatch..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		RO_UserAddress.setUadTkecmuId(O_User_Detail.getUId());

		O_UserAddressService.updateUserAddress(RO_UserAddress);

		O_Response.setMessage("Address Updated SuccessFully");

		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody UserAddress RO_UserAddress) {

		Response O_Response = new Response();

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		RO_UserAddress.setUadTkecmuId(O_User_Detail.getUId());

		O_UserAddressService.deleteUserAddress(RO_UserAddress);

		O_Response.setMessage("Address Deleted SuccessFully");

		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

}
