package com.technokryon.ecommerce.admin.dao;

import java.util.List;

import com.technokryon.ecommerce.admin.pojo.UserApplyGroup;
import com.technokryon.ecommerce.admin.pojo.UserGroup;

public interface AdminGroupDao {

	void addGroup(UserGroup userGroup);

	List<UserGroup> groupList();

	UserGroup groupDetailById(UserGroup userGroup);

	void updateGroup(UserGroup userGroup);

	void activateGroup(UserGroup userGroup);

	void addUserApplyGroup(UserApplyGroup userApplyGroup);

	List<UserApplyGroup> userApplyGroupList();

	void deleteUserApplyGroup(UserApplyGroup userApplyGroup);

	Boolean checkGroupIdExist(UserApplyGroup userApplyGroup);

}
