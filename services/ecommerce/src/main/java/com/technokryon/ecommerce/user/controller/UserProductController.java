package com.technokryon.ecommerce.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.pojo.Product;
import com.technokryon.ecommerce.service.ProductService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/product")
public class UserProductController {

	@Autowired
	private ProductService O_ProductService;
	
	@ResponseBody
	@PostMapping("/list/category")
	private ResponseEntity<?> PRODUCT_LIST_BY_CATEGORY(@RequestBody Product RO_Product){
		
	List<Product> LO_Product =	O_ProductService.getListByCategory(RO_Product.getPTkecmcCategoryId(),RO_Product.getPageNumber());
		
	return new ResponseEntity<Object>(LO_Product, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@PostMapping("/detail/id")
	private ResponseEntity<?> PRODUCT_DETAIL_BY_ID(@RequestBody Product RO_Product){
		
	Product O_Product = O_ProductService.getDetailById(RO_Product.getPId());
		
	return new ResponseEntity<Object>(O_Product, HttpStatus.OK);
	}
	
}
