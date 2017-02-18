package com.lanyuan.service;

import java.util.List;

import com.lanyuan.entity.Resources;
import com.lanyuan.util.PageView;

public interface ResourcesService {
	
	public PageView query(PageView pageView,Resources resources);

	public void add(Resources resources);
	
	public void delete(Integer id);
	
	public void modify(Resources resources);
	
	public Resources getById(Integer id);
	
	public List<Resources> queryAll();
	
	//根据用户的id获取用户的权限
	public List<Resources> getUserResources(Integer id);
	
	//根据角色id获取用户的权限
	public List<Resources> getRoleResources(Integer id);
	
	public List<Resources> getResourcesByUserName(String userName);
	
	public void saveRoleResources(Integer roleId,List<String> list);
}
