package com.lmdestiny.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lmdestiny.rest.service.RedisService;
import com.lmdestiny.util.TaotaoResult;

@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	public TaotaoResult contentCacheSync(@PathVariable Long contentCid) {
		TaotaoResult result = redisService.syncContent(contentCid);
		return result;
	}
}
