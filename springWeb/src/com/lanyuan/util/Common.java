package com.lanyuan.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Common {

	public static boolean isEmpty(String s)
	{
		if(null==s|| "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	//返回用户的ip地址
	public static String toIpAddr(HttpServletRequest request)
	{
		String ip =request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
		
	}

	//获取经过认证的用户名
    public static String getAuthenticationUsername()
    {
    	String username = null;
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
        {
        	username = ((UserDetails)principal).getUsername();
        }
        else
        {
        	username = principal.toString();
        }
    
        return username;
    }
    
}
