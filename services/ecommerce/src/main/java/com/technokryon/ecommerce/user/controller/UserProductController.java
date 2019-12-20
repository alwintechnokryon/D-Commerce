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
import com.technokryon.ecommerce.pojo.PRODUCT;
import com.technokryon.ecommerce.service.ProductService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/product")
public class UserProductController {

	@Autowired
	private ProductService O_ProductService;
	
	@ResponseBody
	@PostMapping("/list/category")
	private ResponseEntity<?> PRODUCT_LIST_BY_CATEGORY(@RequestBody PRODUCT RO_PRODUCT){
		
	List<PRODUCT> LO_PRODUCT =	O_ProductService.getListByCategory(RO_PRODUCT.getPCategoryId(),RO_PRODUCT.getPageNumber());
		
	return new ResponseEntity<Object>(LO_PRODUCT, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@PostMapping("/detail/id")
	private ResponseEntity<?> PRODUCT_DETAIL_BY_ID(@RequestBody PRODUCT RO_PRODUCT){
		
	PRODUCT O_PRODUCT = O_ProductService.getDetailById(RO_PRODUCT.getPId());
		
	return new ResponseEntity<Object>(O_PRODUCT, HttpStatus.OK);
	}
	
}
