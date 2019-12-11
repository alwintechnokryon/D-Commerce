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
import com.technokryon.ecommerce.pojo.PJ_TKECMPRODUCT;
import com.technokryon.ecommerce.service.ProductService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/product")
public class UserProductController {

	@Autowired
	private ProductService O_ProductService;
	
	@ResponseBody
	@PostMapping("/list/category")
	private ResponseEntity<?> LIST_BY_CATEGORY(@RequestBody PJ_TKECMPRODUCT RO_PJ_TKECMPRODUCT){
		
	List<PJ_TKECMPRODUCT> LO_PJ_TKECMPRODUCT =	O_ProductService.getListByCategory(RO_PJ_TKECMPRODUCT.getTkecmpCategoryId(),RO_PJ_TKECMPRODUCT.getPageNumber());
		
	return new ResponseEntity<Object>(LO_PJ_TKECMPRODUCT, HttpStatus.OK);
	}
	
}
