package com.lmdestiny.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lmdestiny.portal.model.SearchResult;
import com.lmdestiny.portal.service.SearchService;
import com.lmdestiny.util.HttpClientUtil;
import com.lmdestiny.util.TaotaoResult;
@Service
public class SearchServiceImpl implements SearchService{
	@Value("${SEARCH_BASE_URL}")
	private  String  SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, Integer page){
		Map<String,String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		TaotaoResult formatToPojo = TaotaoResult.formatToPojo(json, SearchResult.class);
		if(formatToPojo.getStatus() ==200) {
			SearchResult result = (SearchResult)formatToPojo.getData();
			return result;
		}
		return null;
	}

}
