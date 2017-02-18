package com.lanyuan.service;

import java.util.List;

import com.lanyuan.entity.ServerInfo;
import com.lanyuan.util.PageView;

public interface ServerInfoService {

    public PageView query(PageView pageView,ServerInfo serverInfo);
	
	public List<ServerInfo> queryAll(ServerInfo serverInfo);
	
	public void add(ServerInfo serverInfo);
	
	public void delete(Integer id);
	
	public ServerInfo getById(Integer id);
	
	public void modify(ServerInfo serverInfo);
	
}
