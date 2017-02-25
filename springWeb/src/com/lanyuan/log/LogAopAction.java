package com.lanyuan.log;

import java.net.InetAddress;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lanyuan.dao.LogDao;
import com.lanyuan.entity.Log;
import com.lanyuan.util.Common;

@Aspect
@Component
public class LogAopAction {

	@Autowired
	private LogDao logDao;
	
	@Around("execution(* com.lanyuan.service.impl.*.*(..))")
	public Object logAll(ProceedingJoinPoint point)
	{
		Object result = null;
		
		//执行的方法名
		String methodName = point.getSignature().getName();
		String className = point.getTarget().getClass().getSimpleName();
		String user = null;
		Long start = 0L;
		Long end = 0L;
		String ip = null;
		//当前用户
		try
		{
			//执行方法所消耗的时间
	     	start = System.currentTimeMillis();
	     	result = point.proceed();
	     	end = System.currentTimeMillis();
	     	//ip
	     	ip = InetAddress.getLocalHost().getHostAddress();
	     	user = Common.getAuthenticationUsername();
	     	
		}catch(Throwable e)
		{
			
		}
		String name=null;
		if(className.indexOf("Resources")>-1)
		{
			name="资源管理";
		}else if(className.indexOf("Roles")>-1)
		{
			name="角色管理";
		}else if(className.indexOf("User")>-1)
		{
			name="用户管理";
		}
		// 操作类型
		// 操作类型
				String opertype = "";
				if (methodName.indexOf("saveUserRole") > -1) {
					opertype = "update用户的角色";
				} else if (methodName.indexOf("saveRoleRescours") > -1) {
					opertype = "update角色的权限";
				} else if (methodName.indexOf("add") > -1 || methodName.indexOf("save") > -1) {
					opertype = "save操作";
				} else if (methodName.indexOf("update") > -1 || methodName.indexOf("modify") > -1) {
					opertype = "update操作";
				} else if (methodName.indexOf("delete") > -1) {
					opertype = "delete操作";
				}
				
				if(!Common.isEmpty(opertype)&&className.indexOf("UserLoginList")==-1){
					Long time = end - start;
					Log log = new Log();
					log.setUsername(user);
					log.setModule(name);
					log.setAction(opertype);
					log.setActionTime(time.toString());
					log.setUserIp(ip);
					logDao.add(log);
				}
				return result;
	}
}
