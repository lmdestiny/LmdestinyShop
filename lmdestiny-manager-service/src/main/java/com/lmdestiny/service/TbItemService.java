package com.lmdestiny.service;

import java.util.List;

import com.lmdestiny.model.TbItem;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.TaotaoResult;

public interface TbItemService {
	public List<TbItem> findAllItem();
	public EUDataGridResult findItemByPageNo(Integer pages, Integer rows);
	public TaotaoResult save(TbItem tbItem,String desc,String param);
	public TaotaoResult save(Long tbItem,String desc);
}
