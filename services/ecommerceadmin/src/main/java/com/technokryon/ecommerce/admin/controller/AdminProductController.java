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
	private AdminProductService adminProductService;

	@Autowired
	private AdminLoginService adminLoginService;

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
			HttpServletRequest httpServletRequest) {

		Response response = new Response();

		if (productName.isBlank()) {

			response.setMessage("Product Name is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (sku.isBlank()) {

			response.setMessage("SKU is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (categoryId.isBlank()) {

			response.setMessage("CategoryId is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (weight == null || weight == 0) {

			response.setMessage("Weight is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (quantity == null || quantity == 0) {

			response.setMessage("Quantity is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (shortDesc.isBlank()) {

			response.setMessage("Short Desc is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (longDesc.isBlank()) {

			response.setMessage("Long Desc is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (productTypeId.isBlank()) {

			response.setMessage("Product Type is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (price == null || price == 0) {

			response.setMessage("Price is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (files.length == 0) {

			response.setMessage("File is Empty..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Product product = new Product();

		product.setPName(productName);
		product.setPSku(sku);
		product.setPTkecmcCategoryId(categoryId);
		product.setPWeight(weight);
		product.setPQuantity(quantity);
		product.setPShortDesc(shortDesc);
		product.setPLongDesc(longDesc);
		product.setPCountryOfMfg(countryOfMfg);
		product.setPTkecmptId(productTypeId);
		product.setPPrice(price);

		User userDetail = adminLoginService.getUserDetailAPIKey(apiKey);

		Boolean checkSkuAvailable = adminProductService.checkSkuAvailable(sku);

		if (checkSkuAvailable) {

			response.setMessage("SKU Already Exist..!");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		List<Image> image = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {

			MultipartFile multipartFile = files[i];

			String multipartFileName = SingleTon.getFilenamebyfile(multipartFile);

			Image image1 = new Image();

			image1.setIFileName(multipartFileName);

			System.err.println(multipartFileName);

			if (multipartFileName.toLowerCase().endsWith(".jpg") || multipartFileName.toLowerCase().endsWith(".jpeg")
					|| multipartFileName.toLowerCase().endsWith(".png")) {

				image1.setIFileType("Image");
			}

			if (multipartFileName.toLowerCase().endsWith(".mp4") || multipartFileName.toLowerCase().endsWith(".mpg")
					|| multipartFileName.toLowerCase().endsWith(".mpeg")
					|| multipartFileName.toLowerCase().endsWith(".flv")
					|| multipartFileName.toLowerCase().endsWith(".avi")
					|| multipartFileName.toLowerCase().endsWith(".3gp")) {

				image1.setIFileType("Video");
			}
			image.add(image1);
		}
		product.setLO_IMAGE(image);
		product.setPCreatedUserId(userDetail.getUId());

		String productId = adminProductService.addProduct(product);

		return new ResponseEntity<Object>(productId, HttpStatus.OK);
	}

}
