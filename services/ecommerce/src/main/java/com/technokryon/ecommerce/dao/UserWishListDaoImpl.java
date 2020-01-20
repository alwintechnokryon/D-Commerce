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
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public void addWishList(WishList RO_WishList) {

		TKECTWISHLIST O_TKECTWISHLIST = new TKECTWISHLIST();

		O_TKECTWISHLIST.setWlUserId(RO_WishList.getWlUserId());
		O_TKECTWISHLIST.setWlTkecmpId(
				O_SessionFactory.getCurrentSession().get(TKECMPRODUCT.class, RO_WishList.getWlTkecmpId()));
		O_TKECTWISHLIST.setWlCreatedDate(OffsetDateTime.now());
		O_SessionFactory.getCurrentSession().save(O_TKECTWISHLIST);
	}

	@Override
	public List<WishList> listWishList(String uId) {

		String userId = "FROM TKECTWISHLIST WHERE wlUserId =:userId";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(userId);
		userIdQuery.setParameter("userId", uId);

		List<TKECTWISHLIST> LO_TKECTWISHLIST = userIdQuery.getResultList();

		List<WishList> LO_WishList = new ArrayList<>();

		PropertyMap<TKECTWISHLIST, WishList> O_PropertyMap = new PropertyMap<TKECTWISHLIST, WishList>() {
			protected void configure() {

				skip().setWlCreatedDate(null);

			}
		};
		TypeMap<TKECTWISHLIST, WishList> O_TypeMap = O_ModelMapper.getTypeMap(TKECTWISHLIST.class, WishList.class);

		if (O_TypeMap == null) {
			O_ModelMapper.addMappings(O_PropertyMap);
		}

		for (TKECTWISHLIST O_TKECTWISHLIST : LO_TKECTWISHLIST) {

			WishList O_WishList = O_ModelMapper.map(O_TKECTWISHLIST, WishList.class);

			LO_WishList.add(O_WishList);
		}

		return LO_WishList;
	}

	@Override
	public void deleteWishlist(WishList RO_WishList) {

		String deleteAgId = "DELETE FROM TKECTWISHLIST WHERE wlUserId =:userId AND wlAgId =:agId";

		Query deleteAgIdQuery = O_SessionFactory.getCurrentSession().createQuery(deleteAgId);

		deleteAgIdQuery.setParameter("userId", RO_WishList.getWlUserId());
		deleteAgIdQuery.setParameter("agId", RO_WishList.getWlAgId());
		deleteAgIdQuery.executeUpdate();

	}

}
