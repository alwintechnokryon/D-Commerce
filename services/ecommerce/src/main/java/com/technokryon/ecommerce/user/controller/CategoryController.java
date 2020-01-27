package com.technokryon.ecommerce.user.controller;

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
import com.technokryon.ecommerce.service.CategoryService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@ResponseBody
	@GetMapping("/list")
	private ResponseEntity<?> LIST() {

		List<Category> category = categoryService.categoryList();

		return new ResponseEntity<Object>(category, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/list/id") 
	private ResponseEntity<?> LIST_BY_ID(@RequestBody Category category){
		

		List<Category> category1 = categoryService.categoryListById(category);
		
		return new ResponseEntity<Object>(category1, HttpStatus.OK);	}
}
