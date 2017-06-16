package com.lmdestiny.portal.service;

import com.lmdestiny.portal.model.ItemInfo;

public interface ItemService{

	ItemInfo getItemById(Long itemId);
	String getItemDescById(Long itemId);
	String getItemParam(Long itemId);

}
