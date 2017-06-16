package com.lmdestiny.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SupporteController {
	
	@RequestMapping("/toPageIndex")
	public String toPageIndex(){
		return "index";
	}
	@RequestMapping("/{page}")
	public String toList(@PathVariable String page){
		return page;
	}
	
}
