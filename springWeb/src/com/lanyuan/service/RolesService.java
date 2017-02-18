package com.lanyuan.service;

import java.util.List;

import com.lanyuan.entity.Roles;
import com.lanyuan.entity.UserRoles;
import com.lanyuan.util.PageView;

public interface RolesService {

	public PageView query(PageView pageView,Roles roles);
	
	public void add(Roles roles);
	
	public void delete(Integer id);
	
	public void modify(Roles roles);
	
	public Roles getById(Integer id);
	
	public List<Roles> findAll();
	
	public void saveUserRoles(UserRoles userRoles);
}
