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
	private AdminCategoryDao O_AdminCategoryDao;

	@Override
	public Boolean checkCategoryName(String cCategoryName) {
		// TODO Auto-generated method stub
		return O_AdminCategoryDao.checkCategoryName(cCategoryName);
	}

	@Override
	public String addCategory(Category RO_Category) {
		// TODO Auto-generated method stub
		return O_AdminCategoryDao.addCategory(RO_Category);
	}

	@Override
	public List<Category> categoryList() {
		// TODO Auto-generated method stub
		return O_AdminCategoryDao.categoryList();
	}

	@Override
	public List<Category> categoryListById(Category RO_Category) {
		// TODO Auto-generated method stub
		return O_AdminCategoryDao.categoryListById(RO_Category);
	}

	@Override
	public Boolean updateCategory(Category RO_Category) {
		// TODO Auto-generated method stub

		return O_AdminCategoryDao.updateCategory(RO_Category);

	}
}
