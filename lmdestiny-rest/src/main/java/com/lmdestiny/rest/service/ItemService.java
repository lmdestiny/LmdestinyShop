package com.lmdestiny.rest.service;

import com.lmdestiny.util.TaotaoResult;

public interface ItemService{

	TaotaoResult getItemBaseInfo(Long itemId);

	TaotaoResult getItemDesc(Long itemId);
	TaotaoResult getItemParam(long itemId);

}
