package com.liuzhao.biz;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuzhao.Bean.NewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsDataManager {
	public NewsDataManager(){}
	public List<NewsBean> getNewsBeans(String data)
	{
		
		List<NewsBean> newsBeans=null;
        try {
        	newsBeans=new ArrayList<NewsBean>();
        	System.out.println(data);
            JSONObject result_object=new JSONObject(data);
            Log.d("zhibo",data);
            JSONObject data_object=result_object.getJSONObject("data");
            JSONArray datas_array= data_object.getJSONArray("list");
            Gson gson=new Gson();
            newsBeans = gson.fromJson(datas_array.toString(), new TypeToken<List<NewsBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
		
//		for (Element element : elements) {
//			String title = element.select("div.news_title").select("a").text();
//			System.out.println(title+"asd");
//		}
		return newsBeans;
	}
}
