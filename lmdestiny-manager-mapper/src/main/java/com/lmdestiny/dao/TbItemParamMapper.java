package com.lmdestiny.dao;

import java.util.List;

import com.lmdestiny.model.TbItemParam;
import com.lmdestiny.model.vo.TbItemParamWithName;

public interface TbItemParamMapper {
	
	public List<TbItemParam> getEntityById(Long itemCatId);

	public void save(TbItemParam itemParam);

	public List<TbItemParamWithName> findAllTbItem();

	public void delete(String ids);
	
}