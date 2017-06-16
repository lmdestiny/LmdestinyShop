package com.lmdestiny.portal.service;

import java.util.List;


import com.lmdestiny.portal.model.CartItem;
import com.lmdestiny.util.TaotaoResult;

public interface CartService{

	void add(Long itemId, Integer num);

	List<CartItem> display();

	TaotaoResult deleteCartItem(Long itemId);

}
