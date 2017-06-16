package com.lmdestiny.search.service;

import com.lmdestiny.search.model.SearchResult;

public interface SearchService {

	SearchResult search(String queryString, int page, int rows) throws Exception;
}
