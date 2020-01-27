package com.technokryon.ecommerce.admin.dao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.model.TKECMCATEGORY;
import com.technokryon.ecommerce.admin.pojo.Category;

@Repository("AdminCategoryDao")
@Transactional
@Component
public class AdminCategoryDaoImpl implements AdminCategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Boolean checkCategoryName(String cCategoryName) {

		String getCategoryName = "FROM TKECMCATEGORY WHERE cCategoryName =:categoryName";

		Query getCategoryNameQuery = sessionFactory.getCurrentSession().createQuery(getCategoryName);

		getCategoryNameQuery.setParameter("categoryName", cCategoryName);

		TKECMCATEGORY tKECMCATEGORY = (TKECMCATEGORY) getCategoryNameQuery.uniqueResult();

		if (tKECMCATEGORY == null) {

			return false;
		}

		return true;
	}

	@Override
	public String addCategory(Category category) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String getCategory = "FROM TKECMCATEGORY ORDER BY cCategoryId DESC";

		Query categoryQuery = sessionFactory.getCurrentSession().createQuery(getCategory);
		categoryQuery.setMaxResults(1);
		TKECMCATEGORY tKECMCATEGORY1 = (TKECMCATEGORY) categoryQuery.uniqueResult();

		TKECMCATEGORY tKECMCATEGORY = new TKECMCATEGORY();

		if (tKECMCATEGORY1 == null) {

			tKECMCATEGORY.setCCategoryId("TKECC0001");
		} else {

			String categoryId = tKECMCATEGORY1.getCCategoryId();
			Integer Ag = Integer.valueOf(categoryId.substring(5));
			Ag++;

			System.err.println(Ag);
			tKECMCATEGORY.setCCategoryId("TKECC" + String.format("%04d", Ag));
		}

		try {

			tKECMCATEGORY.setCCategoryName(category.getCCategoryName());
			tKECMCATEGORY.setCParentId(category.getCParentId());
			tKECMCATEGORY.setCCategoryLevel(category.getCCategoryLevel());
			tKECMCATEGORY.setCCreatedUserId(category.getCCreatedUserId());
			tKECMCATEGORY.setCCreatedDate(OffsetDateTime.now());
			session.save(tKECMCATEGORY);
			transaction.commit();
			session.close();

			return tKECMCATEGORY.getCCategoryId();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return null;
		}
	}

	@Override
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

		List<TKECMCATEGORY> childtKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.getResultList();

		for (TKECMCATEGORY childTKECMCATEGORY : childtKECMCATEGORY) {

			category.add(new Category(childTKECMCATEGORY, getChildCategories(childTKECMCATEGORY.getCCategoryId())));
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

	@Override
	public Boolean updateCategory(Category category) {

		TKECMCATEGORY tKECMCATEGORY = sessionFactory.getCurrentSession().get(TKECMCATEGORY.class,
				category.getCCategoryId());

		if (tKECMCATEGORY == null) {

			return false;
		}

		tKECMCATEGORY.setCCategoryName(category.getCCategoryName());
		sessionFactory.getCurrentSession().update(tKECMCATEGORY);

		return true;

	}

}
