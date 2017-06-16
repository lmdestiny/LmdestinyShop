package com.lmdestiny.service;

import com.lmdestiny.model.TbContent;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.TaotaoResult;

public interface ContentService {

	EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

	TaotaoResult save(TbContent content);

	TaotaoResult delete(String ids);

	TaotaoResult update(TbContent content);

}
