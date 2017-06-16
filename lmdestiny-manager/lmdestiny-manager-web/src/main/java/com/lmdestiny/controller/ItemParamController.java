package com.lmdestiny.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmdestiny.model.TbItemParam;
import com.lmdestiny.service.ItemParamService;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.IDUtils;
import com.lmdestiny.util.TaotaoResult;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService ItemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult query(@PathVariable Long itemCatId){
		return ItemParamService.getItemParamByCid(itemCatId);
	}
	//提交商品规格参数
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData){
		TbItemParam itemParam = new TbItemParam();
		itemParam.setParamData(paramData);
		itemParam.setId(IDUtils.genItemId());
		itemParam.setItemCatId(cid);
		itemParam.setUpdated(new Date());
		TaotaoResult  taoTaoResult= ItemParamService.save(itemParam);
		return taoTaoResult;
	}
	//得到所有规格参数
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getAllItem(Integer page,Integer rows){
		return ItemParamService.findItemByPageNo(page,rows);
	}
	//删除选中的规格参数
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult delete(String ids){
		
		return ItemParamService.delete(ids);
		
	}
	
}
