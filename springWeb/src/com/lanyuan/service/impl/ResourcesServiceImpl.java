package com.lanyuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanyuan.dao.ResourcesDao;
import com.lanyuan.entity.ResourceRoles;
import com.lanyuan.entity.Resources;
import com.lanyuan.service.ResourcesService;
import com.lanyuan.util.Common;
import com.lanyuan.util.PageView;

@Transactional
@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService{

	@Autowired
	public ResourcesDao resourcesDao;
	
	@Override
	public PageView query(PageView pageView, Resources resources) {
		List<Resources> list = resourcesDao.query(pageView, resources);
		pageView.setRecords(list);
		return pageView;
	}

	@Override
	public void add(Resources resources) {
		
		resourcesDao.add(resources);
		
	}

	@Override
	public void delete(Integer id) {
		
		resourcesDao.delete(id);
		
	}

	@Override
	public void modify(Resources resources) {
		
		resourcesDao.modify(resources);
		
	}

	@Override
	public Resources getById(Integer id) {
		
		return resourcesDao.getById(id);
	}

	@Override
	public List<Resources> findAll() {
		
		return resourcesDao.findAll();
	}

	@Override
	public List<Resources> getUserResources(Integer id) {
		
		return resourcesDao.getUserResources(id);
	}

	@Override
	public List<Resources> getRoleResources(Integer id) {
		
		return resourcesDao.getRoleResources(id);
	}

	@Override
	public List<Resources> getResourcesByUserName(String userName) {
		
		return resourcesDao.getResourcesByUserName(userName);
	}

	@Override
	public void saveRoleResources(Integer roleId, List<Integer> list) {
		
		resourcesDao.deleteRoleResources(roleId);
		for(Integer rId:list)
		{
			String str = rId.toString();
			if(!Common.isEmpty(str))
			{
				ResourceRoles resourceRoles = new ResourceRoles();
				resourceRoles.setRescId(rId);
				resourceRoles.setRoleId(roleId);
				resourcesDao.saveRoleResources(resourceRoles);
			}
		}
	}

}