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
import com.technokryon.ecommerce.pojo.Category;

@Repository("CategoryDao")
@Transactional
@Component
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public String addCategory(Category RO_Category) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

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

			O_TKECMCATEGORY.setCCategoryName(RO_Category.getCCategoryName());
			O_TKECMCATEGORY.setCParentId(RO_Category.getCParentId());
			O_TKECMCATEGORY.setCCategoryLevel(RO_Category.getCCategoryLevel());
			O_Session.save(O_TKECMCATEGORY);
			O_Transaction.commit();
			O_Session.close();

			return O_TKECMCATEGORY.getCCategoryId();
		} catch (Exception e) {
			e.printStackTrace();
			if (O_Transaction.isActive()) {
				O_Transaction.rollback();
			}
			O_Session.close();

			return null;
		}
	}

	@Override
	// @Cacheable("CATEGORY")
	// @CacheEvict(value="CATEGORY", allEntries=true)
	public List<Category> categoryList() {

		List<Category> LO_Category = new ArrayList<Category>();

		String parentCategory = "FROM TKECMCATEGORY WHERE cParentId IS NULL";

		Query parentCategoryQry = O_SessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> LO_TKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.list();
		
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

		List<TKECMCATEGORY> child_LO_TKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.list();

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

		}else {
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
