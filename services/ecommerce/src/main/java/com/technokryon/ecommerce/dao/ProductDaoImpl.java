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
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public List<Product> getListByCategory(String categoryId, Integer PageNumber) {

		List<Product> LO_Product = new ArrayList<>();

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

			Product O_Product = new Product();

			O_Product.setPId(O_TKECMPRODUCT.getPId());
			O_Product.setPName(O_TKECMPRODUCT.getPName());
			O_Product.setPPrice(O_TKECMPRODUCT.getPPrice());

			List<OptionAttribute> LO_OptionAttribute = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OptionAttribute O_OptionAttribute = new OptionAttribute();

				// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

				O_OptionAttribute.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
				O_OptionAttribute.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
				LO_OptionAttribute.add(O_OptionAttribute);
			}
			O_Product.setLO_OPTIONATTRIBUTE(LO_OptionAttribute);

			List<Image> LO_Image = new ArrayList<Image>();

			Query query1 = O_SessionFactory.getCurrentSession().createQuery(imageQuery);

			query1.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECMIMAGE> LO_TKECMIMAGE = query1.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				Image O_Image = O_ModelMapper.map(O_TKECMIMAGE, Image.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				LO_Image.add(O_Image);

			}

			O_Product.setLO_IMAGE(LO_Image);
			LO_Product.add(O_Product);
		}
		return LO_Product;
	}

	@Override
	public Product getDetailById(String tkecmpId) {

		// ModelMapper O_ModelMapper = new ModelMapper();

		Product O_Product = new Product();

		String configurableProduct = "FROM TKECTCONFIGURABLELINK WHERE clTkecmpId.pId =:productId";

		String configurableParent = "FROM TKECTCONFIGURABLELINK WHERE clParentId.pId =:parentId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		String image = "FROM TKECMIMAGE WHERE iTkecmpId.pId =:productId";

		Query configurableProductQuery = O_SessionFactory.getCurrentSession().createQuery(configurableProduct);

		configurableProductQuery.setParameter("productId", tkecmpId);

		TKECTCONFIGURABLELINK O_TKECTCONFIGURABLELINK = (TKECTCONFIGURABLELINK) configurableProductQuery.uniqueResult();

		if (O_TKECTCONFIGURABLELINK != null) {

			O_Product.setPId(tkecmpId);
			O_Product.setPName(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPName());
			O_Product.setPPrice(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPPrice());
			O_Product.setPShortDesc(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPShortDesc());
			O_Product.setPLongDesc(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPLongDesc());
			O_Product.setPWeight(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPWeight());
			O_Product.setPQuantity(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPQuantity());
			O_Product.setPCountryOfMfg(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPCountryOfMfg());
			O_Product.setPTkecmptAgId(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPTkecmptAgId().getPtId());

			O_Product.setLO_CATEGORY(
					getCategory(O_TKECTCONFIGURABLELINK.getClTkecmpId().getPTkecmcCategoryId().getCCategoryId()));

			Query configurableParentQuery = O_SessionFactory.getCurrentSession().createQuery(configurableParent);

			configurableParentQuery.setParameter("parentId", O_TKECTCONFIGURABLELINK.getClParentId().getPId());

			List<TKECTCONFIGURABLELINK> LO_TKECTCONFIGURABLELINK = configurableParentQuery.list();

			List<ProductAttribute> LO_ProductAttribute = new ArrayList<ProductAttribute>();

			System.err.println(LO_TKECTCONFIGURABLELINK.size());

			for (TKECTCONFIGURABLELINK child_TKECTCONFIGURABLELINK : LO_TKECTCONFIGURABLELINK) {

				Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

				productAttributeQuery.setParameter("proAttrProductId",
						child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				ProductAttribute O_ProductAttribute = new ProductAttribute();

				List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.list();

				System.err.println("ReqId" + tkecmpId);

				System.err.println("Id" + child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				O_ProductAttribute.setSubProductId(child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

				if (tkecmpId.equals(child_TKECTCONFIGURABLELINK.getClTkecmpId().getPId())) {

					O_ProductAttribute.setDefaultYN("Y");

				} else {

					O_ProductAttribute.setDefaultYN("N");

				}

				List<OptionAttribute> LO_OptionAttribute = new ArrayList<>();

				for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

					OptionAttribute O_OptionAttribute = new OptionAttribute();

					// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

					O_OptionAttribute.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
					O_OptionAttribute.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
					LO_OptionAttribute.add(O_OptionAttribute);
				}

				O_ProductAttribute.setLO_OPTIONATTRIBUTE(LO_OptionAttribute);

				LO_ProductAttribute.add(O_ProductAttribute);

			}
			O_Product.setLO_PRODUCTATTRIBUTE(LO_ProductAttribute);

			List<Image> LO_Image = new ArrayList<Image>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECTCONFIGURABLELINK.getClTkecmpId().getPId());

			List<TKECMIMAGE> LO_TKECMIMAGE = imageQuery.list();

			System.err.println(LO_TKECMIMAGE);

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				Image O_Image = O_ModelMapper.map(O_TKECMIMAGE, Image.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				LO_Image.add(O_Image);

			}

			O_Product.setLO_IMAGE(LO_Image);

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

			O_Product.setPId(O_TKECMPRODUCT.getPId());
			O_Product.setPName(O_TKECMPRODUCT.getPName());
			O_Product.setPLongDesc(O_TKECMPRODUCT.getPLongDesc());
			O_Product.setPShortDesc(O_TKECMPRODUCT.getPShortDesc());
			O_Product.setPCountryOfMfg(O_TKECMPRODUCT.getPCountryOfMfg());
			O_Product.setPQuantity(O_TKECMPRODUCT.getPQuantity());
			O_Product.setPTkecmptAgId(O_TKECMPRODUCT.getPTkecmptAgId().getPtId());
			O_Product.setPPrice(O_TKECMPRODUCT.getPPrice());
			O_Product.setLO_CATEGORY(getCategory(O_TKECMPRODUCT.getPTkecmcCategoryId().getCCategoryId()));
			if (O_TKECMPRODUCT.getPTkecmptAgId().getPtId().equals("TKECMPT0003")) {

				Query downloadableProductQuery = O_SessionFactory.getCurrentSession().createQuery(downloadableProduct);

				downloadableProductQuery.setParameter("downloadProductId", tkecmpId);

				TKECMPRODUCTDOWNLOAD O_TKECMPRODUCTDOWNLOAD = (TKECMPRODUCTDOWNLOAD) downloadableProductQuery
						.uniqueResult();

				O_Product.setPdFile(O_TKECMPRODUCTDOWNLOAD.getPdFile());
				O_Product.setPdIsSharable(O_TKECMPRODUCTDOWNLOAD.getPdIsSharable());
				O_Product.setPdTitle(O_TKECMPRODUCTDOWNLOAD.getPdTitle());
				O_Product.setPdUrl(O_TKECMPRODUCTDOWNLOAD.getPdUrl());

			}
			List<OptionAttribute> LO_OptionAttribute = new ArrayList<>();

			Query queryProductAttribute = O_SessionFactory.getCurrentSession().createQuery(proAttrQuery);

			queryProductAttribute.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = queryProductAttribute.list();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OptionAttribute O_OptionAttribute = new OptionAttribute();
				// O_ModelMapper.map(O_TKECTPRODUCTATTRIBUTE, OPTIONATTRIBUTE.class);

				O_OptionAttribute.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
				O_OptionAttribute.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
				LO_OptionAttribute.add(O_OptionAttribute);
			}
			O_Product.setLO_OPTIONATTRIBUTE(LO_OptionAttribute);

			List<Image> LO_Image = new ArrayList<Image>();

			Query imageQuery = O_SessionFactory.getCurrentSession().createQuery(image);

			imageQuery.setParameter("productId", O_TKECMPRODUCT.getPId());

			List<TKECMIMAGE> LO_TKECMIMAGE = imageQuery.list();

			for (TKECMIMAGE O_TKECMIMAGE : LO_TKECMIMAGE) {

				Image O_Image = O_ModelMapper.map(O_TKECMIMAGE, Image.class);

//				O_IMAGE.setUrl(O_TKECMIMAGE.getUrl());
//				O_IMAGE.setFileType(O_TKECMIMAGE.getFileType());
//				O_IMAGE.setFileName(O_TKECMIMAGE.getFileName());
				LO_Image.add(O_Image);

			}

			O_Product.setLO_IMAGE(LO_Image);

		}

		return O_Product;
	}

	@Cacheable("CATEGORY")
	private List<Category> getCategory(String categoryId) {

		List<Category> LO_Category = new ArrayList<>();

		while (categoryId != null) {

			String categoryById = "FROM TKECMCATEGORY WHERE cCategoryId =: categoryId";

			Query categoryIdQuery = O_SessionFactory.getCurrentSession().createQuery(categoryById);

			categoryIdQuery.setParameter("categoryId", categoryId);

			TKECMCATEGORY O_TKECMCATEGORY = (TKECMCATEGORY) categoryIdQuery.uniqueResult();

			Category OM_Category = O_ModelMapper.map(O_TKECMCATEGORY, Category.class);

			LO_Category.add(OM_Category);

			categoryId = O_TKECMCATEGORY.getCParentId();

			if (categoryId == null) {
				break;
			}
		}
		return LO_Category;

	}
}