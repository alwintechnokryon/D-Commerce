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

		String getCategory = "FROM TKECMCATEGORY ORDER BY categoryId DESC";

		Query categoryQuery = O_SessionFactory.getCurrentSession().createQuery(getCategory);
		categoryQuery.setMaxResults(1);
		TKECMCATEGORY O_TKECMCATEGORY1 = (TKECMCATEGORY) categoryQuery.uniqueResult();

		TKECMCATEGORY O_TKECMCATEGORY = new TKECMCATEGORY();

		if (O_TKECMCATEGORY1 == null) {

			O_TKECMCATEGORY.setCategoryId("TKECC0001");
		} else {

			String categoryId = O_TKECMCATEGORY1.getCategoryId();
			Integer Ag = Integer.valueOf(categoryId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECMCATEGORY.setCategoryId("TKECC" + String.format("%04d", Ag));
		}

		try {

			O_TKECMCATEGORY.setCategoryName(rO_CATEGORY.getCategoryName());
			O_TKECMCATEGORY.setParentId(rO_CATEGORY.getParentId());
			O_TKECMCATEGORY.setCategoryLevel(rO_CATEGORY.getCategoryLevel());
			session.save(O_TKECMCATEGORY);
			tx.commit();
			session.close();

			return O_TKECMCATEGORY.getCategoryId();
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
	public List<CATEGORY> categoryList() {

		List<CATEGORY> LO_CATEGORY = new ArrayList<CATEGORY>();

		String parentCategory = "FROM TKECMCATEGORY WHERE parentId IS NULL";

		Query parentCategoryQry = O_SessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> LO_TKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.list();

//		System.out.println("Root Category size.." + LO_TKECMCATEGORY.size());
		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			LO_CATEGORY.add(new CATEGORY(O_TKECMCATEGORY, getChildCategories(O_TKECMCATEGORY.getCategoryId())));
		}
		return LO_CATEGORY;
	}

	public List<CATEGORY> getChildCategories(String parentId) {

		List<CATEGORY> LO_CATEGORY = new ArrayList<CATEGORY>();

		String childCategory = "FROM TKECMCATEGORY WHERE parentId =: parentId";

		Query childCategoryQry = O_SessionFactory.getCurrentSession().createQuery(childCategory);

		childCategoryQry.setParameter("parentId", parentId);

		List<TKECMCATEGORY> child_LO_TKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.list();

		for (TKECMCATEGORY child_O_TKECMCATEGORY : child_LO_TKECMCATEGORY) {

			LO_CATEGORY.add(new CATEGORY(child_O_TKECMCATEGORY,
					getChildCategories(child_O_TKECMCATEGORY.getCategoryId())));
		}

		return LO_CATEGORY;
	}

	@Override
	public List<CATEGORY> categoryListById(CATEGORY rO_CATEGORY) {

		List<CATEGORY> LO_CATEGORY = new ArrayList<>();

		ModelMapper O_ModelMapper = new ModelMapper();
		
		
		
		String categoryListById = "FROM TKECMCATEGORY WHERE parentId =:parentid";

		Query categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(categoryListById);

		categoryListByIdQry.setParameter("parentid", rO_CATEGORY.getCategoryId());

		List<TKECMCATEGORY> LO_TKECMCATEGORY = categoryListByIdQry.getResultList();

		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			CATEGORY O_CATEGORY = O_ModelMapper.map(O_TKECMCATEGORY, CATEGORY.class);

//			O_PJ_TKECMCATEGORY.setTkecmcCategoryLevel(O_TKECMCATEGORY.getTkecmcCategoryLevel());
//			O_PJ_TKECMCATEGORY.setTkecmcCategoryName(O_TKECMCATEGORY.getTkecmcCategoryName());
//			O_PJ_TKECMCATEGORY.setTkecmcCategoryId(O_TKECMCATEGORY.getTkecmcCategoryId());
//			O_PJ_TKECMCATEGORY.setTkecmcParentId(O_TKECMCATEGORY.getTkecmcParentId());
			LO_CATEGORY.add(O_CATEGORY);

		}
		return LO_CATEGORY;
	}
}
