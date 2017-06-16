package com.lmdestiny.dao;

import com.lmdestiny.model.TbItemDesc;

public interface TbItemDescMapper {

	void save(TbItemDesc itemDesc);

	TbItemDesc getItemDesc(Long itemId);
	
}