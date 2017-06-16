package com.lmdestiny.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmdestiny.rest.service.ItemService;
import com.lmdestiny.util.TaotaoResult;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDesc(itemId);
		return result;
	}

	@RequestMapping(value="/param/{itemId}")
	@ResponseBody
	public TaotaoResult showItemParam(@PathVariable Long itemId) throws Exception {
		//取规格参数
		TaotaoResult result= itemService.getItemParam(itemId);
		//返回规格参数信息
		return result;
	}

	
}

