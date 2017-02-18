package com.lanyuan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lanyuan.base.impl.BaseDaoImpl;
import com.lanyuan.dao.ResourcesDao;
import com.lanyuan.entity.ResourceRoles;
import com.lanyuan.entity.Resources;

@Repository("resourcesDao")
@SuppressWarnings("unchecked")
public class ResourcesDaoImpl extends BaseDaoImpl<Resources> implements ResourcesDao{

	@Override
	public List<Resources> findAll() {
		
		return this.getSqlSession().selectList("resources.findAll");
	}

	@Override
	public List<Resources> getUserResources(Integer userId) {
		
		return this.getSqlSession().selectList("resources.getUserResources", userId);
	}

	@Override
	public List<Resources> getRoleResources(Integer roleId) {
		
		return getSqlSession().selectList("resources.getRoleResources",roleId);
	}

	@Override
	public List<Resources> getResourcesByUserName(String username) {
		
		return this.getSqlSession().selectList("resources.getResourcesByUserName", username);
	}

	@Override
	public void deleteRoleResources(Integer roleId) {
		
		this.getSqlSession().delete("resources.deleteRoleRescours", roleId);
		
	}

	@Override
	public void saveRoleResources(ResourceRoles resourceRoles) {
		
		this.getSqlSession().insert("resources.saveRoleRescours", resourceRoles);
		
	}

	
}
