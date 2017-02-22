package com.lanyuan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanyuan.dao.UserDao;
import com.lanyuan.entity.Resources;
import com.lanyuan.entity.User;
import com.lanyuan.entity.UserLoginList;
import com.lanyuan.service.ResourcesService;
import com.lanyuan.service.UserLoginListService;
import com.lanyuan.util.Common;

/**
 * 进行管理后台框架界面的类
 * 
 * @author linn
 *
 */

@Controller
// 是一个用来处理请求地址映射的注解，
// 可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
@RequestMapping("/background/")
public class BackgroundController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserLoginListService userLoginListService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping("login")
	public String login(Model model, HttpServletRequest request) {
		Object o = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if (null != o) {
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}

		return "/background/framework/login";
	}

	@RequestMapping("loginCheck")
	public String loginCheck(String username, String password, HttpServletRequest request) {
		try {
			if (!request.getMethod().equals("post")) {
				request.setAttribute("error", "支持post方式提交!");
			}
			if (Common.isEmpty(username) || Common.isEmpty(password)) {
				request.setAttribute("error", "用户名或密码不能为空!");
				return "/background/framework/login";
			}
			// 验证用户的用户名与密码是否正确
			User user = userDao.querySingleUser(username);
			if (user == null || !user.getUserPassword().equals(password)) {
				request.setAttribute("error", "用户名或密码不正确!");
				return "/background/framework/login";
			}
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			// 当验证都通过后将信息都放入session中
			request.getSession().setAttribute("userSession", user);
			// 记录登录信息
			UserLoginList userLoginList = new UserLoginList();
			userLoginList.setUserId(user.getUserId());
			userLoginList.setLoginIp(Common.toIpAddr(request));
			userLoginListService.add(userLoginList);

		} catch (AuthenticationException ae) {

			request.setAttribute("error", "登录异常,请联系管理员!");
			return "/background/framework/login";

		}
		return "redirect:index.html";
	}

	@RequestMapping("index")
	public String index(Model model) {
		return "/background/framework/main";
	}

	@RequestMapping("top")
	public String top(Model model) {
		return "/background/framework/top";

	}

	@RequestMapping("center")
	public String center(Model model) {
		return "/background/framework/center";

	}

	@RequestMapping("tab")
	public String tab(Model model) {
		return "/background/framework/tab/tab";

	}

	@RequestMapping("left")
	public String left(Model model, HttpServletRequest request) {
		try {
			UserDetails userDetials = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = request.getUserPrincipal().getName();
			List<Resources> resources = resourcesService.getResourcesByUserName(username);
			model.addAttribute("resources", resources);
			return "/background/framework/left";
		} catch (Exception e) {
             
			request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
		}
		return "/background/framework/left";
	}

}
