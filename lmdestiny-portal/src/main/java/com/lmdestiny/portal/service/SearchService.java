package com.lmdestiny.portal.service;

import com.lmdestiny.portal.model.SearchResult;

public interface SearchService{

	SearchResult search(String queryString, Integer page);

}
