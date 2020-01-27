package com.technokryon.ecommerce.dao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECTWISHLIST;
import com.technokryon.ecommerce.pojo.WishList;

@Repository("UserWishListDao")
@Transactional
@Component
public class UserWishListDaoImpl implements UserWishListDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void addWishList(WishList wishList) {

		TKECTWISHLIST tKECTWISHLIST = new TKECTWISHLIST();

		tKECTWISHLIST.setWlUserId(wishList.getWlUserId());
		tKECTWISHLIST.setWlTkecmpId(
				sessionFactory.getCurrentSession().get(TKECMPRODUCT.class, wishList.getWlTkecmpId()));
		tKECTWISHLIST.setWlCreatedDate(OffsetDateTime.now());
		sessionFactory.getCurrentSession().save(tKECTWISHLIST);
	}

	@Override
	public List<WishList> listWishList(String uId) {

		String userId = "FROM TKECTWISHLIST WHERE wlUserId =:userId";

		Query userIdQuery = sessionFactory.getCurrentSession().createQuery(userId);
		userIdQuery.setParameter("userId", uId);

		List<TKECTWISHLIST> tKECTWISHLIST = userIdQuery.getResultList();

		List<WishList> wishList = new ArrayList<>();

		PropertyMap<TKECTWISHLIST, WishList> propertyMap = new PropertyMap<TKECTWISHLIST, WishList>() {
			protected void configure() {

				skip().setWlCreatedDate(null);

			}
		};
		TypeMap<TKECTWISHLIST, WishList> typeMap = modelMapper.getTypeMap(TKECTWISHLIST.class, WishList.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		for (TKECTWISHLIST tKECTWISHLIST1 : tKECTWISHLIST) {

			WishList wishList1 = modelMapper.map(tKECTWISHLIST1, WishList.class);

			wishList.add(wishList1);
		}

		return wishList;
	}

	@Override
	public void deleteWishlist(WishList wishList) {

		String deleteAgId = "DELETE FROM TKECTWISHLIST WHERE wlUserId =:userId AND wlAgId =:agId";

		Query deleteAgIdQuery = sessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("userId", wishList.getWlUserId());
		deleteAgIdQuery.setParameter("agId", wishList.getWlAgId());
		deleteAgIdQuery.executeUpdate();

	}

}
