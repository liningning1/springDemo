package com.lanyuan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.entity.Roles;
import com.lanyuan.entity.User;
import com.lanyuan.entity.UserRoles;
import com.lanyuan.service.RolesService;
import com.lanyuan.service.UserService;
import com.lanyuan.util.Common;
import com.lanyuan.util.PageView;

@Controller
@RequestMapping("/background/user/")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RolesService rolesService;
	//Model :存放返回界面的model
	@RequestMapping("query")
	public String query(Model model,User user,String pageNow)
	{
		PageView pageView = null;
		if(Common.isEmpty(pageNow))
		{
			pageView = new PageView(1);
		}
		else
		{
			pageView = new PageView(Integer.parseInt(pageNow));
		}
		pageView = userService.query(pageView, user);
		model.addAttribute("pageView",pageView);
		return "/background/user/list";
	}
	
	@RequestMapping("getById")
	public String getById(Model model,Integer userId,int type)
	{
		User user = userService.getById(userId);
		model.addAttribute("user",user);
		List<Roles> roles  = rolesService.findAll();
		model.addAttribute("roles",roles);
		if(type == 1)
		{
			return "/background/user/edit";
			
		}
		else
		{
			return "/background/user/show";
		}
	} 
	
	@RequestMapping("update")
	public String update(Model model,User user,UserRoles userRoles)
	{
		userService.modify(user);
		if(userRoles.getRoleId()!=null)
		{
			rolesService.saveUserRoles(userRoles);
		}
		return "redirect:query.html";
	}
	
	@RequestMapping("deleteAll")
	public String deleteAll(Model model,String[] check)
	{
		for(String str:check)
		{
			Integer id = Integer.parseInt(str);
			userService.delete(id);
		}
		
		return "redirect:query.html";
	}
	
	@RequestMapping("userRole")
	public String userRole(Model model,String id)
	{
		Integer id1 = Integer.parseInt(id);
		User user = userService.getById(id1);
		model.addAttribute("user", user);
		List<Roles> roles = rolesService.findAll();
		model.addAttribute("roles", roles);
		return "/background/user/userRole";
	}
	//保存用户分配角色
	/*
	 * @responsebody表示该方法的返回结果直接写入HTTP response body中
                     一般在异步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径，
                     加上@responsebody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。
                     比如异步获取json数据，加上@responsebody后，会直接返回json数据。
	 */
	@ResponseBody
	@RequestMapping("allocation")
	public String allocation(Model model,UserRoles userRoles)
	{
		String errorCode ="1000";
		try
		{
			rolesService.saveUserRoles(userRoles);
		}catch(Exception e)
		{
			e.printStackTrace();
			errorCode = "1001";
		}
		return errorCode;
	}
	
	//跳转到登录页面  只提供跳转的功能
	@RequestMapping("addUI")
	public String addUI()
	{
		return "/background/user/add";
	}
	
	@RequestMapping("add")
	public String add(Model model,User user)
	{
		userService.add(user);
		return "redirect:query.html";
		
	}
	
	@RequestMapping("deleteById")
	public String deleteById(Model model,String userId)
	{
		Integer ids = Integer.parseInt(userId);
		userService.delete(ids);
		return "redirect:query.html";
		
	}
	
}
