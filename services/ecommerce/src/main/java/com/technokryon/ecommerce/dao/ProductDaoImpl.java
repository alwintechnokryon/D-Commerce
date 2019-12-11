package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.technokryon.ecommerce.model.TKECMIMAGE;
import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.pojo.PJ_TKECMIMAGE;
import com.technokryon.ecommerce.pojo.PJ_TKECMPRODUCT;

@Repository("ProductDao")
@Transactional
@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public List<PJ_TKECMPRODUCT> getListByCategory(String tkecmpCategoryId, Integer PageNumber) {

		List<PJ_TKECMPRODUCT> LO_PJ_TKECMPRODUCT = new ArrayList<>();

		String productQuery = "FROM TKECMPRODUCT WHERE tkecmpCategoryId.tkecmcCategoryId =:categoryId ";

		Query query = O_SessionFactory.getCurrentSession().createQuery(productQuery);

		int pageSize = 5;
		query.setFirstResult((PageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		query.setParameter("categoryId", tkecmpCategoryId);

		List<TKECMPRODUCT> LO_TKECMPRODUCT = query.list();

		for (TKECMPRODUCT O_TKECMPRODUCT : LO_TKECMPRODUCT) {

			PJ_TKECMPRODUCT O_PJ_TKECMPRODUCT = new PJ_TKECMPRODUCT();

			O_PJ_TKECMPRODUCT.setTkecmpId(O_TKECMPRODUCT.getTkecmpId());
			O_PJ_TKECMPRODUCT.setTkecmpName(O_TKECMPRODUCT.getTkecmpName());
			O_PJ_TKECMPRODUCT.setTkecmpPrice(O_TKECMPRODUCT.getTkecmpPrice());

			List<PJ_TKECMIMAGE> LO_PJ_TKECMIMAGE = new ArrayList<PJ_TKECMIMAGE>();

			String imageQuery = "FROM TKECMIMAGE WHERE tkecmiProductId.tkecmpId =:productId";

			Query query1 = O_SessionFactory.getCurrentSession().createQuery(imageQuery);

			query1.setParameter("productId", O_TKECMPRODUCT.getTkecmpId());

			List<TKECMIMAGE> LO_TKECMIMAGE = query1.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				PJ_TKECMIMAGE O_PJ_TKECMIMAGE = new PJ_TKECMIMAGE();

				O_PJ_TKECMIMAGE.setTkecmiUrl(O_TKECMIMAGE.getTkecmiUrl());
				O_PJ_TKECMIMAGE.setTkecmiFileType(O_TKECMIMAGE.getTkecmiFileType());
				O_PJ_TKECMIMAGE.setTkecmpiFileName(O_TKECMIMAGE.getTkecmpiFileName());
				LO_PJ_TKECMIMAGE.add(O_PJ_TKECMIMAGE);

			}

			O_PJ_TKECMPRODUCT.setImages(LO_PJ_TKECMIMAGE);
			LO_PJ_TKECMPRODUCT.add(O_PJ_TKECMPRODUCT);
		}

		return LO_PJ_TKECMPRODUCT;
	}

}
