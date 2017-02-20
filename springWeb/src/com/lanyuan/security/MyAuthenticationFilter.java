package com.lanyuan.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lanyuan.dao.UserDao;
import com.lanyuan.entity.User;
import com.lanyuan.entity.UserLoginList;
import com.lanyuan.service.UserLoginListService;
import com.lanyuan.util.Common;

//这个类主要是用于用户的登录验证
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	// 登录成功后跳转的地址
	private String successUrl = "/background/index.html";
	// 登录失败后跳转的地址
	private String errorUrl = "/background/login.html";
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserLoginListService userLoginListService;

	public void init() {
		this.setUsernameParameter(USERNAME);
		this.setPasswordParameter(PASSWORD);

		// 验证成功，跳转的页面
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl(successUrl);
		this.setAuthenticationSuccessHandler(successHandler);

		// 验证失败，跳转的页面
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setDefaultFailureUrl(errorUrl);
		this.setAuthenticationFailureHandler(failureHandler);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response)throws AuthenticationException
	{
		if(!request.getMethod().equals("POST"))
		{
			throw new AuthenticationServiceException("Authentication not support"+
		                                request.getMethod()); 
		}
		String username = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();
		
		if(Common.isEmpty(username) || Common.isEmpty(password))
		{
			BadCredentialsException exception = new BadCredentialsException("用户名或密码不能为空!");
			
			throw exception;
		}
		//验证用户的用户名和密码是否正确
		User user = this.userDao.querySingleUser(username);
		if(user ==null ||user.getUserPassword().equals(password))
		{
			BadCredentialsException exception = new BadCredentialsException(
					"用户名和密码不匹配！");// 在界面输出自定义的信息！！
			throw exception;
		}
		//当验证都通过了,将用户信息放到session中去
		request.getSession().setAttribute("userSession", user);
		//记录登录信息
		UserLoginList userLoginList = new UserLoginList();
		userLoginList.setLoginId(user.getUserId());
		System.out.print("userId---"+user.getUserId()+"--Ip--"
		                  + Common.toIpAddr(request));
		userLoginList.setLoginIp(Common.toIpAddr(request));
		userLoginListService.add(userLoginList);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
		//允许子类设置详细属性
		setDetails(request,token);
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(token);
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
	
}
