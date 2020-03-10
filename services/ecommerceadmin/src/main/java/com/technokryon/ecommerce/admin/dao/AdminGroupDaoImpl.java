package com.technokryon.ecommerce.admin.dao;

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

import com.technokryon.ecommerce.admin.model.TKECMUSER;
import com.technokryon.ecommerce.admin.model.TKECMUSERGROUP;
import com.technokryon.ecommerce.admin.model.TKECTUSERAPPLYGROUP;
import com.technokryon.ecommerce.admin.pojo.UserApplyGroup;
import com.technokryon.ecommerce.admin.pojo.UserGroup;

@Repository("AdminGroupDao")
@Transactional
@Component

public class AdminGroupDaoImpl implements AdminGroupDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void addGroup(UserGroup userGroup) {

		String getGroupId = "FROM TKECMUSERGROUP ORDER BY ugId DESC";

		Query groupIdQuery = sessionFactory.getCurrentSession().createQuery(getGroupId);
		groupIdQuery.setMaxResults(1);
		TKECMUSERGROUP tKECMUSERGROUP1 = (TKECMUSERGROUP) groupIdQuery.uniqueResult();

		TKECMUSERGROUP tKECMUSERGROUP = new TKECMUSERGROUP();

		if (tKECMUSERGROUP1 == null) {

			tKECMUSERGROUP.setUgId("TKECG0001");
		} else {

			String userId = tKECMUSERGROUP1.getUgId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			// System.err.println(Ag);
			tKECMUSERGROUP.setUgId("TKECG" + String.format("%04d", Ag));
		}

		tKECMUSERGROUP.setUgName(userGroup.getUgName());
		tKECMUSERGROUP.setUgDescription(userGroup.getUgDescription());
		tKECMUSERGROUP.setUgStatusYN("Y");
		tKECMUSERGROUP.setUgCreatedDate(OffsetDateTime.now());
		tKECMUSERGROUP.setUgCreatedUserId(userGroup.getUgCreatedUserId());

