package com.lanyuan.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.lanyuan.dao.ResourcesDao;
import com.lanyuan.entity.Resources;

/**
 * 加载资源与权限的对应关系       ---访问验证模块的初始化，主要是FilterInvocationSecurityMetadataSource对象加载配置信息的过程。
 * @author linn
 *
 */
@Service
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

	@Autowired
	private ResourcesDao resourcesDao;
	private static Map<String,Collection<ConfigAttribute>> resourceMap = null;
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {	
		return null;
	}

	//返回所请求的资源所需要的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if(resourceMap == null)
		{
			loadResourceDefine();	
		}
		if(requestUrl.indexOf("?")>-1)
		{
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		}
		Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl);
		return configAttributes;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		
		return true;
	}
	
	//@PostConstruct 是java ee 5引入的注解
	//spring 允许开发者在受管bean 中使用它,当DI容器初始化该受管bean时
	//@POSTConstruct 方法会被自动调用，从而完成一些初始化工作
	@PostConstruct
	private void loadResourceDefine()
	{
		if(resourceMap == null)
		{
			resourceMap = new HashMap<String,Collection<ConfigAttribute>>();
			List<Resources> resources = this.resourcesDao.findAll();
			for(Resources resource:resources)
			{
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				//通过资源名称来表示具体的权限,主要必须:"ROLE_"开头
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_" +resource.getResKey());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getResUrl(),configAttributes);
			}
		}
	}

}
