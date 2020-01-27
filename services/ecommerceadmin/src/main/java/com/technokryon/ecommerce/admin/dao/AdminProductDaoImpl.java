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
	private SessionFactory sessionFactory;

	@Override
	public Boolean checkSkuAvailable(String sku) {

		String checkSku = "FROM TKECMPRODUCT WHERE pSku =:productSku";

		Query checkSkuQuery = sessionFactory.getCurrentSession().createQuery(checkSku);

		checkSkuQuery.setParameter("productSku", sku);

		TKECMPRODUCT tKECMPRODUCT = (TKECMPRODUCT) checkSkuQuery.uniqueResult();

		if (tKECMPRODUCT == null) {

			return false;
		}

		return true;
	}

	@Override
	public String addProduct(Product product) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			TKECMPRODUCT tKECMPRODUCT = new TKECMPRODUCT();

			tKECMPRODUCT.setPId(product.getPName().toLowerCase().replace(" ", "-"));
			tKECMPRODUCT.setPSku(product.getPSku().toUpperCase());
			tKECMPRODUCT.setPName(product.getPName());
			tKECMPRODUCT.setPTkecmcCategoryId(session.get(TKECMCATEGORY.class, product.getPTkecmcCategoryId()));
			tKECMPRODUCT.setPWeight(product.getPWeight());
			tKECMPRODUCT.setPQuantity(product.getPQuantity());
			tKECMPRODUCT.setPShortDesc(product.getPShortDesc());
			tKECMPRODUCT.setPLongDesc(product.getPLongDesc());
			tKECMPRODUCT.setPCountryOfMfg(product.getPCountryOfMfg());
			tKECMPRODUCT.setPCreatedDate(OffsetDateTime.now());
			tKECMPRODUCT.setPCreatedUserId(product.getPCreatedUserId());
			tKECMPRODUCT.setPPrice(product.getPPrice());
			tKECMPRODUCT.setPStatus("N");
			tKECMPRODUCT.setPDefaultYN("Y");
			tKECMPRODUCT.setPTkecmptId(session.get(TKECMPRODUCTTYPE.class, product.getPTkecmptId()));

			session.save(tKECMPRODUCT);

			for (Image image : product.getLO_IMAGE()) {

				TKECMIMAGE tKECMIMAGE = new TKECMIMAGE();

				tKECMIMAGE.setITkecmpId(session.get(TKECMPRODUCT.class, tKECMPRODUCT.getPId()));
				tKECMIMAGE.setIFileName(image.getIFileName());
				tKECMIMAGE.setIFileType(image.getIFileType());

				session.save(tKECMIMAGE);
			}

			transaction.commit();
			session.close();
			return tKECMPRODUCT.getPId();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return null;
		}
	}

}
