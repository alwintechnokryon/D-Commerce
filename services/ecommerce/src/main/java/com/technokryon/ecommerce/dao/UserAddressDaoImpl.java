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

import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTSTATE;
import com.technokryon.ecommerce.model.TKECTUSERADDRESS;
import com.technokryon.ecommerce.pojo.USERADDRESS;

@Repository("UserAddressDao")
@Transactional
@Component
public class UserAddressDaoImpl implements UserAddressDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public void addUserAddress(USERADDRESS RO_USERADDRESS) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {
			TKECTUSERADDRESS O_TKECTUSERADDRESS = new TKECTUSERADDRESS();

			O_TKECTUSERADDRESS.setUadTkecmuId(O_Session.get(TKECMUSER.class, RO_USERADDRESS.getUadTkecmuId()));
			O_TKECTUSERADDRESS.setUadName(RO_USERADDRESS.getUadName());
			O_TKECTUSERADDRESS.setUadPhone(RO_USERADDRESS.getUadPhone());
			O_TKECTUSERADDRESS.setUadAlternativePhone(RO_USERADDRESS.getUadAlternativePhone());
			O_TKECTUSERADDRESS.setUadAddress(RO_USERADDRESS.getUadAddress());
			O_TKECTUSERADDRESS.setUadCity(RO_USERADDRESS.getUadCity());
			O_TKECTUSERADDRESS.setUadTkectsAgId(O_Session.get(TKECTSTATE.class, RO_USERADDRESS.getUadTkectsAgId()));
			O_TKECTUSERADDRESS.setUadPostalCode(RO_USERADDRESS.getUadPostalCode());
			O_TKECTUSERADDRESS.setUadAddressType(RO_USERADDRESS.getUadAddressType());
			O_TKECTUSERADDRESS.setUadLandmark(RO_USERADDRESS.getUadLandmark());
			O_TKECTUSERADDRESS.setUadCreatedDate(OffsetDateTime.now());
			O_TKECTUSERADDRESS.setUadCreatedUserId(RO_USERADDRESS.getUadTkecmuId());
			O_TKECTUSERADDRESS.setUadLatitude(RO_USERADDRESS.getUadLatitude());
			O_TKECTUSERADDRESS.setUadLongitude(RO_USERADDRESS.getUadLongitude());
			O_Session.save(O_TKECTUSERADDRESS);

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
	public List<USERADDRESS> listUserAddress(String uId) {

		String userId = "FROM TKECTUSERADDRESS WHERE uadTkecmuId.uId =:userId";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTUSERADDRESS> LO_TKECTUSERADDRESS = userIdQuery.getResultList();

		List<USERADDRESS> LO_USERADDRESS = new ArrayList<>();

		PropertyMap<TKECTUSERADDRESS, USERADDRESS> skipFieldAddress = new PropertyMap<TKECTUSERADDRESS, USERADDRESS>() {
			protected void configure() {

				skip().setUadCreatedUserId(null);
				skip().setUadCreatedDate(null);
				skip().setUadModifiedDate(null);
				skip().setUadModifiedUserId(null);
			}
		};
		O_ModelMapper.addMappings(skipFieldAddress);

		for (TKECTUSERADDRESS O_TKECTUSERADDRESS : LO_TKECTUSERADDRESS) {

			USERADDRESS O_USERADDRESS = O_ModelMapper.map(O_TKECTUSERADDRESS, USERADDRESS.class);

			LO_USERADDRESS.add(O_USERADDRESS);
		}

		return LO_USERADDRESS;
	}

	@Override
	public void updateUserAddress(USERADDRESS RO_USERADDRESS) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {

			TKECTUSERADDRESS O_TKECTUSERADDRESS = O_Session.get(TKECTUSERADDRESS.class, RO_USERADDRESS.getUadAgId());

			O_TKECTUSERADDRESS.setUadTkecmuId(O_Session.get(TKECMUSER.class, RO_USERADDRESS.getUadTkecmuId()));
			O_TKECTUSERADDRESS.setUadName(RO_USERADDRESS.getUadName());
			O_TKECTUSERADDRESS.setUadPhone(RO_USERADDRESS.getUadPhone());
			O_TKECTUSERADDRESS.setUadAlternativePhone(RO_USERADDRESS.getUadAlternativePhone());
			O_TKECTUSERADDRESS.setUadAddress(RO_USERADDRESS.getUadAddress());
			O_TKECTUSERADDRESS.setUadCity(RO_USERADDRESS.getUadCity());
			O_TKECTUSERADDRESS.setUadTkectsAgId(O_Session.get(TKECTSTATE.class, RO_USERADDRESS.getUadTkectsAgId()));
			O_TKECTUSERADDRESS.setUadPostalCode(RO_USERADDRESS.getUadPostalCode());
			O_TKECTUSERADDRESS.setUadAddressType(RO_USERADDRESS.getUadAddressType());
			O_TKECTUSERADDRESS.setUadLandmark(RO_USERADDRESS.getUadLandmark());
			O_TKECTUSERADDRESS.setUadLatitude(RO_USERADDRESS.getUadLatitude());
			O_TKECTUSERADDRESS.setUadLongitude(RO_USERADDRESS.getUadLongitude());
			O_TKECTUSERADDRESS.setUadModifiedDate(OffsetDateTime.now());
			O_TKECTUSERADDRESS.setUadModifiedUserId(RO_USERADDRESS.getUadTkecmuId());
			O_Session.update(O_TKECTUSERADDRESS);

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
	public void deleteUserAddress(USERADDRESS RO_USERADDRESS) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		String deleteAgId = " DELETE FROM TKECTUSERADDRESS WHERE uadAgId =:agId AND uadTkecmuId.uId =:userId";

		Query deleteAgIdQuery = O_Session.createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("agId", RO_USERADDRESS.getUadAgId());
		deleteAgIdQuery.setParameter("userId", RO_USERADDRESS.getUadTkecmuId());
		deleteAgIdQuery.executeUpdate();

		O_Transaction.commit();
		O_Session.close();

	}
}
