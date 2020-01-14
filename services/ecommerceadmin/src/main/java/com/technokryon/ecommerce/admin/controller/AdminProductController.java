package com.technokryon.ecommerce.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.technokryon.ecommerce.SingleTon;
import com.technokryon.ecommerce.admin.pojo.Image;
import com.technokryon.ecommerce.admin.pojo.Product;
import com.technokryon.ecommerce.admin.pojo.Response;
import com.technokryon.ecommerce.admin.pojo.User;
import com.technokryon.ecommerce.admin.service.AdminLoginService;
import com.technokryon.ecommerce.admin.service.AdminProductService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/admin/auth/product")

public class AdminProductController {

	@Autowired
	private AdminProductService O_AdminProductService;

	@Autowired
	private AdminLoginService O_AdminLoginService;

	@PostMapping("/add")
	private ResponseEntity<?> ADD_PRODUCT(@RequestHeader(value = "X-Auth-Token") String apiKey,
			@RequestParam(value = "pName", required = true) String productName,
			@RequestParam(value = "pSku", required = true) String sku,
			@RequestParam(value = "pTkecmcCategoryId", required = true) String categoryId,
			@RequestParam(value = "pWeight", required = true) Float weight,
			@RequestParam(value = "pQuantity", required = true) Integer quantity,
			@RequestParam(value = "pShortDesc", required = true) String shortDesc,
			@RequestParam(value = "pLongDesc", required = true) String longDesc,
			@RequestParam(value = "pCountryOfMfg", required = false) String countryOfMfg,
			@RequestParam(value = "pTkecmptId", required = true) String productTypeId,
			@RequestParam(value = "pPrice", required = true) Double price, @RequestParam MultipartFile[] files,
			HttpServletRequest O_HttpServletRequest) {

		Response O_Response = new Response();

		if (productName.isBlank()) {

			O_Response.setMessage("Product Name is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (sku.isBlank()) {

			O_Response.setMessage("SKU is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (categoryId.isBlank()) {

			O_Response.setMessage("CategoryId is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (weight == null || weight == 0) {

			O_Response.setMessage("Weight is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (quantity == null || quantity == 0) {

			O_Response.setMessage("Quantity is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (shortDesc.isBlank()) {

			O_Response.setMessage("Short Desc is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (longDesc.isBlank()) {

			O_Response.setMessage("Long Desc is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (productTypeId.isBlank()) {

			O_Response.setMessage("Product Type is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (price == null || price == 0) {

			O_Response.setMessage("Price is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (files.length == 0) {

			O_Response.setMessage("File is Empty..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Product O_Product = new Product();

		O_Product.setPName(productName);
		O_Product.setPSku(sku);
		O_Product.setPTkecmcCategoryId(categoryId);
		O_Product.setPWeight(weight);
		O_Product.setPQuantity(quantity);
		O_Product.setPShortDesc(shortDesc);
		O_Product.setPLongDesc(longDesc);
		O_Product.setPCountryOfMfg(countryOfMfg);
		O_Product.setPTkecmptId(productTypeId);
		O_Product.setPPrice(price);

		User O_User_Detail = O_AdminLoginService.getUserDetailAPIKey(apiKey);

		Boolean checkSkuAvailable = O_AdminProductService.checkSkuAvailable(sku);

		if (checkSkuAvailable) {

			O_Response.setMessage("SKU Already Exist..!");
			return new ResponseEntity<Object>(O_Response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		List<Image> LO_Image = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {

			MultipartFile O_MultipartFile = files[i];

			String multipartFileName = SingleTon.getFilenamebyfile(O_MultipartFile);

			Image O_Image = new Image();

			O_Image.setIFileName(multipartFileName);

			if (multipartFileName.toLowerCase().endsWith(".jpg") || multipartFileName.toLowerCase().endsWith(".jpeg")
					|| multipartFileName.toLowerCase().endsWith(".png")) {

				O_Image.setIFileType("Image");
			}

			if (multipartFileName.toLowerCase().endsWith(".mp4") || multipartFileName.toLowerCase().endsWith(".mpg")
					|| multipartFileName.toLowerCase().endsWith(".mpeg")
					|| multipartFileName.toLowerCase().endsWith(".flv")
					|| multipartFileName.toLowerCase().endsWith(".avi")
					|| multipartFileName.toLowerCase().endsWith(".3gp")) {

				O_Image.setIFileType("Video");
			}
			LO_Image.add(O_Image);
		}
		O_Product.setLO_IMAGE(LO_Image);
		O_Product.setPCreatedUserId(O_User_Detail.getUId());

		String productId = O_AdminProductService.addProduct(O_Product);

		return new ResponseEntity<Object>(productId, HttpStatus.OK);
	}

}
