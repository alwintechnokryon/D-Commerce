package com.technokryon.ecommerce.apidoc.controller;

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

import com.technokryon.ecommerce.apidoc.pojo.Module;
import com.technokryon.ecommerce.apidoc.service.ApiDocumentService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/apidoc/module")
public class ApiDocumentController {

	@Autowired
	private ApiDocumentService apiDocumentService;

	@ResponseBody
	@PostMapping("/list")
	private ResponseEntity<?> MODULE_LIST(@RequestBody Module module) {

		List<Module> modules = apiDocumentService.getModuleList(module);

		return new ResponseEntity<Object>(modules, HttpStatus.OK);

	}

	@ResponseBody
	@PostMapping("/detail/id")
	private ResponseEntity<?> DETAIL_BY_ID(@RequestBody Module module) {

		return new ResponseEntity<Object>(apiDocumentService.getDetailById(module), HttpStatus.OK);

	}

}
