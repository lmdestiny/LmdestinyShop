package com.lmdestiny.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolr{
	final String  baseURL = "http://192.168.226.159:8080/solr";
	@Test
	public void addDocument() throws SolrServerException, IOException {
		//创建一个连接
		SolrServer server = new HttpSolrServer(baseURL); 
		//创建一个文档对象
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", "test001");
				document.addField("item_title", "测试商品2");
				document.addField("item_price", 54321);
				//把文档对象写入索引库
				server.add(document);
				//提交
				server.commit();
	}
	@Test
	public void deleteDocument() throws SolrServerException, IOException {
		//创建一连接
		SolrServer solrServer = new HttpSolrServer(baseURL);
		//solrServer.deleteById("test001");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
