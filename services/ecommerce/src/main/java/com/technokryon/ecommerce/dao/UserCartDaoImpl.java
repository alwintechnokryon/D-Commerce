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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTPRODUCTCART;
import com.technokryon.ecommerce.pojo.PRODUCTCART;

@Repository("UserCartDao")
@Transactional
@Component
public class UserCartDaoImpl implements UserCartDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public void addToCart(PRODUCTCART RO_PRODUCTCART) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {
			TKECTPRODUCTCART O_TKECTPRODUCTCART = new TKECTPRODUCTCART();

			O_TKECTPRODUCTCART.setPcTkecmuId(O_Session.get(TKECMUSER.class, RO_PRODUCTCART.getPcTkecmuId()));
			O_TKECTPRODUCTCART.setPcTkecmpId(O_Session.get(TKECMPRODUCT.class, RO_PRODUCTCART.getPcTkecmpId()));
			O_TKECTPRODUCTCART.setPcQuantity(RO_PRODUCTCART.getPcQuantity());
			O_TKECTPRODUCTCART.setPcCartSaveStatus("Y");
			O_TKECTPRODUCTCART.setPcCreatedDate(OffsetDateTime.now());
			O_TKECTPRODUCTCART.setPcCreatedUserId(RO_PRODUCTCART.getPcTkecmuId());
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
	public List<PRODUCTCART> listCart(String uId) {

		String userId = "FROM TKECTPRODUCTCART WHERE pcTkecmuId.uId =:userId";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTPRODUCTCART> LO_TKECTPRODUCTCART = userIdQuery.getResultList();

		List<PRODUCTCART> LO_PRODUCTCART = new ArrayList<>();

		PropertyMap<TKECTPRODUCTCART, PRODUCTCART> O_PropertyMap = new PropertyMap<TKECTPRODUCTCART, PRODUCTCART>() {
			protected void configure() {

				skip().setPcCreatedUserId(null);
				skip().setPcCreatedDate(null);
			}
		};
		O_ModelMapper.addMappings(O_PropertyMap);

		for (TKECTPRODUCTCART O_TKECTPRODUCTCART : LO_TKECTPRODUCTCART) {

			PRODUCTCART O_PRODUCTCART = O_ModelMapper.map(O_TKECTPRODUCTCART, PRODUCTCART.class);

			LO_PRODUCTCART.add(O_PRODUCTCART);
		}

		return LO_PRODUCTCART;
	}

	@Override
	public Integer checkTotalQuantity(String pcTkecmpId) {

		TKECMPRODUCT O_TKECMPRODUCT = O_SessionFactory.getCurrentSession().get(TKECMPRODUCT.class, pcTkecmpId);

		return O_TKECMPRODUCT.getPQuantity();
	}

	@Override
	public Boolean addQuantity(PRODUCTCART RO_PRODUCTCART) {

		TKECMPRODUCT O_TKECMPRODUCT = O_SessionFactory.getCurrentSession().get(TKECMPRODUCT.class,
				RO_PRODUCTCART.getPcTkecmpId());

		if (RO_PRODUCTCART.getPcQuantity() <= O_TKECMPRODUCT.getPQuantity()) {

			TKECTPRODUCTCART O_TKECTPRODUCTCART = O_SessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
					RO_PRODUCTCART.getPcAgId());

			O_TKECTPRODUCTCART.setPcQuantity(RO_PRODUCTCART.getPcQuantity());
			O_SessionFactory.getCurrentSession().update(O_TKECTPRODUCTCART);

			return true;

		} else {

			TKECTPRODUCTCART O_TKECTPRODUCTCART = O_SessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
					RO_PRODUCTCART.getPcAgId());

			O_TKECTPRODUCTCART.setPcQuantity(O_TKECMPRODUCT.getPQuantity());
			O_SessionFactory.getCurrentSession().update(O_TKECTPRODUCTCART);

		}

		return false;

	}

	@Override
	public void saveLater(PRODUCTCART RO_PRODUCTCART) {

		TKECTPRODUCTCART O_TKECTPRODUCTCART = O_SessionFactory.getCurrentSession().get(TKECTPRODUCTCART.class,
				RO_PRODUCTCART.getPcAgId());

		O_TKECTPRODUCTCART.setPcCartSaveStatus(RO_PRODUCTCART.getPcCartSaveStatus());
		O_SessionFactory.getCurrentSession().update(O_TKECTPRODUCTCART);

	}

	@Override
	public void deleteCart(PRODUCTCART RO_PRODUCTCART) {

		String deleteAgId = "DELETE FROM TKECTPRODUCTCART WHERE pcAgId =:agId AND pcTkecmuId.uId =:userId";

		Query deleteAgIdQuery = O_SessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("agId", RO_PRODUCTCART.getPcAgId());
		deleteAgIdQuery.setParameter("userId", RO_PRODUCTCART.getPcTkecmuId());
		deleteAgIdQuery.executeUpdate();

	}

}
