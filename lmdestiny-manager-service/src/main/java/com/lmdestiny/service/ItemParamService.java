package com.lmdestiny.service;

import com.lmdestiny.model.TbItemParam;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.TaotaoResult;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(Long itemCatId);

	TaotaoResult save(TbItemParam itemParam);

	EUDataGridResult findItemByPageNo(Integer page, Integer rows);

	TaotaoResult delete(String ids);


}
