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
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public void addToCart(ProductCart RO_ProductCart) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {
			TKECTPRODUCTCART O_TKECTPRODUCTCART = new TKECTPRODUCTCART();

			O_TKECTPRODUCTCART.setPcTkecmuId(O_Session.get(TKECMUSER.class, RO_ProductCart.getPcTkecmuId()));
			O_TKECTPRODUCTCART.setPcTkecmpId(O_Session.get(TKECMPRODUCT.class, RO_ProductCart.getPcTkecmpId()));
			O_TKECTPRODUCTCART.setPcQuantity(RO_ProductCart.getPcQuantity());
			O_TKECTPRODUCTCART.setPcCartSaveStatus("Y");
			O_TKECTPRODUCTCART.setPcCreatedDate(OffsetDateTime.now());
			O_TKECTPRODUCTCART.setPcCreatedUserId(RO_ProductCart.getPcTkecmuId());
			O_Session.save(O_TKECTPRODUCTCART);

			O_Transaction.commit();
			O_Session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (O_Transaction.isActive()) {
				O_Transaction.rollback();
			}
			O_Session.close();

		}
	}

	@Override
	public List<ProductCart> listCart(String uId) {
		
		List<ProductCart> LO_PRODUCTCART = new ArrayList<>();

		String userId = "FROM TKECTPRODUCTCART WHERE pcTkecmuId.uId =:userId";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTPRODUCTCART> LO_TKECTPRODUCTCART = userIdQuery.getResultList();

		PropertyMap<TKECTPRODUCTCART, ProductCart> O_PropertyMap = new PropertyMap<TKECTPRODUCTCART, ProductCart>() {
			protected void configure() {

				skip().setPcCreatedUserId(null);
				skip().setPcCreatedDate(null);
			}
		};
		TypeMap<TKECTPRODUCTCART, ProductCart> O_TypeMap = O_ModelMapper.getTypeMap(TKECTPRODUCTCART.class,
				ProductCart.class);

		if (O_TypeMap == null) {
			O_ModelMapper.addMappings(O_PropertyMap);
		}
		for (TKECTPRODUCTCART O_TKECTPRODUCTCART : LO_TKECTPRODUCTCART) {

			ProductCart O_ProductCart = O_ModelMapper.map(O_TKECTPRODUCTCART, ProductCart.class);

			LO_PRODUCTCART.add(O_ProductCart);
		}

		return LO_PRODUCTCART;
	}

	@Override
	public Integer checkTotalQuantity(String pcTkecmpId) {

		TKECMPRODUCT O_TKECMPRODUCT = O_SessionFactory.getCurrentSession().get(TKECMPRODUCT.class, pcTkecmpId);

		return O_TKECMPRODUCT.getPQuantity();
	}

	@Override
	public Boolean addQuantity(ProductCart RO_ProductCart) {

		TKECMPRODUCT O_TKECMPRODUCT = O_SessionFactory.getCurrentSession().get(TKECMPRODUCT.class,
				RO_ProductCart.getPcTkecmpId());

		if (RO_ProductCart.getPcQuantity() <= O_TKECMPRODUCT.getPQuantity()) {

			TKECTPRODUCTCART O_TKECTPRODUCTCART = O_SessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
					RO_ProductCart.getPcAgId());

			O_TKECTPRODUCTCART.setPcQuantity(RO_ProductCart.getPcQuantity());
			O_SessionFactory.getCurrentSession().update(O_TKECTPRODUCTCART);

			return true;

		} else {

			TKECTPRODUCTCART O_TKECTPRODUCTCART = O_SessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
					RO_ProductCart.getPcAgId());

			O_TKECTPRODUCTCART.setPcQuantity(O_TKECMPRODUCT.getPQuantity());
			O_SessionFactory.getCurrentSession().update(O_TKECTPRODUCTCART);

		}

		return false;

	}

	@Override
	public void saveLater(ProductCart RO_ProductCart) {

		TKECTPRODUCTCART O_TKECTPRODUCTCART = O_SessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
				RO_ProductCart.getPcAgId());

		O_TKECTPRODUCTCART.setPcCartSaveStatus(RO_ProductCart.getPcCartSaveStatus());
		O_SessionFactory.getCurrentSession().update(O_TKECTPRODUCTCART);

	}

	@Override
	public void deleteCart(ProductCart RO_ProductCart) {

		String deleteAgId = "DELETE FROM TKECTPRODUCTCART WHERE pcAgId =:agId AND pcTkecmuId.uId =:userId";

		Query deleteAgIdQuery = O_SessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("agId", RO_ProductCart.getPcAgId());
		deleteAgIdQuery.setParameter("userId", RO_ProductCart.getPcTkecmuId());
		deleteAgIdQuery.executeUpdate();

	}

}
