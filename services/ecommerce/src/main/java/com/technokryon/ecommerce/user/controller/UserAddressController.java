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

import com.technokryon.ecommerce.pojo.RESPONSE;
import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.pojo.USERADDRESS;
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
			@RequestBody USERADDRESS RO_USERADDRESS) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (RO_USERADDRESS.getUadName() == null || RO_USERADDRESS.getUadName().trim().equals("")) {

			O_RESPONSE.setMessage("Name is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadPhone() == null) {

			O_RESPONSE.setMessage("Phone Number Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadPostalCode() == null || RO_USERADDRESS.getUadPostalCode().trim().equals("")) {

			O_RESPONSE.setMessage("PostalCode Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadAddress() == null || RO_USERADDRESS.getUadAddress().trim().equals("")) {

			O_RESPONSE.setMessage("Address Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadCity() == null || RO_USERADDRESS.getUadCity().trim().equals("")) {

			O_RESPONSE.setMessage("City Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadTkectsAgId() == null) {

			O_RESPONSE.setMessage("State Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadAddressType() == null || RO_USERADDRESS.getUadAddressType().trim().equals("")) {

			O_RESPONSE.setMessage("Address Type Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (!RO_USERADDRESS.getUadAddressType().equals("Home") && !RO_USERADDRESS.getUadAddressType().equals("Work")) {

			O_RESPONSE.setMessage("Address Type Is MisMatch..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		RO_USERADDRESS.setUadTkecmuId(O_USER_DETAIL.getUId());

		O_UserAddressService.addUserAddress(RO_USERADDRESS);

		O_RESPONSE.setMessage("Address Added SuccessFully");
		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);

	}

	@PostMapping("/list")
	private ResponseEntity<?> LIST_ADDRESS(@RequestHeader(value = "apikey") String apiKey) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		List<USERADDRESS> LO_USERADDRESS = O_UserAddressService.listUserAddress(O_USER_DETAIL.getUId());

		return new ResponseEntity<Object>(LO_USERADDRESS, HttpStatus.OK);

	}

	@PutMapping("/update")
	private ResponseEntity<?> UPDATE_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody USERADDRESS RO_USERADDRESS) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (RO_USERADDRESS.getUadAgId() == null) {

			O_RESPONSE.setMessage("Address Id Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadPhone() == null) {

			O_RESPONSE.setMessage("Phone Number Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadPostalCode() == null || RO_USERADDRESS.getUadPostalCode().trim().equals("")) {

			O_RESPONSE.setMessage("PostalCode Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadAddress() == null || RO_USERADDRESS.getUadAddress().trim().equals("")) {

			O_RESPONSE.setMessage("Address Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadCity() == null || RO_USERADDRESS.getUadCity().trim().equals("")) {

			O_RESPONSE.setMessage("City Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadTkectsAgId() == null) {

			O_RESPONSE.setMessage("State Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		if (RO_USERADDRESS.getUadAddressType() == null || RO_USERADDRESS.getUadAddressType().trim().equals("")) {

			O_RESPONSE.setMessage("Address Type Is Empty..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (!RO_USERADDRESS.getUadAddressType().equals("Home") && !RO_USERADDRESS.getUadAddressType().equals("Work")) {

			O_RESPONSE.setMessage("Address Type Is MisMatch..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		RO_USERADDRESS.setUadTkecmuId(O_USER_DETAIL.getUId());

		O_UserAddressService.updateUserAddress(RO_USERADDRESS);

		O_RESPONSE.setMessage("Address Updated SuccessFully");

		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);

	}

	@PostMapping("/delete")
	private ResponseEntity<?> DELETE_ADDRESS(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody USERADDRESS RO_USERADDRESS) {

		RESPONSE O_RESPONSE = new RESPONSE();

		USER O_USER_DETAIL = O_UserService.getUserDetailAPIKey(apiKey);

		if (O_USER_DETAIL == null) {

			O_RESPONSE.setMessage("Session Expired..!");
			return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		RO_USERADDRESS.setUadTkecmuId(O_USER_DETAIL.getUId());

		O_UserAddressService.deleteUserAddress(RO_USERADDRESS);

		O_RESPONSE.setMessage("Address Deleted SuccessFully");

		return new ResponseEntity<Object>(O_RESPONSE, HttpStatus.OK);

	}

}
