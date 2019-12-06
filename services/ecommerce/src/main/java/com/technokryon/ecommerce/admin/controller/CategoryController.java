package com.technokryon.ecommerce.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.pojo.PJ_Response;
import com.technokryon.ecommerce.pojo.PJ_TKECMCATEGORY;
import com.technokryon.ecommerce.service.CategoryService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService O_CategoryService;

	@ResponseBody
	@PostMapping("/add")
	private ResponseEntity<?> ADD_CATEGORY(@RequestBody PJ_TKECMCATEGORY RO_PJ_TKECMCATEGORY) {

		PJ_Response O_PJ_Response = new PJ_Response();

		String addCategory = O_CategoryService.addCategory(RO_PJ_TKECMCATEGORY);

		O_PJ_Response.setMessage("Success");
		return new ResponseEntity<Object>(O_PJ_Response, HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping("/list")
	private ResponseEntity<?> LIST() {

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = O_CategoryService.categoryList();

		return new ResponseEntity<Object>(LO_PJ_TKECMCATEGORY, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/list/id") 
	private ResponseEntity<?> LIST_BY_ID(@RequestBody PJ_TKECMCATEGORY RO_PJ_TKECMCATEGORY){
		

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = O_CategoryService.categoryListById(RO_PJ_TKECMCATEGORY);
		
		return new ResponseEntity<Object>(LO_PJ_TKECMCATEGORY, HttpStatus.OK);	}
}
