package com.liuzhao.biz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuzhao.Bean.RoomItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoomItemManager {
	 public static List<RoomItemBean> getRoomItem(String data){
	        List<RoomItemBean> roomItemBeans=null;
	        try {
	        	roomItemBeans=new ArrayList<RoomItemBean>();
	            JSONObject result_object=new JSONObject(data);
	            JSONArray datas_array= result_object.getJSONArray("data");
	            Gson gson=new Gson();
	            roomItemBeans = gson.fromJson(datas_array.toString(), new TypeToken<List<RoomItemBean>>() {
	            }.getType());
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return roomItemBeans;
	    }

}
