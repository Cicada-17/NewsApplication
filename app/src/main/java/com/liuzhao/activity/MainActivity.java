package com.liuzhao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liuzhao.R;
import com.liuzhao.application.NewApplication;
import com.liuzhao.common.Config;
import com.liuzhao.common.DefineView;
import com.liuzhao.common.DeliverConsts;
import com.liuzhao.common.RequestURL;
import com.liuzhao.update.UpdateInfoModel;
import com.liuzhao.update.UpdateReceiver;
import com.liuzhao.utils.OkhttpManager;
import com.liuzhao.weather.ResultTwoBean;
import com.liuzhao.weather.ResultsBean;
import com.liuzhao.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.okhttp.Request;
import com.tencent.tauth.Tencent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements DefineView, View.OnClickListener {
    public DragLayout getDrag_layout() {
        return drag_layout;

    }

    private DragLayout drag_layout;
    private ImageView top_bar_icon;
   /* private ListView lv_left_main;*/

    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    private Context context;

    private ImageView iv_bottom;
    private Resources resources;
    private TextView cityname;
    private TextView updatetime;

    private ImageView jintianimage;
    private TextView jintiantq;
    private TextView jintianqw;
    private TextView jintianfeng;

    private ImageView mingtianimage;
    private TextView mingtiantq;
    private TextView mingtianqw;
    private TextView mingtianfeng;

    private ImageView houtianimage;
    private TextView houtiantq;
    private TextView houtianqw;
    private TextView houtianfeng;
    //获取经纬度
    private LocationManager locationManager;
    private String locationProvider;




    //更新app
    private HashMap<String, Object> tmpMap = NewApplication.getInstance()
            .getTempMap();
    private UpdateReceiver mUpdateReceiver;
    private IntentFilter mIntentFilter;

    //tencent 腾讯调用类
    private Tencent mTencent;
    private TextView testcity;
    private TextView wendu;
    private TextView tianqitype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        //测试qq连接
        mTencent = Tencent.createInstance(Config.QQAPPID, this.getApplicationContext());


        registerBroadcast();// 注册广播自动更新
        context = this;
        initView();
        initListener();
        initValidata();
        bindData();
        setJingwei();
        jiazaitianqi();
    }
    public void setJingwei(){
        Log.d("liuzhaoGBL","jinru");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this,"没有可用的位置提供器",Toast.LENGTH_SHORT);
            return;
        }
        Log.d("liuzhaoGBL","jinru2");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
            String jingwei = df.format(location.getLatitude())+":"+df.format(location.getLongitude());
            NewApplication.getInstance().setCity(jingwei);
            Log.d("liuzhaoGBL","定位成功------->"+location+"------>" + "经度为：" + location.getLatitude() + "纬度为"+location.getLongitude());
        }
