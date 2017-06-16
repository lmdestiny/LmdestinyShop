package com.lmdestiny.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lmdestiny.dao.TbItemDescMapper;
import com.lmdestiny.dao.TbItemMapper;
import com.lmdestiny.dao.TbItemParamItemMapper;
import com.lmdestiny.model.TbItem;
import com.lmdestiny.model.TbItemDesc;
import com.lmdestiny.model.TbItemParamItem;
import com.lmdestiny.rest.dao.JedisClient;
import com.lmdestiny.rest.service.ItemService;
import com.lmdestiny.util.JsonUtils;
import com.lmdestiny.util.TaotaoResult;
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	
	@Override
	public TaotaoResult getItemBaseInfo(Long itemId){
		TbItem item = null;
		//如果有缓存从缓存中取值
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
			if(!StringUtils.isBlank(json)) {
				item = JsonUtils.jsonToPojo(json, TbItem.class);
				return TaotaoResult.ok(item);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		item = itemMapper.getItem(itemId);
		try {
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}


	@Override
	public TaotaoResult getItemDesc(Long itemId){
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
			if(!StringUtils.isBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.getItemDesc(itemId);
		try {
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc", JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(itemDesc);
	}
	
	//商品规格
	@Override
	public TaotaoResult getItemParam(long itemId) {
		//添加缓存
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaotaoResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TbItemParamItem> list = itemParamItemMapper.getItemParamItem(itemId);
		if (list != null && list.size()>0) {
			TbItemParamItem paramItem = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(paramItem);
		}
		return TaotaoResult.build(400, "无此商品规格");
	}


}
