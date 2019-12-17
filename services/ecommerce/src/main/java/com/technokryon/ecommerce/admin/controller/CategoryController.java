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

import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.pojo.CATEGORY;
import com.technokryon.ecommerce.service.CategoryService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/category")
public class CategoryController {

	@Autowired
	private CategoryService O_CategoryService;

	@ResponseBody
	@PostMapping("/add")
	private ResponseEntity<?> ADD_CATEGORY(@RequestBody CATEGORY RO_CATEGORY) {

		Response O_Response = new Response();

		String addCategory = O_CategoryService.addCategory(RO_CATEGORY);

		O_Response.setMessage("Success");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping("/list")
	private ResponseEntity<?> LIST() {

		List<CATEGORY> LO_CATEGORY = O_CategoryService.categoryList();

		return new ResponseEntity<Object>(LO_CATEGORY, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/list/id") 
	private ResponseEntity<?> LIST_BY_ID(@RequestBody CATEGORY RO_CATEGORY){
		

		List<CATEGORY> LO_CATEGORY = O_CategoryService.categoryListById(RO_CATEGORY);
		
		return new ResponseEntity<Object>(LO_CATEGORY, HttpStatus.OK);	}
}
