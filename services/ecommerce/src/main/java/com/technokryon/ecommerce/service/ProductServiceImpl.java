package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.ProductDao;
import com.technokryon.ecommerce.pojo.Product;

@Service("ProductService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> getListByCategory(String categoryId, Integer PageNumber, String uId) {

		return productDao.getListByCategory(categoryId, PageNumber, uId);
	}

	@Override
	public Product getDetailById(String tkecmpId, String uId) {

		return productDao.getDetailById(tkecmpId, uId);
	}

}
