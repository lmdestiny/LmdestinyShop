package com.lmdestiny.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmdestiny.dao.TbContentCategoryMapper;
import com.lmdestiny.model.TbContentCategory;
import com.lmdestiny.service.ContentCategoryService;
import com.lmdestiny.util.EasyUITreeNode;
import com.lmdestiny.util.TaotaoResult;

@Transactional
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 返回树状结构
	 */
	public List<EasyUITreeNode> list(Long parentId) {
		// 根据parentid查询内容分类列表

		List<TbContentCategory> list = contentCategoryMapper.list(parentId);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			// 判断是否是父节点
			if (tbContentCategory.getIsParent()) {
				node.setState("closed");
			} else {
				node.setState("open");
			}
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult createNode(long parentid, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		contentCategory.setParentId(parentid);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		contentCategory.setUpdated(new Date());
		// 判断父节点的isParent
		TbContentCategory parent = contentCategoryMapper.getCategoryById(parentid);

		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			System.out.println(parent.getIsParent());
			contentCategoryMapper.update(parent);
		}
		contentCategoryMapper.save(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}

	/**
	 * 删除结点
	 */
	public TaotaoResult deleteNode(Long parentId, Long id) {
		TbContentCategory son = contentCategoryMapper.getCategoryById(id);
		// 判断父节点是否要修改
		List<TbContentCategory> list = contentCategoryMapper.list(parentId);
		if (list != null && list.size() > 1) {
		} else if (list != null && list.size() == 1) {
			if (parentId == 0) {
			} else {
				TbContentCategory parent = contentCategoryMapper.getCategoryById(parentId);
				parent.setIsParent(false);
				contentCategoryMapper.update(parent);
			}
		}
		System.out.println(son);
		contentCategoryMapper.updateStatus(son);
		System.out.println(son);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateNodename(Long id, String name) {
		TbContentCategory son = contentCategoryMapper.getCategoryById(id);
		son.setName(name);
		contentCategoryMapper.updateName(son);
		return TaotaoResult.ok();
	}

}
