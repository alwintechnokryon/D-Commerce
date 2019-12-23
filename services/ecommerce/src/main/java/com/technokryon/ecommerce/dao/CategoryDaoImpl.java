package com.technokryon.ecommerce.dao;

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

import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.pojo.CATEGORY;

@Repository("CategoryDao")
@Transactional
@Component
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public String addCategory(CATEGORY rO_CATEGORY) {

		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getCategory = "FROM TKECMCATEGORY ORDER BY cCategoryId DESC";

		Query categoryQuery = O_SessionFactory.getCurrentSession().createQuery(getCategory);
		categoryQuery.setMaxResults(1);
		TKECMCATEGORY O_TKECMCATEGORY1 = (TKECMCATEGORY) categoryQuery.uniqueResult();

		TKECMCATEGORY O_TKECMCATEGORY = new TKECMCATEGORY();

		if (O_TKECMCATEGORY1 == null) {

			O_TKECMCATEGORY.setCCategoryId("TKECC0001");
		} else {

			String categoryId = O_TKECMCATEGORY1.getCCategoryId();
			Integer Ag = Integer.valueOf(categoryId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECMCATEGORY.setCCategoryId("TKECC" + String.format("%04d", Ag));
		}

		try {

			O_TKECMCATEGORY.setCCategoryName(rO_CATEGORY.getCCategoryName());
			O_TKECMCATEGORY.setCParentId(rO_CATEGORY.getCParentId());
			O_TKECMCATEGORY.setCCategoryLevel(rO_CATEGORY.getCCategoryLevel());
			session.save(O_TKECMCATEGORY);
			tx.commit();
			session.close();

			return O_TKECMCATEGORY.getCCategoryId();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			session.close();

			return null;
		}
	}

	@Override
	// @Cacheable("CATEGORY")
	// @CacheEvict(value="CATEGORY", allEntries=true)
	public List<CATEGORY> categoryList() {

		List<CATEGORY> LO_CATEGORY = new ArrayList<CATEGORY>();

		String parentCategory = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query parentCategoryQry = O_SessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> LO_TKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.list();

//		System.out.println("Root Category size.." + LO_TKECMCATEGORY.size());
		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			LO_CATEGORY.add(new CATEGORY(O_TKECMCATEGORY, getChildCategories(O_TKECMCATEGORY.getCCategoryId())));

		}

		return LO_CATEGORY;
	}

	public List<CATEGORY> getChildCategories(String parentId) {

		List<CATEGORY> LO_CATEGORY = new ArrayList<CATEGORY>();

		String childCategory = "FROM TKECMCATEGORY WHERE cParentId =: parentId";

		Query childCategoryQry = O_SessionFactory.getCurrentSession().createQuery(childCategory);

		childCategoryQry.setParameter("parentId", parentId);

		List<TKECMCATEGORY> child_LO_TKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.list();

		for (TKECMCATEGORY child_O_TKECMCATEGORY : child_LO_TKECMCATEGORY) {

			LO_CATEGORY.add(
					new CATEGORY(child_O_TKECMCATEGORY, getChildCategories(child_O_TKECMCATEGORY.getCCategoryId())));
		}

		return LO_CATEGORY;
	}

	@Override
	public List<CATEGORY> categoryListById(CATEGORY rO_CATEGORY) {

		List<CATEGORY> LO_CATEGORY = new ArrayList<>();

		ModelMapper O_ModelMapper = new ModelMapper();

//		PropertyMap<TKECMCATEGORY, CATEGORY> skipModifiedFieldsMap = new PropertyMap<TKECMCATEGORY, CATEGORY>() {
//			protected void configure() {
//
//				skip().setCategoryLevel(null);
//				skip().setCategoryName(null);
//			}
//		};
//		O_ModelMapper.addMappings(skipModifiedFieldsMap);

		String categoryListById = "FROM TKECMCATEGORY WHERE cParentId =:parentid";

		String parentId = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query categoryListByIdQry;


		if (rO_CATEGORY.getCCategoryId() == null) {

			categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(parentId);

		}else {
			categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(categoryListById);
			categoryListByIdQry.setParameter("parentid", rO_CATEGORY.getCCategoryId());
		}

		List<TKECMCATEGORY> LO_TKECMCATEGORY = categoryListByIdQry.getResultList();

		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			CATEGORY O_CATEGORY = O_ModelMapper.map(O_TKECMCATEGORY, CATEGORY.class);
			O_CATEGORY.setChildCategory(getChildCategories(O_TKECMCATEGORY.getCCategoryId()));

			LO_CATEGORY.add(O_CATEGORY);

		}
		return LO_CATEGORY;
	}
}
