package com.lmdestiny.sso.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lmdestiny.dao.TbUserMapper;
import com.lmdestiny.model.TbUser;
import com.lmdestiny.sso.dao.JedisClient;
import com.lmdestiny.sso.service.UserService;
import com.lmdestiny.util.CookieUtils;
import com.lmdestiny.util.JsonUtils;
import com.lmdestiny.util.TaotaoResult;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;
	
	
	//返回true,用户不存在,可以注册等.
	public TaotaoResult find(String param, Integer type){
		//可选参数1、2、3分别代表username、phone、email
		TbUser user = null;
		if(type ==1) {
			user = userMapper.findUserName(param);
		}else if(type ==2) {
			user = userMapper.findphone(param);
		}else {
			user = userMapper.findEmail(param);
		}
		if(user !=null) {
			return TaotaoResult.build(200,"用户存在",false);
		}else {
			return TaotaoResult.build(200,"用户不存在", true);
		}
	}

	@Override
	public TaotaoResult save(TbUser user){
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.save(user);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult login(String username, String password){
		TbUser user= userMapper.getUserByUserName(username);
		if(user == null||!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return TaotaoResult.build(400,"用户名或密码错误");
		}else {
			//生成token
			String token = UUID.randomUUID().toString();
			//保存用户之前，把用户对象中的密码清空。
			user.setPassword(null);
			//把用户信息写入redis
			jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
			//设置session的过期时间
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
			//返回token
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			//String str = (String) requestAttributes.getAttribute("name",RequestAttributes.SCOPE_SESSION);
			HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
			HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
			CookieUtils.setCookie(request, response, "TT_TOKEN", token);
			System.out.println(CookieUtils.getCookieValue(request, "TT_TOKEN"));
			return TaotaoResult.ok(token);
		}
	}
	@Override
	public TaotaoResult getUserByToken(String token) {
			
			//根据token从redis中查询用户信息
			String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
			//判断是否为空
			if (StringUtils.isBlank(json)) {
				return TaotaoResult.build(400, "此session已经过期，请重新登录");
			}
			//更新过期时间
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
			//返回用户信息
			return TaotaoResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
		}

	//安全退出
	@Override
	public TaotaoResult logout(String token){
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, 0);
		return TaotaoResult.ok();
	}
}
