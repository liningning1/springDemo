package com.lanyuan.service;

import com.lanyuan.entity.Roles;
import com.lanyuan.entity.User;
import com.lanyuan.util.PageView;

public interface UserService {

	public PageView query(PageView pageView,User user);
	
	public void add(User user);
	
	public void delete(Integer id);
	
	public void modify(User user);
	
	public User getById(Integer id);
	
	public int countUser(String userName,String userPassword);
	
	public User querySingleUser(String username);
	
	public Roles findbyUserId(Integer userId);
}
