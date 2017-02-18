package com.lanyuan.dao;

import java.util.List;

import com.lanyuan.base.BaseDao;
import com.lanyuan.entity.ResourceRoles;
import com.lanyuan.entity.Resources;

public interface ResourcesDao extends BaseDao<Resources> {

	public List<Resources> findAll();
	//根据用户的id 获取用户的权限
	public List<Resources> getUserResources(Integer userId);
	//根据角色id获取角色权限
	public List<Resources> getRoleResources(Integer roleId);
	//根据用户名获取用户的权限
	public List<Resources> getResourcesByUserName(String username);
	
	public void deleteRoleResources(Integer roleId);
	
	public void saveRoleResources(ResourceRoles resourceRoles);
}
