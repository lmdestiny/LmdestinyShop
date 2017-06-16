package com.lmdestiny.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lmdestiny.model.TbUser;
import com.lmdestiny.portal.service.UserService;
import com.lmdestiny.util.HttpClientUtil;
import com.lmdestiny.util.TaotaoResult;
@Service
public class UserServiceImpl implements UserService{

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	public String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_LOGIN}")
	public String SSO_PAGE_LOGIN;
	@Override
	public TbUser getUserByToken(String token){
		String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
		try {
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			if (result.getStatus() == 200) {
				TbUser user = (TbUser) result.getData();
				return user;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return null;
	}
}
