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

import com.technokryon.ecommerce.model.TKECMCOUNTRY;
import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTSTATE;
import com.technokryon.ecommerce.model.TKECTUSERADDRESS;
import com.technokryon.ecommerce.pojo.UserAddress;

@Repository("UserAddressDao")
@Transactional
@Component
public class UserAddressDaoImpl implements UserAddressDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public void addUserAddress(UserAddress RO_UserAddress) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {
			TKECTUSERADDRESS O_TKECTUSERADDRESS = new TKECTUSERADDRESS();

			O_TKECTUSERADDRESS.setUadTkecmuId(O_Session.get(TKECMUSER.class, RO_UserAddress.getUadTkecmuId()));
			O_TKECTUSERADDRESS.setUadName(RO_UserAddress.getUadName());
			O_TKECTUSERADDRESS.setUadPhone(RO_UserAddress.getUadPhone());
			O_TKECTUSERADDRESS.setUadAlternativePhone(RO_UserAddress.getUadAlternativePhone());
			O_TKECTUSERADDRESS.setUadAddress(RO_UserAddress.getUadAddress());
			O_TKECTUSERADDRESS.setUadCity(RO_UserAddress.getUadCity());
			O_TKECTUSERADDRESS.setUadTkectsAgId(O_Session.get(TKECTSTATE.class, RO_UserAddress.getUadTkectsAgId()));
			O_TKECTUSERADDRESS.setUadPostalCode(RO_UserAddress.getUadPostalCode());
			O_TKECTUSERADDRESS.setUadAddressType(RO_UserAddress.getUadAddressType());
			O_TKECTUSERADDRESS.setUadLandmark(RO_UserAddress.getUadLandmark());
			O_TKECTUSERADDRESS.setUadCreatedDate(OffsetDateTime.now());
			O_TKECTUSERADDRESS.setUadCreatedUserId(RO_UserAddress.getUadTkecmuId());
			O_TKECTUSERADDRESS.setUadLatitude(RO_UserAddress.getUadLatitude());
			O_TKECTUSERADDRESS.setUadLongitude(RO_UserAddress.getUadLongitude());
			O_TKECTUSERADDRESS.setUadTkecnAgId(O_Session.get(TKECMCOUNTRY.class, RO_UserAddress.getUadTkecnAgId()));
			O_TKECTUSERADDRESS.setUadTkectsAgId(O_Session.get(TKECTSTATE.class, RO_UserAddress.getUadTkectsAgId()));
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
	public List<UserAddress> listUserAddress(String uId) {

		String userId = "FROM TKECTUSERADDRESS WHERE uadTkecmuId.uId =:userId";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTUSERADDRESS> LO_TKECTUSERADDRESS = userIdQuery.getResultList();

		List<UserAddress> LO_UserAddress = new ArrayList<>();

		PropertyMap<TKECTUSERADDRESS, UserAddress> O_PropertyMap = new PropertyMap<TKECTUSERADDRESS, UserAddress>() {
			protected void configure() {

				skip().setUadCreatedUserId(null);
				skip().setUadCreatedDate(null);
				skip().setUadModifiedDate(null);
				skip().setUadModifiedUserId(null);
			}
		};
		TypeMap<TKECTUSERADDRESS, UserAddress> O_TypeMap = O_ModelMapper.getTypeMap(TKECTUSERADDRESS.class,
				UserAddress.class);

		if (O_TypeMap == null) {
			O_ModelMapper.addMappings(O_PropertyMap);
		}

		for (TKECTUSERADDRESS O_TKECTUSERADDRESS : LO_TKECTUSERADDRESS) {

			UserAddress O_UserAddress = O_ModelMapper.map(O_TKECTUSERADDRESS, UserAddress.class);

			LO_UserAddress.add(O_UserAddress);
		}

		return LO_UserAddress;
	}

	@Override
	public void updateUserAddress(UserAddress RO_UserAddress) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {

			TKECTUSERADDRESS O_TKECTUSERADDRESS = O_Session.get(TKECTUSERADDRESS.class, RO_UserAddress.getUadAgId());

			O_TKECTUSERADDRESS.setUadTkecmuId(O_Session.get(TKECMUSER.class, RO_UserAddress.getUadTkecmuId()));
			O_TKECTUSERADDRESS.setUadName(RO_UserAddress.getUadName());
			O_TKECTUSERADDRESS.setUadPhone(RO_UserAddress.getUadPhone());
			O_TKECTUSERADDRESS.setUadAlternativePhone(RO_UserAddress.getUadAlternativePhone());
			O_TKECTUSERADDRESS.setUadAddress(RO_UserAddress.getUadAddress());
			O_TKECTUSERADDRESS.setUadCity(RO_UserAddress.getUadCity());
			O_TKECTUSERADDRESS.setUadTkectsAgId(O_Session.get(TKECTSTATE.class, RO_UserAddress.getUadTkectsAgId()));
			O_TKECTUSERADDRESS.setUadPostalCode(RO_UserAddress.getUadPostalCode());
			O_TKECTUSERADDRESS.setUadAddressType(RO_UserAddress.getUadAddressType());
			O_TKECTUSERADDRESS.setUadLandmark(RO_UserAddress.getUadLandmark());
			O_TKECTUSERADDRESS.setUadLatitude(RO_UserAddress.getUadLatitude());
			O_TKECTUSERADDRESS.setUadLongitude(RO_UserAddress.getUadLongitude());
			O_TKECTUSERADDRESS.setUadModifiedDate(OffsetDateTime.now());
			O_TKECTUSERADDRESS.setUadModifiedUserId(RO_UserAddress.getUadTkecmuId());
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
	public void deleteUserAddress(UserAddress RO_UserAddress) {

		String deleteAgId = " DELETE FROM TKECTUSERADDRESS WHERE uadAgId =:agId AND uadTkecmuId.uId =:userId";

		Query deleteAgIdQuery = O_SessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("agId", RO_UserAddress.getUadAgId());
		deleteAgIdQuery.setParameter("userId", RO_UserAddress.getUadTkecmuId());
		deleteAgIdQuery.executeUpdate();

	}
}
