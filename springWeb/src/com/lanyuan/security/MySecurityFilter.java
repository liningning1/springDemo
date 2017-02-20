package com.lanyuan.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

/**
 *核心的InterceptorStatusToken token = super.beforeInvocation(fi);
 *会调用我们定义的 accessDecisionManager:decide(Object object)和securityMetadataSource:getAttributes(Object object)方法
 *自己实现的过滤用户请求类,也可以直接使用FilterSecurityInterceptor
 *AbstractSecurityInterceptor有三个派生类
 *FilterSecurityInterceptor:负责处理FilterInvocation,实现对URL资源的拦截
 *MethodSecurityInterceptor:负责处理MethodInvocation,实现对方法调用的拦截 
 *AspectJSecurityInterceptor:主要用于对切面方法的拦截(AOP)
 * @author linn
 *
 */
@Service
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter{

	@Autowired
	private MySecurityMetadataSource securityMetadataSource;
	@Autowired
	private MyAccessDecisionManager accessDesionManager;
	@Autowired
	private AuthenticationManager MyAuthenticationManager;
	
	@PostConstruct
	public void init()
	{
		super.setAuthenticationManager(MyAuthenticationManager);
        super.setAccessDecisionManager(accessDesionManager);	
	}
	
	
	@Override
	public void destroy() {
		
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(arg0,arg1,arg2);
		invoke(fi);
		
	}
	
	private void invoke(FilterInvocation fi) throws IOException, ServletException
	{
		InterceptorStatusToken token  = super.beforeInvocation(fi);
		try
		{
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			
		}finally
		{
			super.afterInvocation(token, null);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
			
	}

	@Override
	public Class<?> getSecureObjectClass() {
		//下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		
		return this.securityMetadataSource;
	}

}
