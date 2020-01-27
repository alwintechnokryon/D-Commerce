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
import com.technokryon.ecommerce.model.TKECTWISHLIST;
import com.technokryon.ecommerce.pojo.Category;
import com.technokryon.ecommerce.pojo.Image;
import com.technokryon.ecommerce.pojo.OptionAttribute;
import com.technokryon.ecommerce.pojo.Product;
import com.technokryon.ecommerce.pojo.ProductAttribute;

@Repository("ProductDao")
@Transactional
@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Product> getListByCategory(String categoryId, Integer PageNumber, String uId) {

		List<Product> product = new ArrayList<>();

		String productQuery = "FROM TKECMPRODUCT WHERE pTkecmcCategoryId.cCategoryId =:categoryId AND pDefaultYN =:default";

		String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:productId";

		String imageQuery = "FROM TKECMIMAGE WHERE iTkecmpId.pId =:productId";

		String productWishList = "FROM TKECTWISHLIST WHERE wlUserId =:userId AND wlTkecmpId.pId =:productId";

		Query query = sessionFactory.getCurrentSession().createQuery(productQuery);

		int pageSize = 5;
		query.setFirstResult((PageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		query.setParameter("categoryId", categoryId);
		query.setParameter("default", "Y");

		List<TKECMPRODUCT> tKECMPRODUCT = query.getResultList();

		for (TKECMPRODUCT tKECMPRODUCT1 : tKECMPRODUCT) {

			Product product1 = new Product();

			product1.setPId(tKECMPRODUCT1.getPId());
			product1.setPName(tKECMPRODUCT1.getPName());
			product1.setPPrice(tKECMPRODUCT1.getPPrice());

			List<OptionAttribute> optionAttribute = new ArrayList<>();

			Query queryProductAttribute = sessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", tKECMPRODUCT1.getPId());

			List<TKECTPRODUCTATTRIBUTE> tKECTPRODUCTATTRIBUTE = queryProductAttribute.getResultList();

			for (TKECTPRODUCTATTRIBUTE tKECTPRODUCTATTRIBUTE1 : tKECTPRODUCTATTRIBUTE) {

				OptionAttribute optionAttribute1 = new OptionAttribute();

				// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

				optionAttribute1.setOaTkecmaId(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaTkecmaId().getAId());
				optionAttribute1.setOaName(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaName());
				optionAttribute.add(optionAttribute1);
			}
			product1.setLO_OPTIONATTRIBUTE(optionAttribute);

			List<Image> image = new ArrayList<Image>();

			Query query1 = sessionFactory.getCurrentSession().createQuery(imageQuery);

			query1.setParameter("productId", tKECMPRODUCT1.getPId());

			List<TKECMIMAGE> tKECMIMAGE = query1.getResultList();

			for (TKECMIMAGE tKECMIMAGE1 : tKECMIMAGE) {

				Image image1 = modelMapper.map(tKECMIMAGE1, Image.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				image.add(image1);

			}

			product1.setLO_IMAGE(image);

			if (uId != null) {

				Query productWishListQuery = sessionFactory.getCurrentSession().createQuery(productWishList);

				productWishListQuery.setParameter("productId", tKECMPRODUCT1.getPId());
				productWishListQuery.setParameter("userId", uId);
				TKECTWISHLIST tKECTWISHLIST = (TKECTWISHLIST) productWishListQuery.uniqueResult();

				if (tKECTWISHLIST != null) {

					product1.setWlAgId(tKECTWISHLIST.getWlAgId());
				}

			}
			product.add(product1);
		}
		return product;
	}

	@Override
	public Product getDetailById(String tkecmpId, String uId) {

		// ModelMapper O_ModelMapper = new ModelMapper();

		Product product = new Product();

		String configurableProduct = "FROM TKECTCONFIGURABLELINK WHERE clTkecmpId.pId =:productId";

		String configurableParent = "FROM TKECTCONFIGURABLELINK WHERE clParentId.pId =:parentId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		String image = "FROM TKECMIMAGE WHERE iTkecmpId.pId =:productId";

		String productWishList = "FROM TKECTWISHLIST WHERE wlUserId =:userId AND wlTkecmpId.pId =:productId";

		Query configurableProductQuery = sessionFactory.getCurrentSession().createQuery(configurableProduct);

		configurableProductQuery.setParameter("productId", tkecmpId);

		TKECTCONFIGURABLELINK tKECTCONFIGURABLELINK = (TKECTCONFIGURABLELINK) configurableProductQuery.uniqueResult();

		if (tKECTCONFIGURABLELINK != null) {

			product.setPId(tkecmpId);
			product.setPName(tKECTCONFIGURABLELINK.getClTkecmpId().getPName());
			product.setPPrice(tKECTCONFIGURABLELINK.getClTkecmpId().getPPrice());
			product.setPShortDesc(tKECTCONFIGURABLELINK.getClTkecmpId().getPShortDesc());
			product.setPLongDesc(tKECTCONFIGURABLELINK.getClTkecmpId().getPLongDesc());
			product.setPWeight(tKECTCONFIGURABLELINK.getClTkecmpId().getPWeight());
			product.setPQuantity(tKECTCONFIGURABLELINK.getClTkecmpId().getPQuantity());
			product.setPCountryOfMfg(tKECTCONFIGURABLELINK.getClTkecmpId().getPCountryOfMfg());
			product.setPTkecmptId(tKECTCONFIGURABLELINK.getClTkecmpId().getPTkecmptId().getPtId());

			product.setLO_CATEGORY(
					getCategory(tKECTCONFIGURABLELINK.getClTkecmpId().getPTkecmcCategoryId().getCCategoryId()));

			Query configurableParentQuery = sessionFactory.getCurrentSession().createQuery(configurableParent);

			configurableParentQuery.setParameter("parentId", tKECTCONFIGURABLELINK.getClParentId().getPId());

			List<TKECTCONFIGURABLELINK> tKECTCONFIGURABLELINK1 = configurableParentQuery.getResultList();

			List<ProductAttribute> productAttribute1 = new ArrayList<ProductAttribute>();

//			System.err.println(LO_TKECTCONFIGURABLELINK.size());

			for (TKECTCONFIGURABLELINK child_TKECTCONFIGURABLELINK : tKECTCONFIGURABLELINK1) {

				Query productAttributeQuery = sessionFactory.getCurrentSession().createQuery(productAttribute);

				productAttributeQuery.setParameter("proAttrProductId",
						child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				ProductAttribute productAttribute2 = new ProductAttribute();

				List<TKECTPRODUCTATTRIBUTE> tKECTPRODUCTATTRIBUTE = productAttributeQuery.getResultList();

//				System.err.println("ReqId" + tkecmpId);

//				System.err.println("Id" + child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				productAttribute2.setSubProductId(child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				if (tkecmpId.equals(child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId())) {

					productAttribute2.setDefaultYN("Y");

				} else {

					productAttribute2.setDefaultYN("N");

				}

				List<OptionAttribute> optionAttribute = new ArrayList<>();

				for (TKECTPRODUCTATTRIBUTE tKECTPRODUCTATTRIBUTE1 : tKECTPRODUCTATTRIBUTE) {

					OptionAttribute optionAttribute1 = new OptionAttribute();

					// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

					optionAttribute1.setOaTkecmaId(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaTkecmaId().getAId());
					optionAttribute1.setOaName(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaName());
					optionAttribute.add(optionAttribute1);
				}

				productAttribute2.setLO_OPTIONATTRIBUTE(optionAttribute);

				productAttribute1.add(productAttribute2);

			}
			product.setLO_PRODUCTATTRIBUTE(productAttribute1);

		} else {

			String simpleProduct = "FROM TKECMPRODUCT WHERE pId =:productId";

			String proAttrQuery = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:productId";

			String downloadableProduct = "FROM TKECMPRODUCTDOWNLOAD WHERE pdTkecmpId.pId =:downloadProductId";

			Query simpleProductQuery = sessionFactory.getCurrentSession().createQuery(simpleProduct);

			simpleProductQuery.setParameter("productId", tkecmpId);

			TKECMPRODUCT tKECMPRODUCT = (TKECMPRODUCT) simpleProductQuery.uniqueResult();

			if (tKECMPRODUCT == null) {

				return null;
			}

			product.setPId(tKECMPRODUCT.getPId());
			product.setPName(tKECMPRODUCT.getPName());
			product.setPLongDesc(tKECMPRODUCT.getPLongDesc());
			product.setPShortDesc(tKECMPRODUCT.getPShortDesc());
			product.setPCountryOfMfg(tKECMPRODUCT.getPCountryOfMfg());
			product.setPQuantity(tKECMPRODUCT.getPQuantity());
			product.setPTkecmptId(tKECMPRODUCT.getPTkecmptId().getPtId());
			product.setPPrice(tKECMPRODUCT.getPPrice());
			product.setLO_CATEGORY(getCategory(tKECMPRODUCT.getPTkecmcCategoryId().getCCategoryId()));
			if (tKECMPRODUCT.getPTkecmptId().getPtId().equals("TKECMPT0003")) {

				Query downloadableProductQuery = sessionFactory.getCurrentSession().createQuery(downloadableProduct);

				downloadableProductQuery.setParameter("downloadProductId", tkecmpId);

				TKECMPRODUCTDOWNLOAD tKECMPRODUCTDOWNLOAD = (TKECMPRODUCTDOWNLOAD) downloadableProductQuery
						.uniqueResult();

				product.setPdFile(tKECMPRODUCTDOWNLOAD.getPdFile());
				product.setPdIsSharable(tKECMPRODUCTDOWNLOAD.getPdIsSharable());
				product.setPdTitle(tKECMPRODUCTDOWNLOAD.getPdTitle());
				product.setPdUrl(tKECMPRODUCTDOWNLOAD.getPdUrl());

			}
			List<OptionAttribute> optionAttribute = new ArrayList<>();

			Query queryProductAttribute = sessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", tKECMPRODUCT.getPId());

			List<TKECTPRODUCTATTRIBUTE> tKECTPRODUCTATTRIBUTE = queryProductAttribute.getResultList();

			for (TKECTPRODUCTATTRIBUTE tKECTPRODUCTATTRIBUTE1 : tKECTPRODUCTATTRIBUTE) {

				OptionAttribute optionAttribute1 = new OptionAttribute();
				// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

				optionAttribute1.setOaTkecmaId(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaTkecmaId().getAId());
				optionAttribute1.setOaName(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaName());
				optionAttribute.add(optionAttribute1);
			}
			product.setLO_OPTIONATTRIBUTE(optionAttribute);

		}

		List<Image> image1 = new ArrayList<Image>();

		Query imageQuery = sessionFactory.getCurrentSession().createQuery(image);

		imageQuery.setParameter("productId", tkecmpId);

		List<TKECMIMAGE> tKECMIMAGE = imageQuery.getResultList();

		for (TKECMIMAGE tKECMIMAGE1 : tKECMIMAGE) {

			Image image2 = modelMapper.map(tKECMIMAGE1, Image.class);

//			O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//			O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//			O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
			image1.add(image2);

		}

		product.setLO_IMAGE(image1);

		if (uId != null) {

			Query productWishListQuery = sessionFactory.getCurrentSession().createQuery(productWishList);

			productWishListQuery.setParameter("productId", tkecmpId);
			productWishListQuery.setParameter("userId", uId);
			TKECTWISHLIST tKECTWISHLIST = (TKECTWISHLIST) productWishListQuery.uniqueResult();

			if (tKECTWISHLIST != null) {

				product.setWlAgId(tKECTWISHLIST.getWlAgId());
			}

		}
		return product;
	}

	@Cacheable("CATEGORY")
	private List<Category> getCategory(String categoryId) {

		List<Category> category = new ArrayList<>();

		while (categoryId != null) {

			String categoryById = "FROM TKECMCATEGORY WHERE cCategoryId =: categoryId";

			Query categoryIdQuery = sessionFactory.getCurrentSession().createQuery(categoryById);

			categoryIdQuery.setParameter("categoryId", categoryId);

			TKECMCATEGORY tKECMCATEGORY = (TKECMCATEGORY) categoryIdQuery.uniqueResult();

			Category category1 = modelMapper.map(tKECMCATEGORY, Category.class);

			category.add(category1);

			categoryId = tKECMCATEGORY.getCParentId();

			if (categoryId == null) {
				break;
			}
		}
		return category;

	}
}