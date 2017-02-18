package com.lanyuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanyuan.dao.RolesDao;
import com.lanyuan.entity.Roles;
import com.lanyuan.entity.UserRoles;
import com.lanyuan.service.RolesService;
import com.lanyuan.util.PageView;

@Transactional
@Service("rolesService")
public class RolesServiceImpl implements RolesService{

	@Autowired
	public RolesDao rolesDao;
	
	@Override
	public PageView query(PageView pageView, Roles roles) {
		
		List<Roles> list = rolesDao.query(pageView, roles);
		pageView.setRecords(list);
		return pageView;
	}

	@Override
	public void add(Roles roles) {
		
		rolesDao.add(roles);
		
	}

	@Override
	public void delete(Integer id) {
		
		rolesDao.delete(id);
		
	}

	@Override
	public void modify(Roles roles) {
		
		rolesDao.modify(roles);
		
	}

	@Override
	public Roles getById(Integer id) {
		
		return rolesDao.getById(id);
	}

	@Override
	public List<Roles> findAll() {
		
		return rolesDao.findAll();
	}

	@Override
	public void saveUserRoles(UserRoles userRoles) {
		
		rolesDao.saveUserRole(userRoles);
		
	}

}
