package com.lanyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanyuan.entity.User;
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
	
	
}
