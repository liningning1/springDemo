package com.lanyuan.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanyuan.base.impl.BaseDaoImpl;
import com.lanyuan.dao.UserDao;
import com.lanyuan.entity.Roles;
import com.lanyuan.entity.User;
import com.lanyuan.util.PageView;

@Resposity("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public int countUser(String userName, String userPassword) {
		
		Map<Object,Object> map =new HashMap<Object,Object>();
		map.put("userName", userName);
		map.put("userPassword", userPassword);
		
		return (Integer)this.getSqlSession().selectOne("user.countUser", map);
	}

	@Override
	public User querySingleUser(String userName) {
		
		return (User)this.getSqlSession().selectOne("user.queryUserName", userName);
	}

	@Override
	public Roles findbyUserRoles(Integer userId) {
		
		return this.getSqlSession().selectOne("roles.findbyUserRole", userId);
	}

	
}
