package com.technokryon.ecommerce.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.ProductDao;
import com.technokryon.ecommerce.pojo.PRODUCT;

@Service("ProductService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao O_ProductDao;

	@Override
	public List<PRODUCT> getListByCategory(String categoryId,Integer PageNumber) {
		
		return O_ProductDao.getListByCategory(categoryId,PageNumber);
	}

	@Override
	public PRODUCT getDetailById(String id) {
		// TODO Auto-generated method stub
		return O_ProductDao.getDetailById(id);
	}

	
}
