package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.PJ_TKECMPRODUCT;

public interface ProductDao {

	List<PJ_TKECMPRODUCT> getListByCategory(String tkecmpCategoryId, Integer pageNumber);

	PJ_TKECMPRODUCT getDetailById(String tkecmpId);


}
