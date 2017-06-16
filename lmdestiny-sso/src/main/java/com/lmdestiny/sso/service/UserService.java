package com.lmdestiny.sso.service;



import com.lmdestiny.model.TbUser;
import com.lmdestiny.util.TaotaoResult;

public interface UserService{

	TaotaoResult find(String param,Integer type);

	TaotaoResult save(TbUser user);

	TaotaoResult login(String username, String password);

	TaotaoResult getUserByToken(String token);

	TaotaoResult logout(String token);

}
