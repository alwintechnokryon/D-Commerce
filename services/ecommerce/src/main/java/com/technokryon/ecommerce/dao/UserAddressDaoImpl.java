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
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void addUserAddress(UserAddress userAddress) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			TKECTUSERADDRESS tKECTUSERADDRESS = new TKECTUSERADDRESS();

			tKECTUSERADDRESS.setUadTkecmuId(session.get(TKECMUSER.class, userAddress.getUadTkecmuId()));
			tKECTUSERADDRESS.setUadName(userAddress.getUadName());
			tKECTUSERADDRESS.setUadPhone(userAddress.getUadPhone());
			tKECTUSERADDRESS.setUadAlternativePhone(userAddress.getUadAlternativePhone());
			tKECTUSERADDRESS.setUadAddress(userAddress.getUadAddress());
			tKECTUSERADDRESS.setUadCity(userAddress.getUadCity());
			tKECTUSERADDRESS.setUadTkectsAgId(session.get(TKECTSTATE.class, userAddress.getUadTkectsAgId()));
			tKECTUSERADDRESS.setUadPostalCode(userAddress.getUadPostalCode());
			tKECTUSERADDRESS.setUadAddressType(userAddress.getUadAddressType());
			tKECTUSERADDRESS.setUadLandmark(userAddress.getUadLandmark());
			tKECTUSERADDRESS.setUadCreatedDate(OffsetDateTime.now());
			tKECTUSERADDRESS.setUadCreatedUserId(userAddress.getUadTkecmuId());
			tKECTUSERADDRESS.setUadLatitude(userAddress.getUadLatitude());
			tKECTUSERADDRESS.setUadLongitude(userAddress.getUadLongitude());
			tKECTUSERADDRESS.setUadTkecnAgId(session.get(TKECMCOUNTRY.class, userAddress.getUadTkecnAgId()));
			tKECTUSERADDRESS.setUadTkectsAgId(session.get(TKECTSTATE.class, userAddress.getUadTkectsAgId()));
			session.save(tKECTUSERADDRESS);

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
	public List<UserAddress> listUserAddress(String uId) {
		
		List<UserAddress> userAddress = new ArrayList<>();

		String userId = "FROM TKECTUSERADDRESS WHERE uadTkecmuId.uId =:userId";

		Query userIdQuery = sessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTUSERADDRESS> tKECTUSERADDRESS = userIdQuery.getResultList();

		PropertyMap<TKECTUSERADDRESS, UserAddress> propertyMap = new PropertyMap<TKECTUSERADDRESS, UserAddress>() {
			protected void configure() {

				skip().setUadCreatedUserId(null);
				skip().setUadCreatedDate(null);
				skip().setUadModifiedDate(null);
				skip().setUadModifiedUserId(null);
			}
		};
		TypeMap<TKECTUSERADDRESS, UserAddress> typeMap = modelMapper.getTypeMap(TKECTUSERADDRESS.class,
				UserAddress.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		for (TKECTUSERADDRESS tKECTUSERADDRESS1 : tKECTUSERADDRESS) {

			UserAddress userAddress1 = modelMapper.map(tKECTUSERADDRESS1, UserAddress.class);

			userAddress.add(userAddress1);
		}

		return userAddress;
	}

	@Override
	public void updateUserAddress(UserAddress userAddress) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {

			TKECTUSERADDRESS tKECTUSERADDRESS = session.get(TKECTUSERADDRESS.class, userAddress.getUadAgId());

			tKECTUSERADDRESS.setUadTkecmuId(session.get(TKECMUSER.class, userAddress.getUadTkecmuId()));
			tKECTUSERADDRESS.setUadName(userAddress.getUadName());
			tKECTUSERADDRESS.setUadPhone(userAddress.getUadPhone());
			tKECTUSERADDRESS.setUadAlternativePhone(userAddress.getUadAlternativePhone());
			tKECTUSERADDRESS.setUadAddress(userAddress.getUadAddress());
			tKECTUSERADDRESS.setUadCity(userAddress.getUadCity());
			tKECTUSERADDRESS.setUadTkectsAgId(session.get(TKECTSTATE.class, userAddress.getUadTkectsAgId()));
			tKECTUSERADDRESS.setUadPostalCode(userAddress.getUadPostalCode());
			tKECTUSERADDRESS.setUadAddressType(userAddress.getUadAddressType());
			tKECTUSERADDRESS.setUadLandmark(userAddress.getUadLandmark());
			tKECTUSERADDRESS.setUadLatitude(userAddress.getUadLatitude());
			tKECTUSERADDRESS.setUadLongitude(userAddress.getUadLongitude());
			tKECTUSERADDRESS.setUadModifiedDate(OffsetDateTime.now());
			tKECTUSERADDRESS.setUadModifiedUserId(userAddress.getUadTkecmuId());
			session.update(tKECTUSERADDRESS);

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
	public void deleteUserAddress(UserAddress userAddress) {

		String deleteAgId = " DELETE FROM TKECTUSERADDRESS WHERE uadAgId =:agId AND uadTkecmuId.uId =:userId";

		Query deleteAgIdQuery = sessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("agId", userAddress.getUadAgId());
		deleteAgIdQuery.setParameter("userId", userAddress.getUadTkecmuId());
		deleteAgIdQuery.executeUpdate();

	}
}
