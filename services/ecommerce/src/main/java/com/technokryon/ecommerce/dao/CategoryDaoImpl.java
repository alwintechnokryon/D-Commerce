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

		String getCategory = "FROM TKECMCATEGORY ORDER BY tkecmcCategoryId DESC";

		Query categoryQuery = O_SessionFactory.getCurrentSession().createQuery(getCategory);
		categoryQuery.setMaxResults(1);
		TKECMCATEGORY O_TKECMCATEGORY1 = (TKECMCATEGORY) categoryQuery.uniqueResult();

		TKECMCATEGORY O_TKECMCATEGORY = new TKECMCATEGORY();

		if (O_TKECMCATEGORY1 == null) {

			O_TKECMCATEGORY.setTkecmcCategoryId("TKECC0001");
		} else {

			String categoryId = O_TKECMCATEGORY1.getTkecmcCategoryId();
			Integer Ag = Integer.valueOf(categoryId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECMCATEGORY.setTkecmcCategoryId("TKECC" + String.format("%04d", Ag));
		}

		try {

			O_TKECMCATEGORY.setTkecmcCategoryName(rO_PJ_TKECMCATEGORY.getTkecmcCategoryName());
			O_TKECMCATEGORY.setTkecmcParentId(rO_PJ_TKECMCATEGORY.getTkecmcParentId());
			O_TKECMCATEGORY.setTkecmcCategoryLevel(rO_PJ_TKECMCATEGORY.getTkecmcCategoryLevel());
			session.save(O_TKECMCATEGORY);
			tx.commit();
			session.close();

			return O_TKECMCATEGORY.getTkecmcCategoryId();
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

		String parentCategory = "FROM TKECMCATEGORY WHERE tkecmcParentId IS NULL";

		Query parentCategoryQry = O_SessionFactory.getCurrentSession().createQuery(parentCategory);

		List<TKECMCATEGORY> LO_TKECMCATEGORY = (List<TKECMCATEGORY>) parentCategoryQry.list();

		System.out.println("Root Category size.." + LO_TKECMCATEGORY.size());
		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			LO_PJ_TKECMCATEGORY.add(new PJ_TKECMCATEGORY(O_TKECMCATEGORY, getChildCategories(O_TKECMCATEGORY.getTkecmcCategoryId())));
		}
		return LO_PJ_TKECMCATEGORY;
	}

	public List<PJ_TKECMCATEGORY> getChildCategories(String parentId) {

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = new ArrayList<PJ_TKECMCATEGORY>();

		String childCategory = "FROM TKECMCATEGORY WHERE tkecmcParentId =: parentId";

		Query childCategoryQry = O_SessionFactory.getCurrentSession().createQuery(childCategory);

		childCategoryQry.setParameter("parentId", parentId);

		List<TKECMCATEGORY> child_LO_TKECMCATEGORY = (List<TKECMCATEGORY>) childCategoryQry.list();

		for (TKECMCATEGORY child_O_TKECMCATEGORY : child_LO_TKECMCATEGORY) {

			LO_PJ_TKECMCATEGORY.add(new PJ_TKECMCATEGORY(child_O_TKECMCATEGORY,
					getChildCategories(child_O_TKECMCATEGORY.getTkecmcCategoryId())));
		}

		return LO_PJ_TKECMCATEGORY;
	}

	@Override
	public List<PJ_TKECMCATEGORY> categoryListById(PJ_TKECMCATEGORY rO_PJ_TKECMCATEGORY) {

		List<PJ_TKECMCATEGORY> LO_PJ_TKECMCATEGORY = new ArrayList<>();

		ModelMapper O_ModelMapper = new ModelMapper();
		
		
		
		String categoryListById = "FROM TKECMCATEGORY WHERE tkecmcParentId =:parentid";

		Query categoryListByIdQry = O_SessionFactory.getCurrentSession().createQuery(categoryListById);

		categoryListByIdQry.setParameter("parentid", rO_PJ_TKECMCATEGORY.getTkecmcCategoryId());

		List<TKECMCATEGORY> LO_TKECMCATEGORY = categoryListByIdQry.getResultList();

		for (TKECMCATEGORY O_TKECMCATEGORY : LO_TKECMCATEGORY) {

			PJ_TKECMCATEGORY O_PJ_TKECMCATEGORY = O_ModelMapper.map(O_TKECMCATEGORY, PJ_TKECMCATEGORY.class);

//			O_PJ_TKECMCATEGORY.setTkecmcCategoryLevel(O_TKECMCATEGORY.getTkecmcCategoryLevel());
//			O_PJ_TKECMCATEGORY.setTkecmcCategoryName(O_TKECMCATEGORY.getTkecmcCategoryName());
//			O_PJ_TKECMCATEGORY.setTkecmcCategoryId(O_TKECMCATEGORY.getTkecmcCategoryId());
//			O_PJ_TKECMCATEGORY.setTkecmcParentId(O_TKECMCATEGORY.getTkecmcParentId());
			LO_PJ_TKECMCATEGORY.add(O_PJ_TKECMCATEGORY);

		}
		return LO_PJ_TKECMCATEGORY;
	}
}
