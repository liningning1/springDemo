package com.lanyuan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanyuan.dao.UserDao;
import com.lanyuan.entity.Roles;
import com.lanyuan.entity.User;
import com.lanyuan.service.UserService;
import com.lanyuan.util.PageView;

//声明式事务最大的优点就是不需要通过编程的方式管理事务，
//这样就不需要在业务逻辑代码中掺杂事务管理的代码，
//只需在配置文件中做相关的事务规则声明(或通过基于@Transactional注解的方式)，
//便可以将事务规则应用到业务逻辑中。
//事务管理对于企业应用来说是至关重要的，即使出现异常情况，它也可以保证数据的一致性。
//在service类前加上@Transactional，
//声明这个service所有方法需要事务管理。每一个业务方法开始时都会打开一个事务。
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	public UserDao userDao;
	
	@Override
	public PageView query(PageView pageView, User user) {
		
		List<User> list = userDao.query(pageView, user);
		pageView.setRecords(list);
		return pageView;
	}

	@Override
	public void add(User user) {
		
		userDao.add(user);
	}

	@Override
	public void delete(Integer id) {
		
		userDao.delete(id);
		
	}

	@Override
	public void modify(User user) {
		
		userDao.modify(user);
		
	}

	@Override
	public User getById(Integer id) {
		
		return (User)userDao.getById(id);
	}

	@Override
	public int countUser(String userName, String userPassword) {
		
		return userDao.countUser(userName, userPassword);
	}

	@Override
	public User querySingleUser(String username) {
		
		return userDao.querySingleUser(username);
	}

	@Override
	public Roles findbyUserId(Integer userId) {
		
		return userDao.findbyUserRoles(userId);
	}

}
