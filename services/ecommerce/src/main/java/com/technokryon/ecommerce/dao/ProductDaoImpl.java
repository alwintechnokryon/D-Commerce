package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
import com.technokryon.ecommerce.pojo.PRODUCTDOWNLOAD;

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

		String productQuery = "FROM TKECMPRODUCT WHERE categoryId.categoryId =:categoryId AND defaultYN=:default";

		String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE productId.id =:productId";

		String imageQuery = "FROM TKECMIMAGE WHERE productId.id =:productId";

		Query query = O_SessionFactory.getCurrentSession().createQuery(productQuery);

		int pageSize = 5;
		query.setFirstResult((PageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		query.setParameter("categoryId", categoryId);
		query.setParameter("default", "Y");

		List<TKECMPRODUCT> LO_TKECMPRODUCT = query.list();

		for (TKECMPRODUCT O_TKECMPRODUCT : LO_TKECMPRODUCT) {

			PRODUCT O_PRODUCT = new PRODUCT();

			O_PRODUCT.setId(O_TKECMPRODUCT.getId());
			O_PRODUCT.setName(O_TKECMPRODUCT.getName());
			O_PRODUCT.setPrice(O_TKECMPRODUCT.getPrice());

			List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OPTIONATTRIBUTE O_OPTIONATTRIBUTE = O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

//				O_OPTIONATTRIBUTE
//						.setAttributeId(O_TKECTPRODUCTATTRIBUTE.getOptionAttributeId().getAttributeId().getId());
//				O_OPTIONATTRIBUTE.setName(O_TKECTPRODUCTATTRIBUTE.getOptionAttributeId().getName());
				LO_OPTIONATTRIBUTE.add(O_OPTIONATTRIBUTE);
			}
			O_PRODUCT.setLO_OPTIONATTRIBUTE(LO_OPTIONATTRIBUTE);

			List<IMAGE> LO_IMAGE = new ArrayList<IMAGE>();

			Query query1 = O_SessionFactory.getCurrentSession().createQuery(imageQuery);

			query1.setParameter("productId", O_TKECMPRODUCT.getId());

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

		String configurableProduct = "FROM TKECTCONFIGURABLELINK WHERE productId.id =:productId";

		String configurableParent = "FROM TKECTCONFIGURABLELINK WHERE parentId.id =:parentId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE productId.id =:proAttrProductId ";

		String image = "FROM TKECMIMAGE WHERE productId.id =:productId";

		Query configurableProductQuery = O_SessionFactory.getCurrentSession().createQuery(configurableProduct);

		configurableProductQuery.setParameter("productId", tkecmpId);

		TKECTCONFIGURABLELINK O_TKECTCONFIGURABLELINK = (TKECTCONFIGURABLELINK) configurableProductQuery.uniqueResult();

		if (O_TKECTCONFIGURABLELINK != null) {

			O_PRODUCT.setId(tkecmpId);
			O_PRODUCT.setName(O_TKECTCONFIGURABLELINK.getProductId().getName());
			O_PRODUCT.setPrice(O_TKECTCONFIGURABLELINK.getProductId().getPrice());
			O_PRODUCT.setShortDesc(O_TKECTCONFIGURABLELINK.getProductId().getShortDesc());
			O_PRODUCT.setLongDesc(O_TKECTCONFIGURABLELINK.getProductId().getLongDesc());
			O_PRODUCT.setWeight(O_TKECTCONFIGURABLELINK.getProductId().getWeight());
			O_PRODUCT.setQuantity(O_TKECTCONFIGURABLELINK.getProductId().getQuantity());
			O_PRODUCT.setCountryOfMfg(O_TKECTCONFIGURABLELINK.getProductId().getCountryOfMfg());
			O_PRODUCT.setType(O_TKECTCONFIGURABLELINK.getProductId().getType().getAgId());

			O_PRODUCT.setLO_CATEGORY(
					getCategory(O_TKECTCONFIGURABLELINK.getProductId().getCategoryId().getCategoryId()));

			Query configurableParentQuery = O_SessionFactory.getCurrentSession().createQuery(configurableParent);

			configurableParentQuery.setParameter("parentId", O_TKECTCONFIGURABLELINK.getParentId().getId());

			List<TKECTCONFIGURABLELINK> LO_TKECTCONFIGURABLELINK = configurableParentQuery.list();

			List<PRODUCTATTRIBUTE> LO_PRODUCTATTRIBUTE = new ArrayList<PRODUCTATTRIBUTE>();

			System.err.println(LO_TKECTCONFIGURABLELINK.size());

			for (TKECTCONFIGURABLELINK child_TKECTCONFIGURABLELINK : LO_TKECTCONFIGURABLELINK) {

				Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

				productAttributeQuery.setParameter("proAttrProductId",
						child_TKECTCONFIGURABLELINK.getProductId().getId());

				PRODUCTATTRIBUTE O_PRODUCTATTRIBUTE = new PRODUCTATTRIBUTE();

				List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.list();

				System.err.println("ReqId" + tkecmpId);

				System.err.println("Id" + child_TKECTCONFIGURABLELINK.getProductId().getId());

				O_PRODUCTATTRIBUTE.setSubProductId(child_TKECTCONFIGURABLELINK.getProductId().getId());

				if (tkecmpId.equals(child_TKECTCONFIGURABLELINK.getProductId().getId())) {

					O_PRODUCTATTRIBUTE.setDefaultYN("Y");

				} else {

					O_PRODUCTATTRIBUTE.setDefaultYN("N");

				}

				List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE = new ArrayList<>();

				for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

					OPTIONATTRIBUTE O_OPTIONATTRIBUTE = O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE,
							OPTIONATTRIBUTE.class);

//					O_OPTIONATTRIBUTE
//							.setAttributeId(O_TKECTPRODUCTATTRIBUTE.getOptionAttributeId().getAttributeId().getId());
//					O_OPTIONATTRIBUTE.setName(O_TKECTPRODUCTATTRIBUTE.getOptionAttributeId().getName());
					LO_OPTIONATTRIBUTE.add(O_OPTIONATTRIBUTE);
				}

				O_PRODUCTATTRIBUTE.setLO_OPTIONATTRIBUTE(LO_OPTIONATTRIBUTE);

				LO_PRODUCTATTRIBUTE.add(O_PRODUCTATTRIBUTE);

			}
			O_PRODUCT.setLO_PRODUCTATTRIBUTE(LO_PRODUCTATTRIBUTE);

			List<IMAGE> LO_IMAGE = new ArrayList<IMAGE>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECTCONFIGURABLELINK.getProductId().getId());

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

			String simpleProduct = "FROM TKECMPRODUCT WHERE id =:productId";

			String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE productId.id =:productId";

			String downloadableProduct = "FROM TKECMPRODUCTDOWNLOAD WHERE productId.id =:downloadProductId";

			Query simpleProductQuery = O_SessionFactory.getCurrentSession().createQuery(simpleProduct);

			simpleProductQuery.setParameter("productId", tkecmpId);

			TKECMPRODUCT O_TKECMPRODUCT = (TKECMPRODUCT) simpleProductQuery.uniqueResult();

			if (O_TKECMPRODUCT == null) {

				return null;
			}

			O_PRODUCT.setId(O_TKECMPRODUCT.getId());
			O_PRODUCT.setName(O_TKECMPRODUCT.getName());
			O_PRODUCT.setLongDesc(O_TKECMPRODUCT.getLongDesc());
			O_PRODUCT.setShortDesc(O_TKECMPRODUCT.getShortDesc());
			O_PRODUCT.setCountryOfMfg(O_TKECMPRODUCT.getCountryOfMfg());
			O_PRODUCT.setQuantity(O_TKECMPRODUCT.getQuantity());
			O_PRODUCT.setType(O_TKECMPRODUCT.getType().getAgId());
			O_PRODUCT.setPrice(O_TKECMPRODUCT.getPrice());
			O_PRODUCT.setLO_CATEGORY(getCategory(O_TKECMPRODUCT.getCategoryId().getCategoryId()));
			if (O_TKECMPRODUCT.getType().getAgId().equals(3)) {

				Query downloadableProductQuery = O_SessionFactory.getCurrentSession().createQuery(downloadableProduct);

				downloadableProductQuery.setParameter("downloadProductId", tkecmpId);

				TKECMPRODUCTDOWNLOAD O_TKECMPRODUCTDOWNLOAD = (TKECMPRODUCTDOWNLOAD) downloadableProductQuery
						.uniqueResult();

				O_PRODUCT.setFile(O_TKECMPRODUCTDOWNLOAD.getFile());
				O_PRODUCT.setIsSharable(O_TKECMPRODUCTDOWNLOAD.getIsSharable());
				O_PRODUCT.setTitle(O_TKECMPRODUCTDOWNLOAD.getTitle());
				O_PRODUCT.setUrl(O_TKECMPRODUCTDOWNLOAD.getUrl());

			}
			List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OPTIONATTRIBUTE O_OPTIONATTRIBUTE = O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

