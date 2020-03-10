package com.technokryon.ecommerce.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.dao.AdminGroupDao;
import com.technokryon.ecommerce.admin.pojo.UserApplyGroup;
import com.technokryon.ecommerce.admin.pojo.UserGroup;

@Service("AdminGroupService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)

public class AdminGroupServiceImpl implements AdminGroupService {

	@Autowired
	private AdminGroupDao adminGroupDao;

	@Override
	public void addGroup(UserGroup userGroup) {

		adminGroupDao.addGroup(userGroup);
	}

	@Override
	public List<UserGroup> groupList() {

		return adminGroupDao.groupList();
	}

	@Override
	public UserGroup groupDetailById(UserGroup userGroup) {

		return adminGroupDao.groupDetailById(userGroup);
	}

	@Override
	public void updateGroup(UserGroup userGroup) {

		adminGroupDao.updateGroup(userGroup);
	}

	@Override
	public void activateGroup(UserGroup userGroup) {

		adminGroupDao.activateGroup(userGroup);
	}

	@Override
	public Boolean checkGroupIdExist(UserApplyGroup userApplyGroup) {

		return adminGroupDao.checkGroupIdExist(userApplyGroup);
	}

	@Override
	public void addUserApplyGroup(UserApplyGroup userApplyGroup) {

		adminGroupDao.addUserApplyGroup(userApplyGroup);
	}

	@Override
	public List<UserApplyGroup> userApplyGroupList() {

		return adminGroupDao.userApplyGroupList();
	}

	@Override
	public void deleteUserApplyGroup(UserApplyGroup userApplyGroup) {

		adminGroupDao.deleteUserApplyGroup(userApplyGroup);
	}

}
