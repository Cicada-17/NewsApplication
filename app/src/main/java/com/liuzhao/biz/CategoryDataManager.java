package com.liuzhao.biz;

import android.util.Log;

import com.liuzhao.Bean.CategoryBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;



public class CategoryDataManager {
	public CategoryDataManager(){}
	public List<CategoryBean> getCategoryBeans(Document document)
	{	
		List<CategoryBean> categoryBean = new ArrayList<CategoryBean>();
		Elements elements = document.select("div.category_nav").first().select("a");
	
		for (Element element : elements) {
			
			String title = element.text();
			String href = element.attr("abs:href");
			if(title.equals("IT资讯首页"))continue;
			categoryBean.add(new CategoryBean(title, href));
			
			
		}
		Log.d("liuzhao",categoryBean.toString());
		return categoryBean;
	}
	
}
