package com.lanyuan.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanyuan.entity.ServerStatus;
import com.lanyuan.service.ServerInfoService;
import com.lanyuan.util.Common;
import com.lanyuan.util.PropertiesUtils;

@Controller
@RequestMapping("/background/serverInfo")
public class ServerInfoController {

	@Autowired
	private ServerInfoService serverInfoService;
	
	public String forcast()
	{
		return "/background/server/forcast";
	}
	
	//预警监控系统
	public Map<String,Object> warnInfo(HttpServletRequest request) throws Exception
	{
		ServerStatus status = Common.getServerStatus();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String cpuUsage = status.getCpuUsage();
		long FreeMem = status.getFreeMem();
		long useMem = status.getUsedMem();
		long TotalMem = status.getTotalMem();
		String serverUsage = Common.fromUsage(useMem, TotalMem);
		dataMap.put("cpuUsage", cpuUsage);
		dataMap.put("FreeMem", FreeMem);
		dataMap.put("TotalMem", TotalMem);
		dataMap.put("serverUsage", serverUsage);
		long JvmFreeMem = status.getJvmFreeMem();
		long JvmTotalMem = status.getJvmTotalMem();
		String JvmUsage = Common.fromUsage(JvmTotalMem - JvmFreeMem, JvmTotalMem);
		dataMap.put("JvmFreeMem", JvmFreeMem);
		dataMap.put("JvmTotalMem", JvmTotalMem);
		dataMap.put("JvmUsage", JvmUsage);
		dataMap.put("cpu", PropertiesUtils.findPropertiesKey("cpu"));
		dataMap.put("jvm", PropertiesUtils.findPropertiesKey("jvm"));
		dataMap.put("ram", PropertiesUtils.findPropertiesKey("ram"));
		dataMap.put("toEmail", PropertiesUtils.findPropertiesKey("toEmail"));
		dataMap.put("diskInfos", status.getDiskInfos());
		return dataMap;
	}
	
}
