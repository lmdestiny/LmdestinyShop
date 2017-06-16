package com.lmdestiny.rest.service;

import java.util.List;

import com.lmdestiny.model.TbContent;

public interface ContentService {

	List<TbContent> getContentList(long contentCid);
}
