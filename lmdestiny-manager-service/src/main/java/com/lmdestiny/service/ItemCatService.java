package com.lmdestiny.service;

import java.util.List;

import com.lmdestiny.model.TbItemCat;

public interface ItemCatService {

	List<TbItemCat> getItemCatList(Long parentId);

}
