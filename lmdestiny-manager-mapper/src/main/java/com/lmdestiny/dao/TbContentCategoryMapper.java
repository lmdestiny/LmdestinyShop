package com.lmdestiny.dao;

import java.util.List;

import com.lmdestiny.model.TbContentCategory;

public interface TbContentCategoryMapper {

	List<TbContentCategory> list(Long parentId);

	TbContentCategory getCategoryById(long parentid);

	void update(TbContentCategory parent);

	void save(TbContentCategory contentCategory);

	void updateStatus(TbContentCategory son);

	void updateName(TbContentCategory son);

  
}