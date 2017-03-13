package com.liuzhao.biz;

import com.liuzhao.Bean.NewsBean;
import com.liuzhao.common.Config;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;



public class XinHuaNewsDataManager {
	public List<NewsBean> getTVNewsBeanBeans(Document document)
	{
		List<NewsBean> newsBean = new ArrayList<>();
		
		Elements elements =document.select("ol.col3");

		for(Element element : elements){
			String href = element.select("a.srcLink").attr("href");
			String imageurl = Config.NEWIMAGEURL +element.select("p.pics").select("a").select("img").attr("src");
			String time = element.select("p.info>span").text();
			String title = element.select("p.title").text();
			NewsBean newsBean1 = new NewsBean();
			newsBean1.setM_display_url(href);
			newsBean1.setM_image_url(imageurl);
			newsBean1.setM_create_time(time);
			newsBean1.setM_title(title);
			newsBean.add(newsBean1);

		}
		return newsBean;
		
	}

}
