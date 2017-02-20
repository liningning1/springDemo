package com.lanyuan.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lanyuan.dao.ResourcesDao;
import com.lanyuan.dao.UserDao;
import com.lanyuan.entity.Resources;

/*
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 获得对象的方式：
 * WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 或在JSP中：
 * <sec:authentication property="principal.username"/>
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可
 * 权限验证类
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private ResourcesDao resourcesDao;
	
	//登录验证
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.lanyuan.entity.User users = userDao.querySingleUser(username);
		if(users == null)
		{
			throw new UsernameNotFoundException(username+" not exist !");
		}
		Collection<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(users);
		User userDetails = new User(
				users.getUserName(), 
				users.getUserPassword(),
				true, 
				true, 
				true,
				true, 
				grantedAuths	//用户的权限
				);
		return userDetails;
	}
	

	//取得用户的权限
	private Set<GrantedAuthority> obtainGrantedAuthorities(com.lanyuan.entity.User user)
	{
		List<Resources> resources = resourcesDao.getUserResources(user.getUserId());
	    Set<GrantedAuthority> autoSet = new HashSet<GrantedAuthority>();
		for(Resources rs:resources)
		{
			//用户所具有的权限(用户可以访问的资源名称) 必须以ROLE_开头
			autoSet.add(new SimpleGrantedAuthority("ROLE_" + rs.getResKey()));
		}
		return autoSet;
	}
	
	
	
	
	
}
