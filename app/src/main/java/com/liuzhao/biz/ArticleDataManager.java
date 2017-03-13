package com.liuzhao.biz;


import com.liuzhao.Bean.ArticleBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ArticleDataManager {
	private String articleID;
	public ArticleDataManager(){
	
	}
	public ArticleBean getArticleBean(Document document)
	{
		ArticleBean bean = new ArticleBean();
		Element singleElement = document.select("div#page").first();
		String title = singleElement.select("h1").text();
		String time = singleElement.select("div.article-info").first().select("span").text();
		String source = singleElement.select("div.article-info").first().select("a").text();
		String read = singleElement.select("em").first().text();
		
		String context= "";
		
		Elements elements = singleElement.select("div.article-detail").first().select("p");
		for (Element element2 : elements) {
			String asd = element2.toString();
			
			context += element2.toString();
			
		}
//		for(Element a : elements)
//		{
//			context += elements.toString();
//		}
//		String context = singleElement.select("div.post_content").first().toString();
//		System.out.println(singleElement);
//		System.out.println(context);
		bean.setContext(context);
		bean.setSource(source);
		bean.setTime(time);
		bean.setTitle(title);
		bean.setRead(read);
		
		return bean;
	}
}
