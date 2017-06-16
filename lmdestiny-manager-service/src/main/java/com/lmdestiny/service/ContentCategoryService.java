package com.lmdestiny.service;

import java.util.List;

import com.lmdestiny.util.EasyUITreeNode;
import com.lmdestiny.util.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUITreeNode> list(Long parentId);

	TaotaoResult createNode(long parentid, String name);

	TaotaoResult deleteNode(Long parentId, Long id);

	TaotaoResult updateNodename(Long id, String name);

}
