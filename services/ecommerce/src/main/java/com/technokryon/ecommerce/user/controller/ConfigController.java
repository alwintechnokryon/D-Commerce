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
	private ConfigService configService;

	@GetMapping("/product/type/list")
	private ResponseEntity<?> PRODUCT_TYPE_LIST() {

		List<ProductType> productType = configService.productTypeList();

		return new ResponseEntity<Object>(productType, HttpStatus.OK);
	}

	@GetMapping("/product/payment/type/list")
	private ResponseEntity<?> PRODUCT_PAYMENT_TYPE_LIST() {

		List<ProductPaymentType> productPaymentType = configService.productPaymentTypeList();

		return new ResponseEntity<Object>(productPaymentType, HttpStatus.OK);
	}

	@GetMapping("/country/list")
	private ResponseEntity<?> COUNTRY_LIST() {

		List<Country> country = configService.countryList();

		return new ResponseEntity<Object>(country, HttpStatus.OK);
	}

	@GetMapping("/state/list/by/id")
	private ResponseEntity<?> STATE_LIST(@RequestParam(name = "sTkecmcnAgId", required = true) Integer sTkecmcnAgId) {

		List<State> state = configService.stateListById(sTkecmcnAgId);

		return new ResponseEntity<Object>(state, HttpStatus.OK);
	}

	@GetMapping("/attribute/list")
	private ResponseEntity<?> ATTRIBUTE_LIST() {

		List<Attribute> attribute = configService.attributeList();

		return new ResponseEntity<Object>(attribute, HttpStatus.OK);
	}
}
