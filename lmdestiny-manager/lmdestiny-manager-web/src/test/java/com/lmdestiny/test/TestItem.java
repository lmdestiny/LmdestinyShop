package com.lmdestiny.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/beans.xml")
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)
@Transactional
@WebAppConfiguration
public class TestItem {
	protected MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;
	@Before
	public void setup(){
		//初始化MockMvc对象
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	@Test
	public void test() throws UnsupportedEncodingException, Exception{
		RequestBuilder requestBuilder = get("/item/getAll.do")
				.contentType(MediaType.ALL);
				//.param(name, values)
		String responseString = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn().getResponse().getContentAsString();
		System.out.println(responseString);
	}
	
	
	
	
	
	
	
	
	
	
	
}
