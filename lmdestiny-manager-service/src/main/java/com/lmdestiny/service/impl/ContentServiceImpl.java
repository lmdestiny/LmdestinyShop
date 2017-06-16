package com.lmdestiny.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmdestiny.dao.TbContentMapper;
import com.lmdestiny.model.TbContent;
import com.lmdestiny.service.ContentService;
import com.lmdestiny.util.EUDataGridResult;
import com.lmdestiny.util.HttpClientUtil;
import com.lmdestiny.util.TaotaoResult;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		List<TbContent> contents =  contentMapper.list(categoryId);
		PageHelper.startPage(page, rows);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contents);
		EUDataGridResult dataGridResult = new EUDataGridResult();
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(contents);
		return dataGridResult;
	}

	@Override
	public TaotaoResult save(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.save(content);
		try{
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult delete(String ids) {
		contentMapper.delete(ids);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult update(TbContent content) {
		contentMapper.update(content);
		try{
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

}
