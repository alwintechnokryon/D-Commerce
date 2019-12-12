package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMIMAGE;
import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECTCONFIGURABLELINK;
import com.technokryon.ecommerce.model.TKECTPRODUCTATTRIBUTE;
import com.technokryon.ecommerce.pojo.PJ_TKECMIMAGE;
import com.technokryon.ecommerce.pojo.PJ_TKECMPRODUCT;
import com.technokryon.ecommerce.pojo.PJ_TKECTCONFIGURABLELINK;
import com.technokryon.ecommerce.pojo.PJ_TKECTOPTIONATTRIBUTE;
import com.technokryon.ecommerce.pojo.PJ_TKECTPRODUCTATTRIBUTE;

@Repository("ProductDao")
@Transactional
@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public List<PJ_TKECMPRODUCT> getListByCategory(String tkecmpCategoryId, Integer PageNumber) {

		List<PJ_TKECMPRODUCT> LO_PJ_TKECMPRODUCT = new ArrayList<>();

		String productQuery = "FROM TKECMPRODUCT WHERE tkecmpCategoryId.tkecmcCategoryId =:categoryId AND tkecmpDefault=:default";

		String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE tkectpaProductId.tkecmpId =:productId";

		String imageQuery = "FROM TKECMIMAGE WHERE tkecmiProductId.tkecmpId =:productId";

		Query query = O_SessionFactory.getCurrentSession().createQuery(productQuery);

		int pageSize = 5;
		query.setFirstResult((PageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		query.setParameter("categoryId", tkecmpCategoryId);
		query.setParameter("default", "Y");

		List<TKECMPRODUCT> LO_TKECMPRODUCT = query.list();

		for (TKECMPRODUCT O_TKECMPRODUCT : LO_TKECMPRODUCT) {

			PJ_TKECMPRODUCT O_PJ_TKECMPRODUCT = new PJ_TKECMPRODUCT();

			O_PJ_TKECMPRODUCT.setTkecmpId(O_TKECMPRODUCT.getTkecmpId());
			O_PJ_TKECMPRODUCT.setTkecmpName(O_TKECMPRODUCT.getTkecmpName());
			O_PJ_TKECMPRODUCT.setTkecmpPrice(O_TKECMPRODUCT.getTkecmpPrice());

			List<PJ_TKECTOPTIONATTRIBUTE> LO_PJ_TKECTOPTIONATTRIBUTE = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getTkecmpId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				PJ_TKECTOPTIONATTRIBUTE O_PJ_TKECTOPTIONATTRIBUTE = new PJ_TKECTOPTIONATTRIBUTE();

				O_PJ_TKECTOPTIONATTRIBUTE.setTkectoaAttributeId(
						O_TKECTPRODUCTATTRIBUTE.getTkectpaOptionAttributeId().getTkectoaAttributeId().getTkecmaId());
				O_PJ_TKECTOPTIONATTRIBUTE
						.setTkectoaName(O_TKECTPRODUCTATTRIBUTE.getTkectpaOptionAttributeId().getTkectoaName());
				LO_PJ_TKECTOPTIONATTRIBUTE.add(O_PJ_TKECTOPTIONATTRIBUTE);
			}
			O_PJ_TKECMPRODUCT.setOptionAttribute(LO_PJ_TKECTOPTIONATTRIBUTE);

			List<PJ_TKECMIMAGE> LO_PJ_TKECMIMAGE = new ArrayList<PJ_TKECMIMAGE>();

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

	@Override
	public PJ_TKECMPRODUCT getDetailById(String tkecmpId) {

		PJ_TKECMPRODUCT O_PJ_TKECMPRODUCT = new PJ_TKECMPRODUCT();

		String productListByProductId = "FROM TKECTCONFIGURABLELINK WHERE tkectclProductId.tkecmpId =:productId";

		String productListByParentId = "FROM TKECTCONFIGURABLELINK WHERE tkectclParentId.tkecmpId =:parentId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE tkectpaProductId.tkecmpId =:proAttrProductId ";

		String imageQuery = "FROM TKECMIMAGE WHERE tkecmiProductId.tkecmpId =:productId";

		Query productQuery = O_SessionFactory.getCurrentSession().createQuery(productListByProductId);

		productQuery.setParameter("productId", tkecmpId);

		TKECTCONFIGURABLELINK O_TKECTCONFIGURABLELINK = (TKECTCONFIGURABLELINK) productQuery.uniqueResult();

		if (O_TKECTCONFIGURABLELINK != null) {

			O_PJ_TKECMPRODUCT.setAttributeId(tkecmpId);
			O_PJ_TKECMPRODUCT.setTkecmpName(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpName());
			O_PJ_TKECMPRODUCT.setTkecmpPrice(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpPrice());
			O_PJ_TKECMPRODUCT.setTkecmpShortDesc(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpShortDesc());
			O_PJ_TKECMPRODUCT.setTkecmpLongDesc(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpLongDesc());
			O_PJ_TKECMPRODUCT.setTkecmpWeight(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpWeight());
			O_PJ_TKECMPRODUCT.setTkecmpQuantity(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpQuantity());
			O_PJ_TKECMPRODUCT
					.setTkecmpCountryOfMfg(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpCountryOfMfg());
			O_PJ_TKECMPRODUCT
					.setTkecmpType(O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpType().getTkecmptAgId());

			Query parentQuery = O_SessionFactory.getCurrentSession().createQuery(productListByParentId);

			parentQuery.setParameter("parentId", O_TKECTCONFIGURABLELINK.getTkectclParentId());

			List<TKECTCONFIGURABLELINK> LO_TKECTCONFIGURABLELINK = parentQuery.list();

			for (TKECTCONFIGURABLELINK O_TKECTCONFIGURABLELINK1 : LO_TKECTCONFIGURABLELINK) {

				Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

				productAttributeQuery.setParameter("proAttrProductId",
						O_TKECTCONFIGURABLELINK1.getTkectclProductId().getTkecmpId());

				List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.list();

				for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

					PJ_TKECTOPTIONATTRIBUTE O_PJ_TKECTOPTIONATTRIBUTE = new PJ_TKECTOPTIONATTRIBUTE();

					O_PJ_TKECTOPTIONATTRIBUTE.setTkectoaAttributeId(O_TKECTPRODUCTATTRIBUTE
							.getTkectpaOptionAttributeId().getTkectoaAttributeId().getTkecmaId());
					O_PJ_TKECTOPTIONATTRIBUTE
							.setTkectoaName(O_TKECTPRODUCTATTRIBUTE.getTkectpaOptionAttributeId().getTkectoaName());
					;

				}

			}
			List<PJ_TKECMIMAGE> LO_PJ_TKECMIMAGE = new ArrayList<PJ_TKECMIMAGE>();

			Query query1 = O_SessionFactory.getCurrentSession().createQuery(imageQuery);

			query1.setParameter("productId", O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpId());

			List<TKECMIMAGE> LO_TKECMIMAGE = query1.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				PJ_TKECMIMAGE O_PJ_TKECMIMAGE = new PJ_TKECMIMAGE();

				O_PJ_TKECMIMAGE.setTkecmiUrl(O_TKECMIMAGE.getTkecmiUrl());
				O_PJ_TKECMIMAGE.setTkecmiFileType(O_TKECMIMAGE.getTkecmiFileType());
				O_PJ_TKECMIMAGE.setTkecmpiFileName(O_TKECMIMAGE.getTkecmpiFileName());
				LO_PJ_TKECMIMAGE.add(O_PJ_TKECMIMAGE);

			}

			O_PJ_TKECMPRODUCT.setImages(LO_PJ_TKECMIMAGE);

		}

		return O_PJ_TKECMPRODUCT;
	}

}
