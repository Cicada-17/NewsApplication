package com.liuzhao.biz;

import com.liuzhao.Bean.NewsBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2月08日0008.
 */

public class TempNewsDataManager {
    public TempNewsDataManager(){}
    public List<NewsBean> getTempNewsBeans(Document document)
    {
        List<NewsBean> newsBean = new ArrayList<NewsBean>();
        Elements elements = document.select("li");
        for (Element element : elements) {

            String title = element.select("h2>a").text();
            String href = element.select("a").first().attr("abs:href");

            String imgurl = element.select("a").first().select("img").attr("src");
            String datetime = element.select("h2>span").text();
            String text = element.select("div.memo").text();
            String newstag = element.select("span.tags").select("a").text();

            //newsBean.add(new NewsBean(imgurl, title, datetime, newstag, href, text));
        }

//		for (Element element : elements) {
//			String title = element.select("div.news_title").select("a").text();
//			System.out.println(title+"asd");
//		}
        return newsBean;
    }
}
