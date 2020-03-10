package com.technokryon.ecommerce.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technokryon.ecommerce.admin.pojo.Category;
import com.technokryon.ecommerce.admin.pojo.Response;
import com.technokryon.ecommerce.admin.service.AdminCategoryService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/auth/category")
public class AdminCategoryController {

	@Autowired
	private AdminCategoryService adminCategoryService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_CATEGORY(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody Category category, HttpServletRequest httpServletRequest) {

		Response response = new Response();

		Boolean checkCategoryName = adminCategoryService.checkCategoryName(category.getCCategoryName());

		if (checkCategoryName) {

			response.setMessage("Category Name Already Exist..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		category.setCCreatedUserId(httpServletRequest.getAttribute("uId").toString());

		adminCategoryService.addCategory(category);

		response.setMessage("Success");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		List<Category> category = adminCategoryService.categoryList();

		return new ResponseEntity<Object>(category, HttpStatus.OK);

	}

	@PostMapping("/list/id")
	private ResponseEntity<?> LIST_BY_ID(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody Category category) {

		List<Category> category1 = adminCategoryService.categoryListById(category);

		return new ResponseEntity<Object>(category1, HttpStatus.OK);
	}

	@PutMapping("/update")
	private ResponseEntity<?> UPDATE(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody Category category) {

		Response response = new Response();

		if (category.getCCategoryId().trim().isEmpty()) {
			response.setMessage("category Id Is Empty");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (category.getCCategoryName().trim().isEmpty()) {

			response.setMessage("category Name Is Empty");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean updateCategory = adminCategoryService.updateCategory(category);

		if (!updateCategory) {

			response.setMessage("category Id Is Not Matched");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		response.setMessage("category Name Is Changed SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
