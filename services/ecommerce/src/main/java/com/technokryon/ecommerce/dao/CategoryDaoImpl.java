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
	private SessionFactory O_SessionFactory;

	@Override
	// @Cacheable("CATEGORY")
	// @CacheEvict(value="CATEGORY", allEntries=true)
	public List<Category> categoryList() {

		List<Category> LO_Category = new ArrayList<Category>();

		String parentCategory = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query parentCategoryQry = O_SessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> LO_TKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.getResultList();

		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			LO_Category.add(new Category(O_TKECMCATEGORY, getChildCategories(O_TKECMCATEGORY.getCCategoryId())));

		}

		return LO_Category;
	}

	public List<Category> getChildCategories(String parentId) {

		List<Category> LO_Category = new ArrayList<Category>();

		String childCategory = "FROM TKECMCATEGORY WHERE cParentId =: parentId";

		Query childCategoryQry = O_SessionFactory.getCurrentSession().createQuery(childCategory);

		childCategoryQry.setParameter("parentId", parentId);

		List<TKECMCATEGORY> child_LO_TKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.getResultList();

		for (TKECMCATEGORY child_O_TKECMCATEGORY : child_LO_TKECMCATEGORY) {

			LO_Category.add(
					new Category(child_O_TKECMCATEGORY, getChildCategories(child_O_TKECMCATEGORY.getCCategoryId())));
		}

		return LO_Category;
	}

	@Override
	public List<Category> categoryListById(Category RO_Category) {

		List<Category> LO_Category = new ArrayList<>();

		ModelMapper O_ModelMapper = new ModelMapper();

		String categoryListById = "FROM TKECMCATEGORY WHERE cParentId =:parentid";

		String parentId = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query categoryListByIdQry;

		if (RO_Category.getCCategoryId() == null) {

			categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(parentId);

		} else {
			categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(categoryListById);
			categoryListByIdQry.setParameter("parentid", RO_Category.getCCategoryId());
		}

		List<TKECMCATEGORY> LO_TKECMCATEGORY = categoryListByIdQry.getResultList();

		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			Category O_Category = O_ModelMapper.map(O_TKECMCATEGORY, Category.class);
			O_Category.setChildCategory(getChildCategories(O_TKECMCATEGORY.getCCategoryId()));

			LO_Category.add(O_Category);

		}
		return LO_Category;
	}
}
