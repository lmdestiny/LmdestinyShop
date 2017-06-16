package com.lmdestiny.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmdestiny.dao.TbItemParamMapper;
import com.lmdestiny.model.TbItemParam;
import com.lmdestiny.model.vo.TbItemParamWithName;
import com.lmdestiny.service.ItemParamService;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.TaotaoResult;
@Service
@Transactional
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	/**
	 * 判断规格参数是否存在
	 */
	public TaotaoResult getItemParamByCid(Long itemCatId) {
		List<TbItemParam> list = itemParamMapper.getEntityById(itemCatId);
		if(list!=null && list.size()>0){
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}
	//保存规格参数
	public TaotaoResult save(TbItemParam itemParam) {
		itemParamMapper.save(itemParam);
		return TaotaoResult.ok();
	}
	@Override
	public EUDataGridResult findItemByPageNo(Integer page, Integer rows) {
		PageHelper.startPage(page,rows);
		List<TbItemParamWithName> itemParamList =itemParamMapper.findAllTbItem();
		PageInfo<TbItemParamWithName>  pageInfo= new PageInfo<TbItemParamWithName>(itemParamList);
		Long total = pageInfo.getTotal();
		EUDataGridResult eUDataGridResult = new EUDataGridResult();
		eUDataGridResult.setRows(itemParamList);
		eUDataGridResult.setTotal(total);
		return eUDataGridResult;
	}
	@Override
	public TaotaoResult delete(String ids) {
		itemParamMapper.delete(ids);
		return TaotaoResult.ok();
	}

}
