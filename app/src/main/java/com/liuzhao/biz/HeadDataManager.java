package com.liuzhao.biz;

import com.liuzhao.Bean.AdHeadBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class HeadDataManager {
	public HeadDataManager(){}
	public List<AdHeadBean> getHeadBeans(Document document)
	{
		List<AdHeadBean> adHeadBeans = new ArrayList<AdHeadBean>();

		Elements headElements =  document.select("div.thumbs").first().select("div.thumb");
		for (Element element : headElements) {
			String imgurl = element.select("img").attr("src") ;
			String href =element.select("a").attr("href");
			String title =  element.select("p").text() ;
			System.out.println(imgurl);
			adHeadBeans.add(new AdHeadBean(imgurl, href, title));
		
			
		}
		
		return adHeadBeans;
	}
}
