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
	private AdminCategoryService O_AdminCategoryService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_CATEGORY(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody Category RO_Category) {

		Response O_Response = new Response();

		Boolean checkCategoryName = O_AdminCategoryService.checkCategoryName(RO_Category.getCCategoryName());

		if (checkCategoryName) {

			O_Response.setMessage("Category Name Already Exist..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		O_AdminCategoryService.addCategory(RO_Category);

		O_Response.setMessage("Success");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		List<Category> LO_Category = O_AdminCategoryService.categoryList();

		return new ResponseEntity<Object>(LO_Category, HttpStatus.OK);

	}

	@PostMapping("/list/id")
	private ResponseEntity<?> LIST_BY_ID(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody Category RO_Category) {

		List<Category> LO_Category = O_AdminCategoryService.categoryListById(RO_Category);

		return new ResponseEntity<Object>(LO_Category, HttpStatus.OK);
	}

	@PostMapping("/update")
	private ResponseEntity<?> UPDATE(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody Category RO_Category) {

		Response O_Response = new Response();

		if (RO_Category.getCCategoryId().isBlank()) {
			O_Response.setMessage("category Id Is Empty");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (RO_Category.getCCategoryName().isBlank()) {

			O_Response.setMessage("category Name Is Empty");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Boolean updateCategory = O_AdminCategoryService.updateCategory(RO_Category);

		if (!updateCategory) {

			O_Response.setMessage("category Id Is Not Matched");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		O_Response.setMessage("category Name Is Changed SuccessFully");
		return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
