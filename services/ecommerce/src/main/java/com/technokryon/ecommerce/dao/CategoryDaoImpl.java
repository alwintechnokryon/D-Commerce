package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.pojo.Category;

@Repository("CategoryDao")
@Transactional
@Component
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	// @Cacheable("CATEGORY")
	// @CacheEvict(value="CATEGORY", allEntries=true)
	public List<Category> categoryList() {

		List<Category> category = new ArrayList<Category>();

		String parentCategory = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query parentCategoryQry = sessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> tKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.getResultList();

		for (TKECMCATEGORY tKECMCATEGORY1 : tKECMCATEGORY) {

			category.add(new Category(tKECMCATEGORY1, getChildCategories(tKECMCATEGORY1.getCCategoryId())));

		}

		return category;
	}

	public List<Category> getChildCategories(String parentId) {

		List<Category> category = new ArrayList<Category>();

		String childCategory = "FROM TKECMCATEGORY WHERE cParentId =: parentId";

		Query childCategoryQry = sessionFactory.getCurrentSession().createQuery(childCategory);

		childCategoryQry.setParameter("parentId", parentId);

		List<TKECMCATEGORY> tKECMCATEGORYchild = (List<TKECMCATEGORY>) childCategoryQry.getResultList();

		for (TKECMCATEGORY tKECMCATEGORYchild1 : tKECMCATEGORYchild) {

			category.add(
					new Category(tKECMCATEGORYchild1, getChildCategories(tKECMCATEGORYchild1.getCCategoryId())));
		}

		return category;
	}

	@Override
	public List<Category> categoryListById(Category category) {

		List<Category> category1 = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		String categoryListById = "FROM TKECMCATEGORY WHERE cParentId =:parentid";

		String parentId = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query categoryListByIdQry;

		if (category.getCCategoryId() == null) {

			categoryListByIdQry = sessionFactory.getCurrentSession().createQuery(parentId);

		} else {
			categoryListByIdQry = sessionFactory.getCurrentSession().createQuery(categoryListById);
			categoryListByIdQry.setParameter("parentid", category.getCCategoryId());
		}

		List<TKECMCATEGORY> tKECMCATEGORY = categoryListByIdQry.getResultList();

		for (TKECMCATEGORY tKECMCATEGORY1 : tKECMCATEGORY) {

			Category category2 = modelMapper.map(tKECMCATEGORY1, Category.class);
			category2.setChildCategory(getChildCategories(tKECMCATEGORY1.getCCategoryId()));

			category1.add(category2);

		}
		return category1;
	}
}
