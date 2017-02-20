package com.lanyuan.util;

import javax.servlet.http.HttpServletRequest;

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
}
