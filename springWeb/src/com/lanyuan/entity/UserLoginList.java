package com.lanyuan.entity;

import java.util.Date;

/**
 * 登录信息实体类
 * @author linn
 *
 */
public class UserLoginList {

	private Integer loginId;
	private Integer userId;
	private String userName;
	private Date LoginTime;
	private String loginIp;
	
	public UserLoginList()
	{
		
	}

	public UserLoginList(Integer loginId, Integer userId, String userName, Date loginTime, String loginIp) {
		super();
		this.loginId = loginId;
		this.userId = userId;
		this.userName = userName;
		LoginTime = loginTime;
		this.loginIp = loginIp;
	}

	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginTime() {
		return LoginTime;
	}

	public void setLoginTime(Date loginTime) {
		LoginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	
}
