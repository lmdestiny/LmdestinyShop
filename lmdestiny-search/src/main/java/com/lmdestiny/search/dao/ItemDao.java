package com.lmdestiny.search.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lmdestiny.search.model.Item;
@Component
public interface ItemDao{
	List<Item> getItems();
}
