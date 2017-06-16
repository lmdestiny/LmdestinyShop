package com.lmdestiny.dao;

import java.util.List;

import com.lmdestiny.model.TbItem;

public interface TbItemMapper {
	//查看所有商品
	public List<TbItem> findAllTbItem();

	public void save(TbItem tbItem);

	public TbItem getItem(Long itemId);
}