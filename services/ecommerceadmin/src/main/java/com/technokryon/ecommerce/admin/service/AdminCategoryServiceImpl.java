package com.technokryon.ecommerce.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.dao.AdminCategoryDao;
import com.technokryon.ecommerce.admin.pojo.Category;

@Service("AdminCategoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class AdminCategoryServiceImpl implements AdminCategoryService {

	@Autowired
	private AdminCategoryDao adminCategoryDao;

	@Override
	public Boolean checkCategoryName(String cCategoryName) {
		
		return adminCategoryDao.checkCategoryName(cCategoryName);
	}

	@Override
	public String addCategory(Category category) {
		
		return adminCategoryDao.addCategory(category);
	}

	@Override
	public List<Category> categoryList() {
		
		return adminCategoryDao.categoryList();
	}

	@Override
	public List<Category> categoryListById(Category category) {
		
		return adminCategoryDao.categoryListById(category);
	}

	@Override
	public Boolean updateCategory(Category category) {

		return adminCategoryDao.updateCategory(category);

	}
}
