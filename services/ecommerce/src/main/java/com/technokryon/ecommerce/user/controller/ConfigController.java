package com.technokryon.ecommerce.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.technokryon.ecommerce.pojo.ATTRIBUTE;
import com.technokryon.ecommerce.pojo.COUNTRY;
import com.technokryon.ecommerce.pojo.PRODUCTPAYMENTTYPE;
import com.technokryon.ecommerce.pojo.PRODUCTTYPE;
import com.technokryon.ecommerce.pojo.STATE;
import com.technokryon.ecommerce.service.ConfigService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/config")
public class ConfigController {

	@Autowired
	private ConfigService O_ConfigService;

	@GetMapping("/product/type/list")
	private ResponseEntity<?> PRODUCT_TYPE_LIST() {

		List<PRODUCTTYPE> LO_PRODUCTTYPE = O_ConfigService.productTypeList();

		return new ResponseEntity<Object>(LO_PRODUCTTYPE, HttpStatus.OK);
	}

	@GetMapping("/product/payment/type/list")
	private ResponseEntity<?> PRODUCT_PAYMENT_TYPE_LIST() {

		List<PRODUCTPAYMENTTYPE> LO_PRODUCTPAYMENTTYPE = O_ConfigService.productPaymentTypeList();

		return new ResponseEntity<Object>(LO_PRODUCTPAYMENTTYPE, HttpStatus.OK);
	}

	@GetMapping("/country/list")
	private ResponseEntity<?> COUNTRY_LIST() {

		List<COUNTRY> LO_COUNTRY = O_ConfigService.countryList();

		return new ResponseEntity<Object>(LO_COUNTRY, HttpStatus.OK);
	}

	@GetMapping("/state/list/by/id")
	private ResponseEntity<?> STATE_LIST(@RequestParam(name = "sTkecmcnAgId", required = true) Integer sTkecmcnAgId) {

		List<STATE> LO_STATE = O_ConfigService.stateListById(sTkecmcnAgId);

		return new ResponseEntity<Object>(LO_STATE, HttpStatus.OK);
	}

	@GetMapping("/attribute/list")
	private ResponseEntity<?> ATTRIBUTE_LIST() {

		List<ATTRIBUTE> LO_ATTRIBUTE = O_ConfigService.attributeList();

		return new ResponseEntity<Object>(LO_ATTRIBUTE, HttpStatus.OK);
	}
}
