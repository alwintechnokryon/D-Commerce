package com.technokryon.ecommerce.service;

import org.springframework.transaction.annotation.Propagation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.CategoryDao;
import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.pojo.PJ_TKECMCATEGORY;

@Service("CategoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao O_CategoryDao;

	@Override
	public String addCategory(PJ_TKECMCATEGORY rO_PJ_TKECMCATEGORY) {
		// TODO Auto-generated method stub
		return O_CategoryDao.addCategory(rO_PJ_TKECMCATEGORY);
	}

	@Override
	public List<PJ_TKECMCATEGORY> categoryList() {
		// TODO Auto-generated method stub
		return O_CategoryDao.categoryList();
	}

	@Override
	public List<PJ_TKECMCATEGORY> categoryListById(PJ_TKECMCATEGORY rO_PJ_TKECMCATEGORY) {
		// TODO Auto-generated method stub
		return O_CategoryDao.categoryListById(rO_PJ_TKECMCATEGORY);
	}
}
