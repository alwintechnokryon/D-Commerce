package com.technokryon.ecommerce.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.technokryon.ecommerce.admin.pojo.Response;
import com.technokryon.ecommerce.admin.pojo.UserApplyGroup;
import com.technokryon.ecommerce.admin.pojo.UserGroup;
import com.technokryon.ecommerce.admin.service.AdminGroupService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/auth/group")

public class AdminGroupController {

	@Autowired
	private AdminGroupService adminGroupService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserGroup userGroup, HttpServletRequest httpServletRequest) {

		Response response = new Response();

		userGroup.setUgCreatedUserId(httpServletRequest.getAttribute("uId").toString());

		adminGroupService.addGroup(userGroup);

		response.setMessage("Added SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@GetMapping("/list")
	private ResponseEntity<?> LIST_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		return new ResponseEntity<Object>(adminGroupService.groupList(), HttpStatus.OK);

	}

	@PostMapping("/detail/id")
	private ResponseEntity<?> DETAIL_BY_ID(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserGroup userGroup) {

		return new ResponseEntity<Object>(adminGroupService.groupDetailById(userGroup), HttpStatus.OK);

	}

	@PutMapping("/update")
	private ResponseEntity<?> UPDATE_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserGroup userGroup, HttpServletRequest httpServletRequest) {

		Response response = new Response();

		userGroup.setUgModifiedUserId(httpServletRequest.getAttribute("uId").toString());

		adminGroupService.updateGroup(userGroup);

		response.setMessage("Updated SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@PutMapping("/activate")
	private ResponseEntity<?> ACTIVATE_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserGroup userGroup, HttpServletRequest httpServletRequest) {

		Response response = new Response();

		userGroup.setUgModifiedUserId(httpServletRequest.getAttribute("uId").toString());

		adminGroupService.activateGroup(userGroup);

		response.setMessage("Updated SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@PostMapping("/user/apply/add")
	private ResponseEntity<?> ADD_USER_APPLLY_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserApplyGroup userApplyGroup, HttpServletRequest httpServletRequest) {

		Response response = new Response();

		Boolean check = adminGroupService.checkGroupIdExist(userApplyGroup);

		if (check) {

			response.setMessage("Group Already Exist For This User");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		userApplyGroup.setUagCreatedUserId(httpServletRequest.getAttribute("uId").toString());

		adminGroupService.addUserApplyGroup(userApplyGroup);

		response.setMessage("Added SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}

	@GetMapping("/user/apply/list")
	private ResponseEntity<?> LIST_USER_APPLLY_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey) {

		return new ResponseEntity<Object>(adminGroupService.userApplyGroupList(), HttpStatus.OK);

	}

	@DeleteMapping("/user/apply/delete")
	private ResponseEntity<?> DELETE_USER_APPLLY_GROUP(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestBody UserApplyGroup userApplyGroup) {

		Response response = new Response();

		adminGroupService.deleteUserApplyGroup(userApplyGroup);

		response.setMessage("Deleted SuccessFully");
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}