		sessionFactory.getCurrentSession().save(tKECMUSERGROUP);

	}

	@Override
	public List<UserGroup> groupList() {

		List<UserGroup> userGroup = new ArrayList<UserGroup>();

		String getUserGroup = "FROM TKECMUSERGROUP";

		Query getUserGroupQuery = sessionFactory.getCurrentSession().createQuery(getUserGroup);

		List<TKECMUSERGROUP> tKECMUSERGROUP = getUserGroupQuery.getResultList();

		PropertyMap<TKECMUSERGROUP, UserGroup> propertyMap = new PropertyMap<TKECMUSERGROUP, UserGroup>() {
			protected void configure() {

				skip().setUgModifiedDate(null);
				skip().setUgCreatedDate(null);
				skip().setUgCreatedUserId(null);
				skip().setUgModifiedUserId(null);
			}
		};
		TypeMap<TKECMUSERGROUP, UserGroup> typeMap = modelMapper.getTypeMap(TKECMUSERGROUP.class, UserGroup.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		for (TKECMUSERGROUP tKECMUSERGROUP1 : tKECMUSERGROUP) {

			UserGroup userGroup1 = modelMapper.map(tKECMUSERGROUP1, UserGroup.class);
			userGroup.add(userGroup1);

		}

		return userGroup;
	}

	@Override
	public UserGroup groupDetailById(UserGroup userGroup) {

		TKECMUSERGROUP tKECMUSERGROUP = sessionFactory.getCurrentSession().get(TKECMUSERGROUP.class,
				userGroup.getUgId());

		PropertyMap<TKECMUSERGROUP, UserGroup> propertyMap = new PropertyMap<TKECMUSERGROUP, UserGroup>() {
			protected void configure() {

				skip().setUgModifiedDate(null);
				skip().setUgCreatedDate(null);
				skip().setUgCreatedUserId(null);
				skip().setUgModifiedUserId(null);
			}
		};
		TypeMap<TKECMUSERGROUP, UserGroup> typeMap = modelMapper.getTypeMap(TKECMUSERGROUP.class, UserGroup.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		UserGroup userGroup1 = modelMapper.map(tKECMUSERGROUP, UserGroup.class);

		return userGroup1;
	}

	@Override
	public void updateGroup(UserGroup userGroup) {

		TKECMUSERGROUP tKECMUSERGROUP = sessionFactory.getCurrentSession().get(TKECMUSERGROUP.class,
				userGroup.getUgId());

		tKECMUSERGROUP.setUgName(userGroup.getUgName());
		tKECMUSERGROUP.setUgDescription(userGroup.getUgDescription());
		tKECMUSERGROUP.setUgModifiedDate(OffsetDateTime.now());
		tKECMUSERGROUP.setUgModifiedUserId(userGroup.getUgModifiedUserId());
		sessionFactory.getCurrentSession().update(tKECMUSERGROUP);
	}

	@Override
	public void activateGroup(UserGroup userGroup) {

		TKECMUSERGROUP tKECMUSERGROUP = sessionFactory.getCurrentSession().get(TKECMUSERGROUP.class,
				userGroup.getUgId());

		tKECMUSERGROUP.setUgStatusYN(userGroup.getUgStatusYN());
		tKECMUSERGROUP.setUgModifiedDate(OffsetDateTime.now());
		tKECMUSERGROUP.setUgModifiedUserId(userGroup.getUgModifiedUserId());
		sessionFactory.getCurrentSession().update(tKECMUSERGROUP);

	}

	@Override
	public Boolean checkGroupIdExist(UserApplyGroup userApplyGroup) {

		String getUserGroupId = "FROM TKECTUSERAPPLYGROUP WHERE uagTkecmuId.uId =:userId AND uagTkecmugId.ugId =:groupId";

		Query getgetUserGroupIdQuery = sessionFactory.getCurrentSession().createQuery(getUserGroupId);
		getgetUserGroupIdQuery.setParameter("userId", userApplyGroup.getUagTkecmuId());
		getgetUserGroupIdQuery.setParameter("groupId", userApplyGroup.getUagTkecmugId());

		TKECTUSERAPPLYGROUP tKECTUSERAPPLYGROUP = (TKECTUSERAPPLYGROUP) getgetUserGroupIdQuery.uniqueResult();

		if (tKECTUSERAPPLYGROUP == null) {

			return false;
		}

		return true;
	}

	@Override
	public void addUserApplyGroup(UserApplyGroup userApplyGroup) {

		TKECTUSERAPPLYGROUP tKECTUSERAPPLYGROUP = new TKECTUSERAPPLYGROUP();

		tKECTUSERAPPLYGROUP.setUagTkecmugId(
				sessionFactory.getCurrentSession().get(TKECMUSERGROUP.class, userApplyGroup.getUagTkecmugId()));

		tKECTUSERAPPLYGROUP.setUagTkecmuId(
				sessionFactory.getCurrentSession().get(TKECMUSER.class, userApplyGroup.getUagTkecmuId()));

		tKECTUSERAPPLYGROUP.setUagCreatedDate(OffsetDateTime.now());

		tKECTUSERAPPLYGROUP.setUagCreatedUserId(userApplyGroup.getUagCreatedUserId());

		sessionFactory.getCurrentSession().save(tKECTUSERAPPLYGROUP);

	}

	@Override
	public List<UserApplyGroup> userApplyGroupList() {

		List<UserApplyGroup> userApplyGroup = new ArrayList<UserApplyGroup>();

		String getUserApplyGroup = "FROM TKECTUSERAPPLYGROUP";

		Query getUserApplyGroupQuery = sessionFactory.getCurrentSession().createQuery(getUserApplyGroup);

		List<TKECTUSERAPPLYGROUP> tKECTUSERAPPLYGROUP = getUserApplyGroupQuery.getResultList();

		PropertyMap<TKECTUSERAPPLYGROUP, UserApplyGroup> propertyMap = new PropertyMap<TKECTUSERAPPLYGROUP, UserApplyGroup>() {
			protected void configure() {

				skip().setUagCreatedDate(null);
				skip().setUagCreatedUserId(null);
			}
		};
		TypeMap<TKECTUSERAPPLYGROUP, UserApplyGroup> typeMap = modelMapper.getTypeMap(TKECTUSERAPPLYGROUP.class,
				UserApplyGroup.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		for (TKECTUSERAPPLYGROUP tKECTUSERAPPLYGROUP1 : tKECTUSERAPPLYGROUP) {

			UserApplyGroup userApplyGroup1 = modelMapper.map(tKECTUSERAPPLYGROUP1, UserApplyGroup.class);
			userApplyGroup.add(userApplyGroup1);

		}

		return userApplyGroup;
	}

	@Override
	public void deleteUserApplyGroup(UserApplyGroup userApplyGroup) {

		TKECTUSERAPPLYGROUP tKECTUSERAPPLYGROUP = sessionFactory.getCurrentSession().get(TKECTUSERAPPLYGROUP.class,
				userApplyGroup.getUagAgId());

		tKECTUSERAPPLYGROUP.setUagTkecmugId(null);
		tKECTUSERAPPLYGROUP.setUagTkecmuId(null);

		sessionFactory.getCurrentSession().delete(tKECTUSERAPPLYGROUP);

	}

}
