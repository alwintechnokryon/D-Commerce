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

import com.technokryon.ecommerce.pojo.Category;
import com.technokryon.ecommerce.pojo.Response;
import com.technokryon.ecommerce.service.CategoryService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/category")
public class CategoryController {

	@Autowired
	private CategoryService O_CategoryService;

	@ResponseBody
	@PostMapping("/add")
	private ResponseEntity<?> ADD_CATEGORY(@RequestBody Category RO_Category) {

		Response O_Response = new Response();

		O_CategoryService.addCategory(RO_Category);

		O_Response.setMessage("Success");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping("/list")
	private ResponseEntity<?> LIST() {

		List<Category> LO_Category = O_CategoryService.categoryList();

		return new ResponseEntity<Object>(LO_Category, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/list/id") 
	private ResponseEntity<?> LIST_BY_ID(@RequestBody Category RO_Category){
		

		List<Category> LO_Category = O_CategoryService.categoryListById(RO_Category);
		
		return new ResponseEntity<Object>(LO_Category, HttpStatus.OK);	}
}
