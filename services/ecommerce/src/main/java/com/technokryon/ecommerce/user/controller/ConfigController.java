package com.technokryon.ecommerce.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.technokryon.ecommerce.pojo.Attribute;
import com.technokryon.ecommerce.pojo.Country;
import com.technokryon.ecommerce.pojo.ProductPaymentType;
import com.technokryon.ecommerce.pojo.ProductType;
import com.technokryon.ecommerce.pojo.State;
import com.technokryon.ecommerce.service.ConfigService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/config")
public class ConfigController {

	@Autowired
	private ConfigService O_ConfigService;

	@GetMapping("/product/type/list")
	private ResponseEntity<?> PRODUCT_TYPE_LIST() {

		List<ProductType> LO_ProductType = O_ConfigService.productTypeList();

		return new ResponseEntity<Object>(LO_ProductType, HttpStatus.OK);
	}

	@GetMapping("/product/payment/type/list")
	private ResponseEntity<?> PRODUCT_PAYMENT_TYPE_LIST() {

		List<ProductPaymentType> LO_ProductPaymentType = O_ConfigService.productPaymentTypeList();

		return new ResponseEntity<Object>(LO_ProductPaymentType, HttpStatus.OK);
	}

	@GetMapping("/country/list")
	private ResponseEntity<?> COUNTRY_LIST() {

		List<Country> LO_Country = O_ConfigService.countryList();

		return new ResponseEntity<Object>(LO_Country, HttpStatus.OK);
	}

	@GetMapping("/state/list/by/id")
	private ResponseEntity<?> STATE_LIST(@RequestParam(name = "sTkecmcnAgId", required = true) Integer sTkecmcnAgId) {

		List<State> LO_State = O_ConfigService.stateListById(sTkecmcnAgId);

		return new ResponseEntity<Object>(LO_State, HttpStatus.OK);
	}

	@GetMapping("/attribute/list")
	private ResponseEntity<?> ATTRIBUTE_LIST() {

		List<Attribute> LO_Attribute = O_ConfigService.attributeList();

		return new ResponseEntity<Object>(LO_Attribute, HttpStatus.OK);
	}
}
