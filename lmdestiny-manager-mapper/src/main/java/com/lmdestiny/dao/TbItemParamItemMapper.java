package com.lmdestiny.dao;

import java.util.List;

import com.lmdestiny.model.TbItemParamItem;

public interface TbItemParamItemMapper {

	List<TbItemParamItem> getItemParamItem(long itemId);

	void insert(TbItemParamItem itemParamItem);
}