//监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            Log.d("liuzhaoGBL","定位变换------->"+location+"------>" + "经度为：" + location.getLatitude() + "\n纬度为" + location.getLongitude());
        }
    };
    public int getResource(String imageName) {
        Class drawable = R.drawable.class;
        try {
            Field field = drawable.getField(imageName);
            int resId = field.getInt(imageName);
            return resId;
        } catch (NoSuchFieldException e) {//如果没有在"mipmap"下找到imageName,将会返回0
            return 0;
        } catch (IllegalAccessException e) {
            return 0;
        }

    }

    @Override
    public void initView() {
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);

        drag_layout = (DragLayout) findViewById(R.id.drag_layout);
        top_bar_icon = (ImageView) findViewById(R.id.top_bar_icon);
       /* lv_left_main = (ListView) findViewById(R.id.lv_left_main);*/
        cityname = (TextView) findViewById(R.id.cityname);
        updatetime = (TextView) findViewById(R.id.updatetime);
        wendu = (TextView) findViewById(R.id.wendu);
        tianqitype = (TextView) findViewById(R.id.tianqitype);
        jintianimage = (ImageView) findViewById(R.id.jintianimage);
        jintiantq = (TextView) findViewById(R.id.jintiantq);
        jintianqw = (TextView) findViewById(R.id.jintianqw);
        jintianfeng = (TextView) findViewById(R.id.jintianfeng);

        mingtianimage = (ImageView) findViewById(R.id.mingtianimage);
        mingtiantq = (TextView) findViewById(R.id.mingtiantq);
        mingtianqw = (TextView) findViewById(R.id.mingtianqw);
        mingtianfeng = (TextView) findViewById(R.id.mingtianfeng);

        houtianimage = (ImageView) findViewById(R.id.houtianimage);
        houtiantq = (TextView) findViewById(R.id.houtiantq);
        houtianqw = (TextView) findViewById(R.id.houtianqw);
        houtianfeng = (TextView) findViewById(R.id.houtianfeng);


        setTabState(one, R.drawable.tab_one_fill, ContextCompat.getColor(context, R.color.mask_tags_8));


    }

    public  void  jiazaitianqi(){

        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        String url = Config.WEATHERNOW + NewApplication.getInstance().getCity();
        String url1 = Config.WEATHERDAILY+ NewApplication.getInstance().getCity();
        Log.d("liuzhaotianqi","jinru1"+url);
        OkhttpManager.getAsync(url1, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) {
              try {
                  JSONObject data = new JSONObject(result);
                  JSONArray result_object = data.getJSONArray(Config.RESULTS);
                  Gson gson=new Gson();
                  List<ResultTwoBean> resultsBeen = new ArrayList<ResultTwoBean>();

                  resultsBeen = gson.fromJson(result_object.toString(), new TypeToken<List<ResultTwoBean>>() {
                  }.getType());
                  jintianimage.setImageResource(getResource("w"+resultsBeen.get(0).getDaily().get(0).getCode_day()));
                  jintianfeng.setText(resultsBeen.get(0).getDaily().get(0).getWind_scale()+"级"+resultsBeen.get(0).getDaily().get(0).getWind_direction()+"风");
                  jintianqw.setText(resultsBeen.get(0).getDaily().get(0).getLow()+"/"+resultsBeen.get(0).getDaily().get(0).getHigh()+"℃");
                  jintiantq.setText(resultsBeen.get(0).getDaily().get(0).getText_day());

                  mingtianimage.setImageResource(getResource("w"+resultsBeen.get(0).getDaily().get(1).getCode_day()));
                  mingtianfeng.setText(resultsBeen.get(0).getDaily().get(1).getWind_scale()+"级"+resultsBeen.get(0).getDaily().get(1).getWind_direction()+"风");
                  mingtianqw.setText(resultsBeen.get(0).getDaily().get(1).getLow()+"/"+resultsBeen.get(0).getDaily().get(1).getHigh()+"℃");
                  mingtiantq.setText(resultsBeen.get(0).getDaily().get(1).getText_day());

                  houtianimage.setImageResource(getResource("w"+resultsBeen.get(0).getDaily().get(2).getCode_day()));
                  houtianfeng.setText(resultsBeen.get(0).getDaily().get(2).getWind_scale()+"级"+resultsBeen.get(0).getDaily().get(2).getWind_direction()+"风");
                  houtianqw.setText(resultsBeen.get(0).getDaily().get(2).getLow()+"/"+resultsBeen.get(0).getDaily().get(2).getHigh()+"℃");
                  houtiantq.setText(resultsBeen.get(0).getDaily().get(2).getText_day());
                  Log.d("ericresult",resultsBeen.get(0).getDaily().get(0).getText_day());
              }catch (JSONException e) {
                  e.printStackTrace();
              }


            }
        });

        OkhttpManager.getAsync(url, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("liuzhaotianqi","jinru weather2");

            }

            @Override
            public void requestSuccess(String result) {

                Log.d("liuzhaotianqi","jinru34"+result );
                try {
                    JSONObject data=new JSONObject(result);
                    JSONArray result_object =data.getJSONArray(Config.RESULTS);
                    Gson gson=new Gson();
                    List<ResultsBean> resultsBeen = new ArrayList<ResultsBean>();

                    resultsBeen = gson.fromJson(result_object.toString(), new TypeToken<List<ResultsBean>>() {
                    }.getType());
                    iv_bottom.setImageResource(getResource("w"+resultsBeen.get(0).getNow().getCode()));
                    cityname.setText(resultsBeen.get(0).getLocation().getName());
                    updatetime.setText("更新时间:"+resultsBeen.get(0).getLast_update().substring(11,16));
                    wendu.setText(resultsBeen.get(0).getNow().getTemperature()+"℃");
                    tianqitype.setText(resultsBeen.get(0).getNow().getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        });
    }
    @Override
    public void initValidata() {
       /* lv_left_main.setAdapter(new LeftItemAdapter());*/
        OkhttpManager.getAsync(RequestURL.UPDATE_URL, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("liuzhaoUp","area失败");
            }

            @Override
            public void requestSuccess(String result) {
                Log.d("liuzhaoUP",result);
                try{
                    JSONObject object = new JSONObject(result);
                    UpdateInfoModel  model = new UpdateInfoModel();
                    model.setAppname(object.getString("appname"));
                    model.setLastForce(object.getString("lastForce"));
                    model.setServerFlag(object.getString("serverFlag"));
                    model.setServerVersion(object.getString("serverVersion"));
                    model.setUpdateurl(object.getString("updateurl"));
                    model.setUpgradeinfo(object.getString("upgradeinfo"));
                    tmpMap.put(DeliverConsts.KEY_APP_UPDATE, model);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                sendBroadcast(new Intent(UpdateReceiver.UPDATE_ACTION));
            }
        });


    }

    @Override
    public void initListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        drag_layout.setDragListener(new CustomDragListener());
        top_bar_icon.setOnClickListener(new CustomOnClickListener());
     /*   lv_left_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1://发现

                        break;
                    case 2://关注
                        break;
                    case 3://收藏
                        break;
                    case 4://意见反馈
                        break;
                    case 5://设置
                        break;
                }
            }
        });*/

    }

    @Override
    public void bindData() {


    }

    class CustomDragListener implements DragLayout.DragListener {

        /**
         * 界面打开
         */
        @Override
        public void onOpen() {

        }

        /**
         * 界面关闭
         */
        @Override
        public void onClose() {

        }

        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(top_bar_icon, 1 - percent);
        }
    }

    class CustomOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            drag_layout.open();
        }
    }



    @Override
    public void onClick(View view) {
        resetTabState();//reset the tab state
        switch (view.getId()) {
            case R.id.one:
                setTabState(one, R.drawable.tab_one_fill, ContextCompat.getColor(context,R.color.mask_tags_8));//设置Tab状态

                break;
            case R.id.two:

               openActivity(FindActivity.class);
                this.finish();



                break;
            case R.id.three:
              openActivity(DouyuActivity.class);

                break;
            case R.id.four:
              /*  openActivity(VitamioActivity.class);*/
                openActivity(UserActivity.class);
                this.finish();


                break;
        }
    }


    private void setTabState(TextView textView, int image, int color) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);
        textView.setTextColor(color);
    }


    private void resetTabState() {
        setTabState(one, R.drawable.tab_one, ContextCompat.getColor(context,R.color.color_black));

        setTabState(two, R.drawable.tab_two, ContextCompat.getColor(context,R.color.color_black));
        setTabState(three, R.drawable.tab_three, ContextCompat.getColor(context,R.color.color_black));
        setTabState(four, R.drawable.tab_four,ContextCompat.getColor(context,R.color.color_black));

    }

    /**
     * 广播注册
     */
    private void registerBroadcast() {
        mUpdateReceiver = new UpdateReceiver(false);
        mIntentFilter = new IntentFilter(UpdateReceiver.UPDATE_ACTION);
        this.registerReceiver(mUpdateReceiver, mIntentFilter);
    }

    /**
     * 广播卸载
     */
    private void unRegisterBroadcast() {
        try {
            this.unregisterReceiver(mUpdateReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterBroadcast();
    }
}
