package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMCATEGORY;
import com.technokryon.ecommerce.model.TKECMIMAGE;
import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECMPRODUCTDOWNLOAD;
import com.technokryon.ecommerce.model.TKECTCONFIGURABLELINK;
import com.technokryon.ecommerce.model.TKECTPRODUCTATTRIBUTE;
import com.technokryon.ecommerce.pojo.CATEGORY;
import com.technokryon.ecommerce.pojo.IMAGE;
import com.technokryon.ecommerce.pojo.OPTIONATTRIBUTE;
import com.technokryon.ecommerce.pojo.PRODUCT;
import com.technokryon.ecommerce.pojo.PRODUCTATTRIBUTE;

@Repository("ProductDao")
@Transactional
@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public List<PRODUCT> getListByCategory(String categoryId, Integer PageNumber) {

		// ModelMapper O_ModelMapper = new ModelMapper();

		List<PRODUCT> LO_PRODUCT = new ArrayList<>();

		String productQuery = "FROM TKECMPRODUCT WHERE pTkecmcCategoryId.cCategoryId =:categoryId AND pDefaultYN=:default";

		String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:productId";

		String imageQuery = "FROM TKECMIMAGE WHERE iTkecmpId.pId =:productId";

		Query query = O_SessionFactory.getCurrentSession().createQuery(productQuery);

		int pageSize = 5;
		query.setFirstResult((PageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		query.setParameter("categoryId", categoryId);
		query.setParameter("default", "Y");

		List<TKECMPRODUCT> LO_TKECMPRODUCT = query.list();

		for (TKECMPRODUCT O_TKECMPRODUCT : LO_TKECMPRODUCT) {

			PRODUCT O_PRODUCT = new PRODUCT();

			O_PRODUCT.setPId(O_TKECMPRODUCT.getPId());
			O_PRODUCT.setPName(O_TKECMPRODUCT.getPName());
			O_PRODUCT.setPPrice(O_TKECMPRODUCT.getPPrice());

			List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OPTIONATTRIBUTE O_OPTIONATTRIBUTE = new OPTIONATTRIBUTE();

				// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

				O_OPTIONATTRIBUTE.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
				O_OPTIONATTRIBUTE.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
				LO_OPTIONATTRIBUTE.add(O_OPTIONATTRIBUTE);
			}
			O_PRODUCT.setLO_OPTIONATTRIBUTE(LO_OPTIONATTRIBUTE);

			List<IMAGE> LO_IMAGE = new ArrayList<IMAGE>();

			Query query1 = O_SessionFactory.getCurrentSession().createQuery(imageQuery);

			query1.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECMIMAGE> LO_TKECMIMAGE = query1.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				IMAGE O_IMAGE = O_ModelMapper.map(O_TKECMIMAGE, IMAGE.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				LO_IMAGE.add(O_IMAGE);

			}

			O_PRODUCT.setLO_IMAGE(LO_IMAGE);
			LO_PRODUCT.add(O_PRODUCT);
		}
		return LO_PRODUCT;
	}

	@Override
	public PRODUCT getDetailById(String tkecmpId) {

		// ModelMapper O_ModelMapper = new ModelMapper();

		PRODUCT O_PRODUCT = new PRODUCT();

		String configurableProduct = "FROM TKECTCONFIGURABLELINK WHERE clTkecmpId.pId =:productId";

		String configurableParent = "FROM TKECTCONFIGURABLELINK WHERE clParentId.pId =:parentId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		String image = "FROM TKECMIMAGE WHERE iTkecmpId.pId =:productId";

		Query configurableProductQuery = O_SessionFactory.getCurrentSession().createQuery(configurableProduct);

		configurableProductQuery.setParameter("productId", tkecmpId);

		TKECTCONFIGURABLELINK O_TKECTCONFIGURABLELINK = (TKECTCONFIGURABLELINK) configurableProductQuery.uniqueResult();

		if (O_TKECTCONFIGURABLELINK != null) {

			O_PRODUCT.setPId(tkecmpId);
			O_PRODUCT.setPName(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPName());
			O_PRODUCT.setPPrice(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPPrice());
			O_PRODUCT.setPShortDesc(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPShortDesc());
			O_PRODUCT.setPLongDesc(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPLongDesc());
			O_PRODUCT.setPWeight(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPWeight());
			O_PRODUCT.setPQuantity(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPQuantity());
			O_PRODUCT.setPCountryOfMfg(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPCountryOfMfg());
			O_PRODUCT.setPTkecmptAgId(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPTkecmptAgId().getPtAgId());

			O_PRODUCT.setLO_CATEGORY(
					getCategory(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPTkecmcCategoryId().getCCategoryId()));

			Query configurableParentQuery = O_SessionFactory.getCurrentSession().createQuery(configurableParent);

			configurableParentQuery.setParameter("parentId", O_TKECTCONFIGURABLELINK.getClParentId().getPId());

			List<TKECTCONFIGURABLELINK> LO_TKECTCONFIGURABLELINK = configurableParentQuery.list();

			List<PRODUCTATTRIBUTE> LO_PRODUCTATTRIBUTE = new ArrayList<PRODUCTATTRIBUTE>();

			System.err.println(LO_TKECTCONFIGURABLELINK.size());

			for (TKECTCONFIGURABLELINK child_TKECTCONFIGURABLELINK : LO_TKECTCONFIGURABLELINK) {

				Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

				productAttributeQuery.setParameter("proAttrProductId",
						child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				PRODUCTATTRIBUTE O_PRODUCTATTRIBUTE = new PRODUCTATTRIBUTE();

				List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.list();

				System.err.println("ReqId" + tkecmpId);

				System.err.println("Id" + child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				O_PRODUCTATTRIBUTE.setSubProductId(child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				if (tkecmpId.equals(child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId())) {

					O_PRODUCTATTRIBUTE.setDefaultYN("Y");

				} else {

					O_PRODUCTATTRIBUTE.setDefaultYN("N");

				}

				List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE = new ArrayList<>();

				for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

					OPTIONATTRIBUTE O_OPTIONATTRIBUTE = new OPTIONATTRIBUTE();

					// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

					O_OPTIONATTRIBUTE.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
					O_OPTIONATTRIBUTE.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
					LO_OPTIONATTRIBUTE.add(O_OPTIONATTRIBUTE);
				}

				O_PRODUCTATTRIBUTE.setLO_OPTIONATTRIBUTE(LO_OPTIONATTRIBUTE);

				LO_PRODUCTATTRIBUTE.add(O_PRODUCTATTRIBUTE);

			}
			O_PRODUCT.setLO_PRODUCTATTRIBUTE(LO_PRODUCTATTRIBUTE);

			List<IMAGE> LO_IMAGE = new ArrayList<IMAGE>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

			List<TKECMIMAGE> LO_TKECMIMAGE = imageQuery.list();

			System.err.println(LO_TKECMIMAGE);

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				IMAGE O_IMAGE = O_ModelMapper.map(O_TKECMIMAGE, IMAGE.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				LO_IMAGE.add(O_IMAGE);

			}

			O_PRODUCT.setLO_IMAGE(LO_IMAGE);

		} else {

			String simpleProduct = "FROM TKECMPRODUCT WHERE pId =:productId";

			String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:productId";

			String downloadableProduct = "FROM TKECMPRODUCTDOWNLOAD WHERE pdTkecmpId.pId =:downloadProductId";

			Query simpleProductQuery = O_SessionFactory.getCurrentSession().createQuery(simpleProduct);

			simpleProductQuery.setParameter("productId", tkecmpId);

			TKECMPRODUCT O_TKECMPRODUCT = (TKECMPRODUCT) simpleProductQuery.uniqueResult();

			if (O_TKECMPRODUCT == null) {

				return null;
			}

			O_PRODUCT.setPId(O_TKECMPRODUCT.getPId());
			O_PRODUCT.setPName(O_TKECMPRODUCT.getPName());
			O_PRODUCT.setPLongDesc(O_TKECMPRODUCT.getPLongDesc());
			O_PRODUCT.setPShortDesc(O_TKECMPRODUCT.getPShortDesc());
			O_PRODUCT.setPCountryOfMfg(O_TKECMPRODUCT.getPCountryOfMfg());
			O_PRODUCT.setPQuantity(O_TKECMPRODUCT.getPQuantity());
			O_PRODUCT.setPTkecmptAgId(O_TKECMPRODUCT.getPTkecmptAgId().getPtAgId());
			O_PRODUCT.setPPrice(O_TKECMPRODUCT.getPPrice());
			O_PRODUCT.setLO_CATEGORY(getCategory(O_TKECMPRODUCT.getPTkecmcCategoryId().getCCategoryId()));
			if (O_TKECMPRODUCT.getPTkecmptAgId().getPtAgId().equals(3)) {

				Query downloadableProductQuery = O_SessionFactory.getCurrentSession().createQuery(downloadableProduct);

				downloadableProductQuery.setParameter("downloadProductId", tkecmpId);

				TKECMPRODUCTDOWNLOAD O_TKECMPRODUCTDOWNLOAD = (TKECMPRODUCTDOWNLOAD) downloadableProductQuery
						.uniqueResult();

				O_PRODUCT.setPdFile(O_TKECMPRODUCTDOWNLOAD.getPdFile());
				O_PRODUCT.setPdIsSharable(O_TKECMPRODUCTDOWNLOAD.getPdIsSharable());
				O_PRODUCT.setPdTitle(O_TKECMPRODUCTDOWNLOAD.getPdTitle());
				O_PRODUCT.setPdUrl(O_TKECMPRODUCTDOWNLOAD.getPdUrl());

			}
			List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OPTIONATTRIBUTE O_OPTIONATTRIBUTE = new OPTIONATTRIBUTE();
				// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

				O_OPTIONATTRIBUTE.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
				O_OPTIONATTRIBUTE.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
				LO_OPTIONATTRIBUTE.add(O_OPTIONATTRIBUTE);
			}
			O_PRODUCT.setLO_OPTIONATTRIBUTE(LO_OPTIONATTRIBUTE);

			List<IMAGE> LO_IMAGE = new ArrayList<IMAGE>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECMIMAGE> LO_TKECMIMAGE = imageQuery.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				IMAGE O_IMAGE = O_ModelMapper.map(O_TKECMIMAGE, IMAGE.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				LO_IMAGE.add(O_IMAGE);

			}

			O_PRODUCT.setLO_IMAGE(LO_IMAGE);

		}

		return O_PRODUCT;
	}

	@Cacheable("CATEGORY")
	private List<CATEGORY> getCategory(String categoryId) {

		List<CATEGORY> LO_CATEGORY = new ArrayList<>();

		while (categoryId != null) {

			String categoryById = "FROM TKECMCATEGORY WHERE cCategoryId =: categoryId";

			Query categoryIdQuery = O_SessionFactory.getCurrentSession().createQuery(categoryById);

			categoryIdQuery.setParameter("categoryId", categoryId);

			TKECMCATEGORY O_CATEGORY = (TKECMCATEGORY) categoryIdQuery.uniqueResult();

			CATEGORY OM_CATEGORY = O_ModelMapper.map(O_CATEGORY, CATEGORY.class);

			LO_CATEGORY.add(OM_CATEGORY);

			categoryId = O_CATEGORY.getCParentId();

			if (categoryId == null) {
				break;
			}
		}
		return LO_CATEGORY;

	}
}