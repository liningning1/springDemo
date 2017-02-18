package com.lanyuan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lanyuan.base.impl.BaseDaoImpl;
import com.lanyuan.dao.RolesDao;
import com.lanyuan.entity.Roles;
import com.lanyuan.entity.UserRoles;

@Repository("rolesDao")
public class RolesDaoImpl extends BaseDaoImpl<Roles> implements RolesDao{

	@Override
	public List<Roles> findAll() {
		
		return this.getSqlSession().selectList("roles.findAll");
	}

	@Override
	public void deleteUserRole(Integer userId) {
		
		this.getSqlSession().delete("roles.deleteUserRole", userId);
		
	}

	@Override
	public void saveUserRole(UserRoles userRoles) {
		
		this.getSqlSession().insert("roles.saveUserRole", userRoles);
		
	}

	
}
