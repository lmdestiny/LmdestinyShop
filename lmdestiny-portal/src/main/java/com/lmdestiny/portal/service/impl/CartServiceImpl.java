package com.lmdestiny.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lmdestiny.model.TbItem;
import com.lmdestiny.portal.model.CartItem;
import com.lmdestiny.portal.service.CartService;
import com.lmdestiny.util.CookieUtils;
import com.lmdestiny.util.HttpClientUtil;
import com.lmdestiny.util.JsonUtils;
import com.lmdestiny.util.TaotaoResult;

@Service
public class CartServiceImpl implements CartService{
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;
	@Override
	public void add(Long itemId, Integer num){
		// 获得request,response
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) requestAttributes)
				.getResponse();
		List<CartItem> carts = getAllCart(request);
		CartItem tc = null;
		for (CartItem c : carts) {
			if (c.getId() == itemId) {
				c.setNum(c.getNum() + num);
				tc = c;
				break;
			}
		}
		if (tc == null) {
			tc = new CartItem();
			// 根据商品id查询商品基本信息。
			String json = HttpClientUtil
					.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(json,
					TbItem.class);
			if (formatToPojo.getStatus() == 200) {
				TbItem tbItem = (TbItem) formatToPojo.getData();
				tc.setId(itemId);
				tc.setNum(num);
				tc.setImage(tbItem.getImage() == null ? "":tbItem.getImage().split(",")[0]);
				tc.setPrice(tbItem.getPrice());
				tc.setTitle(tbItem.getTitle());
				carts.add(tc);
			}
		}
		CookieUtils.setCookie(request, response, "TT_CART",
				JsonUtils.objectToJson(carts));
	}
	
	
	private List<CartItem> getAllCart(HttpServletRequest request){
		String jsonData = CookieUtils.getCookieValue(request, "TT_CART", true);
		try {
			List<CartItem> list = JsonUtils.jsonToList(jsonData,
					CartItem.class);
			if (list != null)
				return list;
			else
				return new ArrayList<CartItem>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<CartItem>();
	}
	@Override
	public List<CartItem> display(){
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		return this.getAllCart(request);
	}
	@Override
	public TaotaoResult deleteCartItem(Long itemId) {
		// 获得request,response
				RequestAttributes requestAttributes = RequestContextHolder
						.currentRequestAttributes();
				HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
						.getRequest();
				HttpServletResponse response = ((ServletRequestAttributes) requestAttributes)
						.getResponse();
		
		//从cookie中取购物车商品列表
		List<CartItem> itemList = getAllCart(request);
		//从列表中找到此商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				itemList.remove(cartItem);
				break;
			}
				 
		}
		//把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		
		return TaotaoResult.ok();
	}



}
