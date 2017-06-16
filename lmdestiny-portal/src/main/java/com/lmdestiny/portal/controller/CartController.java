package com.lmdestiny.portal.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lmdestiny.portal.model.CartItem;
import com.lmdestiny.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController{
	@Autowired
	private CartService cartService;
	@RequestMapping("/add/{itemId}")
	public String add(@PathVariable Long itemId,@RequestParam(defaultValue="1") Integer num) {
		cartService.add(itemId,num);
		return "redirect:/cart/cart.html";
	}
	@RequestMapping("/cart")
	public String display(Model model) {
		List<CartItem> items = cartService.display();
		model.addAttribute("cartList", items);
		return "cart";
	}
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId) {
		cartService.deleteCartItem(itemId);
		return "redirect:/cart/cart.html";
	}

}
