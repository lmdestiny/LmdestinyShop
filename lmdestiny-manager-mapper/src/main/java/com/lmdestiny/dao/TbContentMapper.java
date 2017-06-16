package com.lmdestiny.dao;

import java.util.List;

import com.lmdestiny.model.TbContent;

public interface TbContentMapper {

	List<TbContent> list(Long categoryId);

	void save(TbContent content);

	void delete(String ids);

	void update(TbContent content);

	List<TbContent> findByCategoryId(long cid);
    
}