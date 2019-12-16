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
import com.technokryon.ecommerce.model.TKECMPRODUCTDOWNLOAD;
import com.technokryon.ecommerce.model.TKECTCONFIGURABLELINK;
import com.technokryon.ecommerce.model.TKECTPRODUCTATTRIBUTE;
import com.technokryon.ecommerce.pojo.PJ_TKECMIMAGE;
import com.technokryon.ecommerce.pojo.PJ_TKECMPRODUCT;
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
			O_PJ_TKECMPRODUCT.setLO_PJ_TKECTOPTIONATTRIBUTE(LO_PJ_TKECTOPTIONATTRIBUTE);

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

			O_PJ_TKECMPRODUCT.setLO_PJ_TKECMIMAGE(LO_PJ_TKECMIMAGE);
			LO_PJ_TKECMPRODUCT.add(O_PJ_TKECMPRODUCT);
		}
		return LO_PJ_TKECMPRODUCT;
	}

	@Override
	public PJ_TKECMPRODUCT getDetailById(String tkecmpId) {

		PJ_TKECMPRODUCT O_PJ_TKECMPRODUCT = new PJ_TKECMPRODUCT();

		String configurableProduct = "FROM TKECTCONFIGURABLELINK WHERE tkectclProductId.tkecmpId =:productId";

		String configurableParent = "FROM TKECTCONFIGURABLELINK WHERE tkectclParentId.tkecmpId =:parentId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE tkectpaProductId.tkecmpId =:proAttrProductId ";

		String image = "FROM TKECMIMAGE WHERE tkecmiProductId.tkecmpId =:productId";

		Query configurableProductQuery = O_SessionFactory.getCurrentSession().createQuery(configurableProduct);

		configurableProductQuery.setParameter("productId", tkecmpId);

		TKECTCONFIGURABLELINK O_TKECTCONFIGURABLELINK = (TKECTCONFIGURABLELINK) configurableProductQuery.uniqueResult();

		if (O_TKECTCONFIGURABLELINK != null) {

			O_PJ_TKECMPRODUCT.setTkecmpId(tkecmpId);
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

			Query configurableParentQuery = O_SessionFactory.getCurrentSession().createQuery(configurableParent);

			configurableParentQuery.setParameter("parentId",
					O_TKECTCONFIGURABLELINK.getTkectclParentId().getTkecmpId());

			List<TKECTCONFIGURABLELINK> LO_TKECTCONFIGURABLELINK = configurableParentQuery.list();

			List<PJ_TKECTPRODUCTATTRIBUTE> LO_PJ_TKECTPRODUCTATTRIBUTE = new ArrayList<PJ_TKECTPRODUCTATTRIBUTE>();

			System.err.println(LO_TKECTCONFIGURABLELINK.size());

			System.err.println(LO_TKECTCONFIGURABLELINK);

			for (TKECTCONFIGURABLELINK child_TKECTCONFIGURABLELINK : LO_TKECTCONFIGURABLELINK) {

				Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

				productAttributeQuery.setParameter("proAttrProductId",
						child_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpId());

				PJ_TKECTPRODUCTATTRIBUTE O_PJ_TKECTPRODUCTATTRIBUTE = new PJ_TKECTPRODUCTATTRIBUTE();

				List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.list();

				System.err.println("ReqId" + tkecmpId);

				System.err.println("Id" + child_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpId());

				O_PJ_TKECTPRODUCTATTRIBUTE
						.setSubProductId(child_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpId());

				if (tkecmpId.equals(child_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpId())) {

					O_PJ_TKECTPRODUCTATTRIBUTE.setDefaultYN("Y");

				} else {

					O_PJ_TKECTPRODUCTATTRIBUTE.setDefaultYN("N");

				}

				List<PJ_TKECTOPTIONATTRIBUTE> LO_PJ_TKECTOPTIONATTRIBUTE = new ArrayList<>();

				for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

					PJ_TKECTOPTIONATTRIBUTE O_PJ_TKECTOPTIONATTRIBUTE = new PJ_TKECTOPTIONATTRIBUTE();

					O_PJ_TKECTOPTIONATTRIBUTE.setTkectoaAttributeId(O_TKECTPRODUCTATTRIBUTE
							.getTkectpaOptionAttributeId().getTkectoaAttributeId().getTkecmaId());
					O_PJ_TKECTOPTIONATTRIBUTE
							.setTkectoaName(O_TKECTPRODUCTATTRIBUTE.getTkectpaOptionAttributeId().getTkectoaName());
					LO_PJ_TKECTOPTIONATTRIBUTE.add(O_PJ_TKECTOPTIONATTRIBUTE);
				}

				O_PJ_TKECTPRODUCTATTRIBUTE.setLO_PJ_TKECTOPTIONATTRIBUTE(LO_PJ_TKECTOPTIONATTRIBUTE);

				LO_PJ_TKECTPRODUCTATTRIBUTE.add(O_PJ_TKECTPRODUCTATTRIBUTE);

			}
			O_PJ_TKECMPRODUCT.setLO_PJ_TKECTPRODUCTATTRIBUTE(LO_PJ_TKECTPRODUCTATTRIBUTE);

			List<PJ_TKECMIMAGE> LO_PJ_TKECMIMAGE = new ArrayList<PJ_TKECMIMAGE>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECTCONFIGURABLELINK.getTkectclProductId().getTkecmpId());

			List<TKECMIMAGE> LO_TKECMIMAGE = imageQuery.list();

			System.err.println(LO_TKECMIMAGE);

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				PJ_TKECMIMAGE O_PJ_TKECMIMAGE = new PJ_TKECMIMAGE();

				O_PJ_TKECMIMAGE.setTkecmiUrl(O_TKECMIMAGE.getTkecmiUrl());
				O_PJ_TKECMIMAGE.setTkecmiFileType(O_TKECMIMAGE.getTkecmiFileType());
				O_PJ_TKECMIMAGE.setTkecmpiFileName(O_TKECMIMAGE.getTkecmpiFileName());
				LO_PJ_TKECMIMAGE.add(O_PJ_TKECMIMAGE);

			}

			O_PJ_TKECMPRODUCT.setLO_PJ_TKECMIMAGE(LO_PJ_TKECMIMAGE);

		} else {

			String simpleProduct = "FROM TKECMPRODUCT WHERE tkecmpId =:productId";

			String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE tkectpaProductId.tkecmpId =:productId";

			String downloadableProduct = "FROM TKECMPRODUCTDOWNLOAD WHERE tkecmpdProductId.tkecmpId =:downloadProductId";

			Query simpleProductQuery = O_SessionFactory.getCurrentSession().createQuery(simpleProduct);

			simpleProductQuery.setParameter("productId", tkecmpId);

			
			
			TKECMPRODUCT O_TKECMPRODUCT = (TKECMPRODUCT) simpleProductQuery.uniqueResult();
			
			if (O_TKECMPRODUCT == null) {

				return null;
			}
			
			O_PJ_TKECMPRODUCT.setTkecmpId(O_TKECMPRODUCT.getTkecmpId());
			O_PJ_TKECMPRODUCT.setTkecmpName(O_TKECMPRODUCT.getTkecmpName());
			O_PJ_TKECMPRODUCT.setTkecmpLongDesc(O_TKECMPRODUCT.getTkecmpLongDesc());
			O_PJ_TKECMPRODUCT.setTkecmpShortDesc(O_TKECMPRODUCT.getTkecmpShortDesc());
			O_PJ_TKECMPRODUCT.setTkecmpCountryOfMfg(O_TKECMPRODUCT.getTkecmpCountryOfMfg());
			O_PJ_TKECMPRODUCT.setTkecmpQuantity(O_TKECMPRODUCT.getTkecmpQuantity());
			O_PJ_TKECMPRODUCT.setTkecmpType(O_TKECMPRODUCT.getTkecmpType().getTkecmptAgId());
			O_PJ_TKECMPRODUCT.setTkecmpPrice(O_TKECMPRODUCT.getTkecmpPrice());

			if (O_TKECMPRODUCT.getTkecmpType().getTkecmptAgId().equals(3)) {

				Query downloadableProductQuery = O_SessionFactory.getCurrentSession().createQuery(downloadableProduct);

				downloadableProductQuery.setParameter("downloadProductId", tkecmpId);

				TKECMPRODUCTDOWNLOAD O_TKECMPRODUCTDOWNLOAD = (TKECMPRODUCTDOWNLOAD) downloadableProductQuery
						.uniqueResult();

				O_PJ_TKECMPRODUCT.setTkecmpdFile(O_TKECMPRODUCTDOWNLOAD.getTkecmpdFile());
				O_PJ_TKECMPRODUCT.setTkecmpdIsSharable(O_TKECMPRODUCTDOWNLOAD.getTkecmpdIsSharable());
				O_PJ_TKECMPRODUCT.setTkecmpdTitle(O_TKECMPRODUCTDOWNLOAD.getTkecmpdTitle());
				O_PJ_TKECMPRODUCT.setTkecmpdUrl(O_TKECMPRODUCTDOWNLOAD.getTkecmpdUrl());

			}
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
			O_PJ_TKECMPRODUCT.setLO_PJ_TKECTOPTIONATTRIBUTE(LO_PJ_TKECTOPTIONATTRIBUTE);

			List<PJ_TKECMIMAGE> LO_PJ_TKECMIMAGE = new ArrayList<PJ_TKECMIMAGE>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECMPRODUCT.getTkecmpId());

			List<TKECMIMAGE> LO_TKECMIMAGE = imageQuery.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				PJ_TKECMIMAGE O_PJ_TKECMIMAGE = new PJ_TKECMIMAGE();

				O_PJ_TKECMIMAGE.setTkecmiUrl(O_TKECMIMAGE.getTkecmiUrl());
				O_PJ_TKECMIMAGE.setTkecmiFileType(O_TKECMIMAGE.getTkecmiFileType());
				O_PJ_TKECMIMAGE.setTkecmpiFileName(O_TKECMIMAGE.getTkecmpiFileName());
				LO_PJ_TKECMIMAGE.add(O_PJ_TKECMIMAGE);

			}

			O_PJ_TKECMPRODUCT.setLO_PJ_TKECMIMAGE(LO_PJ_TKECMIMAGE);

		}

		return O_PJ_TKECMPRODUCT;
	}

}
