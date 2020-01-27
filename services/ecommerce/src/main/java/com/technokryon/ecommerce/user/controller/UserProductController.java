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
	private ProductService productService;

	@Autowired
	private UserService userService;

	@ResponseBody
	@PostMapping("/list/category")
	private ResponseEntity<?> PRODUCT_LIST_BY_CATEGORY(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody Product product) {

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		List<Product> product1;

		if (userDetail == null) {

			product1 = productService.getListByCategory(product.getPTkecmcCategoryId(),
					product.getPageNumber(), null);
		} else {

			product1 = productService.getListByCategory(product.getPTkecmcCategoryId(),
					product.getPageNumber(), userDetail.getUId());
		}
		return new ResponseEntity<Object>(product1, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/detail/id")
	private ResponseEntity<?> PRODUCT_DETAIL_BY_ID(@RequestHeader(value = "apikey") String apiKey,
			@RequestBody Product product) {

		User userDetail = userService.getUserDetailAPIKey(apiKey);

		Product product1;

		if (userDetail == null) {

			product1 = productService.getDetailById(product.getPId(), null);
		} else {

			product1 = productService.getDetailById(product.getPId(), userDetail.getUId());
		}
		return new ResponseEntity<Object>(product1, HttpStatus.OK);
	}

}
