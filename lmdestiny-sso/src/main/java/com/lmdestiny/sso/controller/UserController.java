package com.lmdestiny.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmdestiny.model.TbUser;
import com.lmdestiny.sso.service.UserService;
import com.lmdestiny.util.ExceptionUtil;
import com.lmdestiny.util.TaotaoResult;

@Controller
@RequestMapping("/user")
public class UserController{
	@Autowired
	private UserService userService;
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	//验证用户是否存在
	public Object vaildate(@PathVariable String param,@PathVariable Integer type,String callback) {
		//param 与 type 不可能为空,为空则不能映射
		if(type>3 || type <1) {
			return TaotaoResult.build(400, "输入类型无效",false);
		}
		TaotaoResult result = null;
		result = userService.find(param,type);
		if(callback == null) {
			return result;
		}else {
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
	}
	//用户注册
	@RequestMapping("/register")
	@ResponseBody
	public TaotaoResult register(TbUser user) {
		try {
			return userService.save(user);
		}catch(Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	//用户登录
	@RequestMapping("/login")
	@ResponseBody
	public TaotaoResult login(String username,String password) {
		try {
			return userService.login(username,password);
		}catch(Exception e) {
			return TaotaoResult.build(400,"登录失败");
			}
	}
	//通过token查询是否session是否过期
	
	@RequestMapping("/token/{token}")
	@ResponseBody
		public Object getUserByToken(@PathVariable String token, String callback) {
			TaotaoResult result = null;
			try {
				result = userService.getUserByToken(token);
			} catch (Exception e) {
				e.printStackTrace();
				result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
			}
			
			//判断是否为jsonp调用
			if (StringUtils.isBlank(callback)) {
				return result;
			} else {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			
		}
	//安全退出
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public TaotaoResult logout(@PathVariable String token) {
		try {
			return userService.logout(token);
		}catch(Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400, "退出失败"); 
		}
	}
	
	@RequestMapping("/showRegister")
	public String register() {
		return"register";
	}
	
	@RequestMapping("/showLogin")
	public String showLogin(String redirect,Model model) {
		model.addAttribute("redirect",redirect);
		return "login";
	}

}
