package com.lmdestiny.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmdestiny.dao.TbItemCatMapper;
import com.lmdestiny.model.TbItemCat;
import com.lmdestiny.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	public List<TbItemCat> getItemCatList(Long parentId) {
		
		return itemCatMapper.getItemCatList(parentId);
	}

}
