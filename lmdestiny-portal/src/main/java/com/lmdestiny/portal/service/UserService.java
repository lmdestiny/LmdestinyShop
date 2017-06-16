package com.lmdestiny.portal.service;

import com.lmdestiny.model.TbUser;

public interface UserService{
	TbUser getUserByToken(String token);
}
