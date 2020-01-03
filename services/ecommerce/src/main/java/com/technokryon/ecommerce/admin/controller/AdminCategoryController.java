package com.technokryon.ecommerce.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.technokryon.ecommerce.admin.service.AdminCategoryService;
import com.technokryon.ecommerce.pojo.Category;
import com.technokryon.ecommerce.pojo.Response;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/category")
public class AdminCategoryController {

	@Autowired
	private AdminCategoryService O_AdminCategoryService;

	@ResponseBody
	@PostMapping("/add")
	private ResponseEntity<?> ADD_CATEGORY(@RequestBody Category RO_Category) {

		Response O_Response = new Response();

		O_AdminCategoryService.addCategory(RO_Category);

		O_Response.setMessage("Success");
		return new ResponseEntity<Object>(O_Response, HttpStatus.OK);

	}

}
