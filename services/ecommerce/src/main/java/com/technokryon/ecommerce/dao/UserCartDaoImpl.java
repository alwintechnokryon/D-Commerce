package com.technokryon.ecommerce.dao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTPRODUCTCART;
import com.technokryon.ecommerce.pojo.ProductCart;

@Repository("UserCartDao")
@Transactional
@Component
public class UserCartDaoImpl implements UserCartDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void addToCart(ProductCart productCart) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			TKECTPRODUCTCART tKECTPRODUCTCART = new TKECTPRODUCTCART();

			tKECTPRODUCTCART.setPcTkecmuId(session.get(TKECMUSER.class, productCart.getPcTkecmuId()));
			tKECTPRODUCTCART.setPcTkecmpId(session.get(TKECMPRODUCT.class, productCart.getPcTkecmpId()));
			tKECTPRODUCTCART.setPcQuantity(productCart.getPcQuantity());
			tKECTPRODUCTCART.setPcCartSaveStatus("Y");
			tKECTPRODUCTCART.setPcCreatedDate(OffsetDateTime.now());
			tKECTPRODUCTCART.setPcCreatedUserId(productCart.getPcTkecmuId());
			session.save(tKECTPRODUCTCART);

			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

		}
	}

	@Override
	public List<ProductCart> listCart(String uId) {

		List<ProductCart> productCart = new ArrayList<>();

		String userId = "FROM TKECTPRODUCTCART WHERE pcTkecmuId.uId =:userId";

		Query userIdQuery = sessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTPRODUCTCART> tKECTPRODUCTCART = userIdQuery.getResultList();

		PropertyMap<TKECTPRODUCTCART, ProductCart> propertyMap = new PropertyMap<TKECTPRODUCTCART, ProductCart>() {
			protected void configure() {

				skip().setPcCreatedUserId(null);
				skip().setPcCreatedDate(null);
			}
		};
		TypeMap<TKECTPRODUCTCART, ProductCart> typeMap = modelMapper.getTypeMap(TKECTPRODUCTCART.class,
				ProductCart.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}
		for (TKECTPRODUCTCART tKECTPRODUCTCART1 : tKECTPRODUCTCART) {

			ProductCart productCart1 = modelMapper.map(tKECTPRODUCTCART1, ProductCart.class);

			productCart.add(productCart1);
		}

		return productCart;
	}

	@Override
	public Integer checkTotalQuantity(String pcTkecmpId) {

		TKECMPRODUCT tKECMPRODUCT = sessionFactory.getCurrentSession().get(TKECMPRODUCT.class, pcTkecmpId);

		System.err.println(tKECMPRODUCT);
		return tKECMPRODUCT.getPQuantity();
	}

	@Override
	public Boolean addQuantity(ProductCart productCart) {

		TKECMPRODUCT tKECMPRODUCT = sessionFactory.getCurrentSession().get(TKECMPRODUCT.class,
				productCart.getPcTkecmpId());

		if (productCart.getPcQuantity() <= tKECMPRODUCT.getPQuantity()) {

			TKECTPRODUCTCART tKECTPRODUCTCART = sessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
					productCart.getPcAgId());

			tKECTPRODUCTCART.setPcQuantity(productCart.getPcQuantity());
			sessionFactory.getCurrentSession().update(tKECTPRODUCTCART);

			return true;

		} else {

			TKECTPRODUCTCART tKECTPRODUCTCART = sessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
					productCart.getPcAgId());

			tKECTPRODUCTCART.setPcQuantity(tKECMPRODUCT.getPQuantity());
			sessionFactory.getCurrentSession().update(tKECTPRODUCTCART);

		}

		return false;

	}

	@Override
	public void saveLater(ProductCart productCart) {

		TKECTPRODUCTCART tKECTPRODUCTCART = sessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
				productCart.getPcAgId());

		tKECTPRODUCTCART.setPcCartSaveStatus(productCart.getPcCartSaveStatus());
		sessionFactory.getCurrentSession().update(tKECTPRODUCTCART);

	}

	@Override
	public void deleteCart(ProductCart productCart) {

		String deleteAgId = "DELETE FROM TKECTPRODUCTCART WHERE pcAgId =:agId AND pcTkecmuId.uId =:userId";

		Query deleteAgIdQuery = sessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("agId", productCart.getPcAgId());
		deleteAgIdQuery.setParameter("userId", productCart.getPcTkecmuId());
		deleteAgIdQuery.executeUpdate();

	}

}
