package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.pojo.PJ_TKECMCATEGORY;

@Repository("CategoryDao")
@Transactional
@Component
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public String addCategory(PJ_TKECMCATEGORY rO_PJ_TKECMCATEGORY) {

		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getCategory = "FROM TKECMCATEGORY ORDER BY TKECMC_CATEGORY_ID DESC";

		Query categoryQuery = O_SessionFactory.getCurrentSession().createQuery(getCategory);
		categoryQuery.setMaxResults(1);
		TKECMCATEGORY O_TKECMCATEGORY1 = (TKECMCATEGORY) categoryQuery.uniqueResult();

		TKECMCATEGORY O_TKECMCATEGORY = new TKECMCATEGORY();

		if (O_TKECMCATEGORY1 == null) {

			O_TKECMCATEGORY.setTKECMC_CATEGORY_ID("TKECC0001");
		} else {

			String categoryId = O_TKECMCATEGORY1.getTKECMC_CATEGORY_ID();
			Integer Ag = Integer.valueOf(categoryId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECMCATEGORY.setTKECMC_CATEGORY_ID("TKECC" + String.format("%04d", Ag));
		}

		try {

			O_TKECMCATEGORY.setTKECMC_CATEGORY_NAME(rO_PJ_TKECMCATEGORY.getCategoryName());
			O_TKECMCATEGORY.setTKECMC_PARENT_ID(rO_PJ_TKECMCATEGORY.getParentId());
			O_TKECMCATEGORY.setTKECMC_CATEGORY_LEVEL(rO_PJ_TKECMCATEGORY.getCategoryLevel());
			session.save(O_TKECMCATEGORY);
			tx.commit();
			session.close();

			return O_TKECMCATEGORY.getTKECMC_CATEGORY_ID();
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
	public List<PJ_TKECMCATEGORY> categoryList() {

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = new ArrayList<PJ_TKECMCATEGORY>();

		String parentCategory = "FROM TKECMCATEGORY WHERE TKECMC_PARENT_ID IS NULL";

		Query parentCategoryQry = O_SessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> LO_TKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.list();

		System.out.println("Root Category size.." + LO_TKECMCATEGORY.size());
		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			LO_PJ_TKECMCATEGORY.add(
					new PJ_TKECMCATEGORY(O_TKECMCATEGORY, getChildCategories(O_TKECMCATEGORY.getTKECMC_CATEGORY_ID())));
		}
		return LO_PJ_TKECMCATEGORY;
	}

	public List<PJ_TKECMCATEGORY> getChildCategories(String parentId) {

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = new ArrayList<PJ_TKECMCATEGORY>();

		String childCategory = "FROM TKECMCATEGORY WHERE TKECMC_PARENT_ID =: parentId";

		Query childCategoryQry = O_SessionFactory.getCurrentSession().createQuery(childCategory);

		childCategoryQry.setParameter("parentId", parentId);

		List<TKECMCATEGORY> child_LO_TKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.list();

		for (TKECMCATEGORY child_O_TKECMCATEGORY : child_LO_TKECMCATEGORY) {

			LO_PJ_TKECMCATEGORY.add(new PJ_TKECMCATEGORY(child_O_TKECMCATEGORY,
					getChildCategories(child_O_TKECMCATEGORY.getTKECMC_CATEGORY_ID())));
		}

		return LO_PJ_TKECMCATEGORY;
	}

	@Override
	public List<PJ_TKECMCATEGORY> categoryListById(PJ_TKECMCATEGORY rO_PJ_TKECMCATEGORY) {

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = new ArrayList<>();

		String categoryListById = "FROM TKECMCATEGORY WHERE TKECMC_PARENT_ID =:parentid";

		Query categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(categoryListById);

		categoryListByIdQry.setParameter("parentid", rO_PJ_TKECMCATEGORY.getCategoryId());

		List<TKECMCATEGORY> LO_TKECMCATEGORY = categoryListByIdQry.getResultList();

		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			PJ_TKECMCATEGORY O_PJ_TKECMCATEGORY = new PJ_TKECMCATEGORY();

			O_PJ_TKECMCATEGORY.setCategoryLevel(O_TKECMCATEGORY.getTKECMC_CATEGORY_LEVEL());
			O_PJ_TKECMCATEGORY.setCategoryName(O_TKECMCATEGORY.getTKECMC_CATEGORY_NAME());
			O_PJ_TKECMCATEGORY.setCategoryId(O_TKECMCATEGORY.getTKECMC_CATEGORY_ID());
			O_PJ_TKECMCATEGORY.setParentId(O_TKECMCATEGORY.getTKECMC_PARENT_ID());
			LO_PJ_TKECMCATEGORY.add(O_PJ_TKECMCATEGORY);

		}
		return LO_PJ_TKECMCATEGORY;
	}
}
