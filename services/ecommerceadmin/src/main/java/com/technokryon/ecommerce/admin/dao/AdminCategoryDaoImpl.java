package com.technokryon.ecommerce.admin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
	private SessionFactory O_SessionFactory;

	@Override
	public Boolean checkCategoryName(String cCategoryName) {

		String getCategoryName = "FROM TKECMCATEGORY WHERE cCategoryName =:categoryName";

		Query getCategoryNameQuery = O_SessionFactory.getCurrentSession().createQuery(getCategoryName);

		getCategoryNameQuery.setParameter("categoryName", cCategoryName);

		TKECMCATEGORY O_TKECMCATEGORY = (TKECMCATEGORY) getCategoryNameQuery.uniqueResult();

		if (O_TKECMCATEGORY == null) {

			return false;
		}

		return true;
	}

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

}