//				O_OPTIONATTRIBUTE
//						.setAttributeId(O_TKECTPRODUCTATTRIBUTE.getOptionAttributeId().getAttributeId().getId());
//				O_OPTIONATTRIBUTE.setName(O_TKECTPRODUCTATTRIBUTE.getOptionAttributeId().getName());
				LO_OPTIONATTRIBUTE.add(O_OPTIONATTRIBUTE);
			}
			O_PRODUCT.setLO_OPTIONATTRIBUTE(LO_OPTIONATTRIBUTE);

			List<IMAGE> LO_IMAGE = new ArrayList<IMAGE>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECMPRODUCT.getId());

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

			String categoryById = "FROM TKECMCATEGORY WHERE categoryId =: categoryId";

			Query categoryIdQuery = O_SessionFactory.getCurrentSession().createQuery(categoryById);

			categoryIdQuery.setParameter("categoryId", categoryId);

			TKECMCATEGORY O_CATEGORY = (TKECMCATEGORY) categoryIdQuery.uniqueResult();

			CATEGORY OM_CATEGORY = O_ModelMapper.map(O_CATEGORY, CATEGORY.class);

			LO_CATEGORY.add(OM_CATEGORY);

			categoryId = O_CATEGORY.getParentId();

			if (categoryId == null) {
				break;
			}
		}
		return LO_CATEGORY;

	}
}