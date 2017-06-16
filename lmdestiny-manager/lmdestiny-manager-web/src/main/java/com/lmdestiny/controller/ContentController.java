package com.lmdestiny.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmdestiny.model.TbContent;
import com.lmdestiny.service.ContentService;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.TaotaoResult;
@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows) throws Exception {
		EUDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	@RequestMapping(value = "/save")
	@ResponseBody
	public TaotaoResult save(TbContent content){
		content.setPic("http://localhost:8080"+content.getPic());
		content.setPic2("http://localhost:8080"+content.getPic2());
		return contentService.save(content);
	}
	@RequestMapping(value="/delete")
	@ResponseBody
	public TaotaoResult delete(String ids){
		return contentService.delete(ids);
	}
	@RequestMapping(value="/edit")
	@ResponseBody
	public TaotaoResult update(TbContent content){
		content.setPic("http://localhost:8080"+content.getPic());
		content.setPic2("http://localhost:8080"+content.getPic2());
		content.setUpdated(new Date());
		return contentService.update(content);
	}
	
}
