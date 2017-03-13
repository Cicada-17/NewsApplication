package com.liuzhao.biz;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuzhao.Bean.RecentNewsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 当前类注释:近期获取数据解析工具类
 */
public class RecentDataManager {
    public static List<RecentNewsBean> getRecentDatas(String data){
        List<RecentNewsBean> recentNewsBeans=null;
        try {
            recentNewsBeans=new ArrayList<RecentNewsBean>();
            JSONObject result_object=new JSONObject(data);
            JSONObject data_object=result_object.getJSONObject("data");
            JSONArray datas_array= data_object.getJSONArray("data");
            Gson gson=new Gson();
            recentNewsBeans = gson.fromJson(datas_array.toString(), new TypeToken<List<RecentNewsBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recentNewsBeans;
    }
}
