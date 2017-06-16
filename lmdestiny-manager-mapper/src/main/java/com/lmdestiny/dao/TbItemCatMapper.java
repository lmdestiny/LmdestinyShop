package com.lmdestiny.dao;

import java.util.List;

import com.lmdestiny.model.TbItemCat;

public interface TbItemCatMapper {

	List<TbItemCat> getItemCatList(Long parentId);

}