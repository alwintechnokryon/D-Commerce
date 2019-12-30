package com.technokryon.ecommerce.service;

import org.springframework.transaction.annotation.Propagation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.CategoryDao;
import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.pojo.CATEGORY;

@Service("CategoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao O_CategoryDao;

	@Override
	public String addCategory(CATEGORY RO_CATEGORY) {
		// TODO Auto-generated method stub
		return O_CategoryDao.addCategory(RO_CATEGORY);
	}

	@Override
	public List<CATEGORY> categoryList() {
		// TODO Auto-generated method stub
		return O_CategoryDao.categoryList();
	}

	@Override
	public List<CATEGORY> categoryListById(CATEGORY RO_CATEGORY) {
		// TODO Auto-generated method stub
		return O_CategoryDao.categoryListById(RO_CATEGORY);
	}
}
