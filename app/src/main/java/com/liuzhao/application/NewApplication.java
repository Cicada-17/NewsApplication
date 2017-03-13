package com.liuzhao.application;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2月05日0005.
 */

public class NewApplication extends Application{
    private static  NewApplication instance= null;
    private  String City = "beijing";
    private  String GPS = null;

    public  String getCity()
    {
        return  this.City;
    }
    public  void  setCity(String city){
        if (city != null){
            this.City = city;
        }
    }

    private HashMap<String, Object> tempMap = new HashMap<String, Object>();
    public HashMap<String, Object> getTempMap() {
        return tempMap;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.instance=this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initImageLoader();
        Log.d("liuzhao","创建了Content");
    }

    public static NewApplication getInstance(){return instance;}
    private  void  initImageLoader(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
}
