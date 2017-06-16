package com.lmdestiny.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lmdestiny.model.TbItem;
import com.lmdestiny.model.TbItemCat;
import com.lmdestiny.service.ItemCatService;
import com.lmdestiny.service.PictureService;
import com.lmdestiny.service.TbItemService;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.PictureResult;
import com.lmdestiny.util.TaotaoResult;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private TbItemService itemService;
	@Autowired
	private ItemCatService itemCatService;
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getAllItem(Integer page,Integer rows){
		return itemService.findItemByPageNo(page,rows);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<Map> categoryList(@RequestParam(value="id", defaultValue="0") Long parentId) throws Exception {
		List<Map> catList = new ArrayList<Map>();
		//查询数据库
		List<TbItemCat> list = itemCatService.getItemCatList(parentId);
		for (TbItemCat tbItemCat : list) {
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("id", tbItemCat.getId());
			node.put("text", tbItemCat.getName());
			//如果是父节点的话就设置成关闭状态，如果是叶子节点就是open状态
			node.put("state", tbItemCat.getIsParent()?"closed":"open");
			catList.add(node);
		}
		return catList;
	}
	//商品图片上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PictureResult uploda(MultipartFile uploadFile) throws Exception {
		//调用service上传图片
		PictureResult pictureResult = pictureService.uploadFile(uploadFile);
		String url = pictureResult.getUrl();
		/**
		 * ********************************************************************
		 */
		//前期无法搭建访问图片
		String u="http://localhost:8080/item/pic/download"+url;
		pictureResult.setUrl(u);
		//返回上传结果
		return pictureResult;
	}
	//图片下载
	@RequestMapping("pic/download/{path1}/{path2}/{path3}/{path4}/{path5}/{path:.+}")
	public ResponseEntity<byte[]> download(@PathVariable String path1,
			@PathVariable String path2,@PathVariable String path3,@PathVariable String path4,@PathVariable String path5,@PathVariable String path) throws IOException{
		String str = "/"+path1+"/"+path2+"/"+path3+"/"+path4+"/"+path5+"/"+path;
		System.out.println(str);
		byte[] b = pictureService.downloadFile(str);
		ResponseEntity<byte[]> entity = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		entity = new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
		return entity;
	}
	//新增商品
	@RequestMapping(value="/save",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult save(TbItem tbItem,String desc,String itemParams){
		TaotaoResult tr = itemService.save(tbItem,desc,itemParams);
		return tr;
	}
	
	
}
