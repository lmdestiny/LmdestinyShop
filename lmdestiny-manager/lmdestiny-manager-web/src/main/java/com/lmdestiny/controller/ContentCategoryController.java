package com.lmdestiny.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmdestiny.service.ContentCategoryService;
import com.lmdestiny.util.EasyUITreeNode;
import com.lmdestiny.util.TaotaoResult;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> list(@RequestParam(value="id", defaultValue="0")Long  parentId){
		return contentCategoryService.list(parentId);
	}
	/**
	 * 添加结点
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createNode(long parentId, String name){
		return contentCategoryService.createNode(parentId,name);
	}
	/**
	 * 删除结点
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteNode(Long parentId ,Long id){
		return contentCategoryService.deleteNode(parentId,id); 
	}
	/**
	 * 重命名结点
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateNodename(Long id,String name){
		return contentCategoryService.updateNodename(id,name);
	}
	
	

}
