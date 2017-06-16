package com.lmdestiny.rest.service;
import com.lmdestiny.util.TaotaoResult;

public interface RedisService {
	TaotaoResult syncContent(long contentCid);
}
