package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.PJ_TKECMPRODUCT;

public interface ProductService {

	List<PJ_TKECMPRODUCT> getListByCategory(String tkecmpCategoryId, Integer PageNumber);

	PJ_TKECMPRODUCT getDetailById(String tkecmpId);

}
