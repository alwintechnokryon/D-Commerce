package com.technokryon.ecommerce.admin.service;

import java.util.List;

import com.technokryon.ecommerce.admin.pojo.UserApplyGroup;
import com.technokryon.ecommerce.admin.pojo.UserGroup;

public interface AdminGroupService {

	void addGroup(UserGroup userGroup);

	List<UserGroup> groupList();

	UserGroup groupDetailById(UserGroup userGroup);

	void updateGroup(UserGroup userGroup);

	void activateGroup(UserGroup userGroup);

	Boolean checkGroupIdExist(UserApplyGroup userApplyGroup);

	void addUserApplyGroup(UserApplyGroup userApplyGroup);

	List<UserApplyGroup> userApplyGroupList();

	void deleteUserApplyGroup(UserApplyGroup userApplyGroup);

}
