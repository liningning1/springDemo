package com.lanyuan.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/*
 *AccessDecisionManager 判断当前用户是否拥有访问该资源的权限             ---用于授权一个特定的操作。
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object arg1, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null)
		{
			return;
		}
		//所请求的资源拥有的权限(一个资源有可能有多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while(iterator.hasNext())
		{
			ConfigAttribute configAttribute = iterator.next();
			//访问请求资源所需要的权限
			String needPermit = configAttribute.getAttribute();
			//用户所拥有的权限authentication
			for(GrantedAuthority ga:authentication.getAuthorities())
			{
				if(needPermit.equals(ga.getAuthority()))
				{
					return;	
				}				
			}
		}
		throw new AccessDeniedException("没有访问权限!");
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		
		return true;
	}

}
