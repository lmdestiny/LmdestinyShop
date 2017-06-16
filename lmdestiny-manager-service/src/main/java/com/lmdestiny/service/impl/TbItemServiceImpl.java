package com.lmdestiny.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmdestiny.dao.TbItemDescMapper;
import com.lmdestiny.dao.TbItemMapper;
import com.lmdestiny.dao.TbItemParamItemMapper;
import com.lmdestiny.model.TbItem;
import com.lmdestiny.model.TbItemDesc;
import com.lmdestiny.model.TbItemParamItem;
import com.lmdestiny.service.TbItemService;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.IDUtils;
import com.lmdestiny.util.TaotaoResult;


@Service
public class TbItemServiceImpl implements TbItemService{

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	//查询所有商品
	public List<TbItem> findAllItem() {
		return tbItemMapper.findAllTbItem();
	}
	//分页查询
	public EUDataGridResult findItemByPageNo(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<TbItem> items =tbItemMapper.findAllTbItem();
		PageInfo<TbItem>  pageInfo= new PageInfo<TbItem>(items);
		Long total = pageInfo.getTotal();
		EUDataGridResult eUDataGridResult = new EUDataGridResult();
		eUDataGridResult.setRows(items);
		eUDataGridResult.setTotal(total);
		return eUDataGridResult;
	}
	//保存商品
	@Transactional
	public TaotaoResult save(TbItem tbItem,String desc,String itemParam){
		tbItem.setId(IDUtils.genItemId());
		tbItem.setStatus((byte)1);
		tbItem.setUpdated(new Date());
		tbItemMapper.save(tbItem);
		if(this.save(tbItem.getId(), desc).getStatus() !=200){
			throw new RuntimeException();
		}
		if(this.insertItemParamItem(tbItem.getId(), itemParam).getStatus()!=200) {
			throw new RuntimeException();
		}
		return TaotaoResult.ok(tbItem);
	}
	
	@Transactional
	//保存商品描述
	public TaotaoResult save(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.save(itemDesc);
		return TaotaoResult.ok();
	}
	@Transactional
	//保存商品参数
	private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
		//创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setUpdated(new Date());
		//向表中插入数据
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
		
	}
	
	
}
