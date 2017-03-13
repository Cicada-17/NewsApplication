package com.liuzhao.biz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuzhao.Bean.RoomBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2月19日0019.
 */

public class RoomManager {
    public static RoomBean getRoom(String data){
       RoomBean roomBeen=null;
        try {
            roomBeen=new RoomBean();
            JSONObject result_object=new JSONObject(data);
            JSONObject room = result_object.getJSONObject("data");
            Gson gson=new Gson();
            roomBeen = gson.fromJson(room.toString(), new TypeToken<RoomBean>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roomBeen;
    }
}
