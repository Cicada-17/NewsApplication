package com.liuzhao.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuzhao.common.Config;
import com.liuzhao.weather.LocationBean;
import com.liuzhao.weather.NowBean;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2月17日0017.
 */

public class WeatherUtil {
     public static HashMap<String,Object> getNowWeather(String asd){
        final HashMap<String,Object> map = new HashMap<>();
        if (asd!=null) {
            Log.d("liuzhaotianqi","jinru weather1");

            String url = Config.WEATHERNOW + asd;
            OkhttpManager.getAsync(url, new OkhttpManager.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {
                    Log.d("liuzhaotianqi","jinru weather2");

                }

                @Override
                public void requestSuccess(String result) {
                    Log.d("liuzhaotianqi","jinru weather3");

                    try {
                        JSONObject result_object=new JSONObject(result);
                        JSONObject location=result_object.getJSONObject(Config.LOCATION);
                        JSONObject now = result_object.getJSONObject(Config.NOW);
                        JSONObject update = result_object.getJSONObject(Config.LAST_UPDATE);
                        Gson gson=new Gson();
                        NowBean nowBean = gson.fromJson(now.toString(), new TypeToken<NowBean>() {
                        }.getType());
                        map.put(Config.NOW,nowBean);
                        LocationBean locationBean = gson.fromJson(location.toString(),new TypeToken<LocationBean>(){}.getType());
                        map.put(Config.LOCATION,locationBean);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }
            });

            return  map;
        }else{
            return null;
        }
    }
}
