package com.technokryon.ecommerce.admin.dao;

import java.time.OffsetDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.model.TKECMCATEGORY;
import com.technokryon.ecommerce.admin.model.TKECMIMAGE;
import com.technokryon.ecommerce.admin.model.TKECMPRODUCT;
import com.technokryon.ecommerce.admin.model.TKECMPRODUCTTYPE;
import com.technokryon.ecommerce.admin.pojo.Image;
import com.technokryon.ecommerce.admin.pojo.Product;

@Repository("AdminProductDao")
@Component
@Transactional
public class AdminProductDaoImpl implements AdminProductDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public Boolean checkSkuAvailable(String sku) {

		String checkSku = "FROM TKECMPRODUCT WHERE pSku =:productSku";

		Query checkSkuQuery = O_SessionFactory.getCurrentSession().createQuery(checkSku);

		checkSkuQuery.setParameter("productSku", sku);

		TKECMPRODUCT O_TKECMPRODUCT = (TKECMPRODUCT) checkSkuQuery.uniqueResult();

		if (O_TKECMPRODUCT == null) {

			return false;
		}

		return true;
	}

	@Override
	public String addProduct(Product O_Product) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {
			TKECMPRODUCT O_TKECMPRODUCT = new TKECMPRODUCT();

			O_TKECMPRODUCT.setPId(O_Product.getPName().toLowerCase().replace(" ", "-"));
			O_TKECMPRODUCT.setPSku(O_Product.getPSku().toUpperCase());
			O_TKECMPRODUCT.setPName(O_Product.getPName());
			O_TKECMPRODUCT.setPTkecmcCategoryId(O_Session.get(TKECMCATEGORY.class, O_Product.getPTkecmcCategoryId()));
			O_TKECMPRODUCT.setPWeight(O_Product.getPWeight());
			O_TKECMPRODUCT.setPQuantity(O_Product.getPQuantity());
			O_TKECMPRODUCT.setPShortDesc(O_Product.getPShortDesc());
			O_TKECMPRODUCT.setPLongDesc(O_Product.getPLongDesc());
			O_TKECMPRODUCT.setPCountryOfMfg(O_Product.getPCountryOfMfg());
			O_TKECMPRODUCT.setPCreatedDate(OffsetDateTime.now());
			O_TKECMPRODUCT.setPCreatedUserId(O_Product.getPCreatedUserId());
			O_TKECMPRODUCT.setPPrice(O_Product.getPPrice());
			O_TKECMPRODUCT.setPStatus("N");
			O_TKECMPRODUCT.setPDefaultYN("Y");
			O_TKECMPRODUCT.setPTkecmptId(O_Session.get(TKECMPRODUCTTYPE.class, O_Product.getPTkecmptId()));

			O_Session.save(O_TKECMPRODUCT);

			for (Image O_Image : O_Product.getLO_IMAGE()) {

				TKECMIMAGE O_TKECMIMAGE = new TKECMIMAGE();

				O_TKECMIMAGE.setITkecmpId(O_Session.get(TKECMPRODUCT.class, O_TKECMPRODUCT.getPId()));
				O_TKECMIMAGE.setIFileName(O_Image.getIFileName());
				O_TKECMIMAGE.setIFileType(O_Image.getIFileType());

				O_Session.save(O_TKECMIMAGE);
			}

			O_Transaction.commit();
			O_Session.close();
			return O_TKECMPRODUCT.getPId();

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
