package com.lmdestiny.dao;

import com.lmdestiny.model.TbUser;

public interface TbUserMapper {

	TbUser findUserName(String param);
	TbUser findphone(String param);
	TbUser findEmail(String param);
	void save(TbUser user);
	TbUser getUserByUserName(String username);
}