package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.CategoryDao;
import com.technokryon.ecommerce.pojo.Category;

@Service("CategoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao O_CategoryDao;

	@Override
	public String addCategory(Category RO_Category) {
		
		return O_CategoryDao.addCategory(RO_Category);
	}

	@Override
	public List<Category> categoryList() {
		
		return O_CategoryDao.categoryList();
	}

	@Override
	public List<Category> categoryListById(Category RO_Category) {
		
		return O_CategoryDao.categoryListById(RO_Category);
	}
}
