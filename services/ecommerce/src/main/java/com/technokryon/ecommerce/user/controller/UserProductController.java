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
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.pojo.Product;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.service.ProductService;
import com.technokryon.ecommerce.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/product")
public class UserProductController {

	@Autowired
	private ProductService O_ProductService;

	@Autowired
	private UserService O_UserService;

	@ResponseBody
	@PostMapping("/list/category")
	private ResponseEntity<?> PRODUCT_LIST_BY_CATEGORY(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody Product RO_Product) {

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		List<Product> LO_Product;

		if (O_User_Detail == null) {

			LO_Product = O_ProductService.getListByCategory(RO_Product.getPTkecmcCategoryId(),
					RO_Product.getPageNumber(), null);
		} else {

			LO_Product = O_ProductService.getListByCategory(RO_Product.getPTkecmcCategoryId(),
					RO_Product.getPageNumber(), O_User_Detail.getUId());
		}
		return new ResponseEntity<Object>(LO_Product, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/detail/id")
	private ResponseEntity<?> PRODUCT_DETAIL_BY_ID(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody Product RO_Product) {

		User O_User_Detail = O_UserService.getUserDetailAPIKey(apiKey);

		Product O_Product;

		if (O_User_Detail == null) {

			O_Product = O_ProductService.getDetailById(RO_Product.getPId(), null);
		} else {

			O_Product = O_ProductService.getDetailById(RO_Product.getPId(), O_User_Detail.getUId());
		}
		return new ResponseEntity<Object>(O_Product, HttpStatus.OK);
	}

}
