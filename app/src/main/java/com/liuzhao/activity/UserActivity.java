package com.liuzhao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuzhao.Bean.Person;
import com.liuzhao.R;
import com.liuzhao.application.NewApplication;
import com.liuzhao.common.Config;
import com.liuzhao.common.DefineView;
import com.liuzhao.common.DeliverConsts;
import com.liuzhao.utils.DataCleanManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2月15日0015.
 */

public class UserActivity extends BaseActivity implements DefineView, View.OnClickListener {
    private TextView one;
    private  TextView two;
    private TextView three;
    private  TextView four;
    private Context context;
    private HashMap<String,Object> tampmap = NewApplication.getInstance().getTempMap();
    private Button user_bt_login;
    private Person person ;
    private RelativeLayout user_user;
    private  RelativeLayout user_login;
    private  TextView tv_name;
    private  TextView tv_email;
    private  TextView qqlogin;
    private ImageView iv_bottom;
    public static Tencent mTencent;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private  IUiListener liulistener;
    private  TextView shareNews;
    private  TextView guanyu;
    private  TextView huancun;
    private  RelativeLayout  clean ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        context = this;
        setStatusBar();
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Config.QQAPPID, this);
        }
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        setTabState(two, R.drawable.tab_two_fill, ContextCompat.getColor(context,R.color.mask_tags_8));
        user_bt_login= (Button) findViewById(R.id.user_bt_login);
        user_user = (RelativeLayout) findViewById(R.id.user_user);
        user_login = (RelativeLayout) findViewById(R.id.user_login);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_email = (TextView) findViewById(R.id.tv_email);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        qqlogin = (TextView) findViewById(R.id.qqlogin);
        shareNews = (TextView) findViewById(R.id.shareNews);
        huancun = (TextView) findViewById(R.id.huancun);
        clean = (RelativeLayout) findViewById(R.id.clean);

        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaultbg).cacheInMemory(true)
                .cacheOnDisk(true).build();
    }

    @Override
    public void initValidata() {


    }

    @Override
    public void initListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        user_bt_login.setOnClickListener(this);
        qqlogin.setOnClickListener(this);
        shareNews.setOnClickListener(this);
        clean.setOnClickListener(this);

    }

    @Override
    public void bindData() {
        jianchua();
    }
    @Override
    public void onClick(View view) {
        resetTabState();//reset the tab state
        switch (view.getId()) {
            case R.id.one:
                openActivity(MainActivity.class);
                break;
            case R.id.two:




                break;
            case R.id.three:
                setTabState(three, R.drawable.like_fill, ContextCompat.getColor(context,R.color.colorPrimary));

                break;
            case R.id.four:
                setTabState(four, R.drawable.person_fill,ContextCompat.getColor(context,R.color.colorPrimary));

                break;
            case  R.id.user_bt_login:
                openActivity(LoginActivity.class);
                break;
            case R.id.qqlogin:
                login();

            break;
            case R.id.shareNews:
                onClickAppShare();
                break;
            case R.id.clean:
                cleanapp();
                break;
        }
    }
    public void jianchua(){
        try {
            long asd = DataCleanManager.getFolderSize(context.getExternalCacheDir());
            huancun.setText(DataCleanManager.getFormatSize(asd));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void cleanapp()
    {

        DataCleanManager.cleanExternalCache(context);
        jianchua();
        Toast.makeText(context,"清理缓存成功",Toast.LENGTH_SHORT).show();

    }
    private void onClickAppShare() {
        final Bundle params = new Bundle();
        Log.d("fenxiangying","asdasdasdas");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "分享IT新闻");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "IT新闻");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://c2.staticflickr.com/6/5728/32571171610_8c0c01b1a7_q.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "IT新闻");
        mTencent.shareToQQ(UserActivity.this, params, liulistener );
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
    //获取用户信息
    public void getUserInfo()
    {

        user_user.setVisibility(View.VISIBLE);
        user_login.setVisibility(View.GONE);
        Log.d("fanhui响应字符串2", "info");
        IUiListener infolistener = new IUiListener() {
            @Override
            public void onComplete(final Object o) {
                if(o == null){

                    return;
                }
                try {
                    person = new Person();
                    JSONObject jo = (JSONObject) o;

                    Log.d("fanhui响应字符串2", "rensheng"+jo.getString("nickname"));
                    tv_name.setText(jo.getString("nickname"));
                    tv_email.setText("");
                    mImageLoader.displayImage(jo.getString("figureurl_qq_2"),iv_bottom,options);
                    person.setId(jo.getString("ret"));
                    person.setUsername(jo.getString("nickname"));
                    person.setEmail("");
                     person.setLogo_url(jo.getString("figureurl_qq_2"));
                    person.setNobi(jo.getString("ret"));

                    tampmap.put(DeliverConsts.KEY_LOGIN,person);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };

        UserInfo info = new UserInfo(this, mTencent.getQQToken());
        info.getUserInfo(infolistener);


        Log.d("liuzhaohuoqu2",info.toString());
    }

    public void login()
    {
        mTencent = Tencent.createInstance(Config.QQAPPID, this.getApplicationContext());

        if (!mTencent.isSessionValid())
        {
            Log.d("fanhui响应字符串2", "rensheng");
            liulistener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Log.d("fanhuiasd", o.toString());
                    JSONObject jo = (JSONObject) o;
                    String openID = null;
                    try {
                        openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getUserInfo();
                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            };
            mTencent.login(this, "all",liulistener);
        }
/*       IUiListener listener = new IUiListener() {
           @Override
           public void onComplete(Object o) {
               Log.d("tencent响应字符串2", o.toString());
               JSONObject jo = (JSONObject) o;
               String openID = null;
               try {
                   openID = jo.getString("openid");
                   String accessToken = jo.getString("access_token");
                   String expires = jo.getString("expires_in");
                   mTencent.setOpenId(openID);
                   mTencent.setAccessToken(accessToken, expires);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
               getUserInfo();
           }

           @Override
           public void onError(UiError uiError) {

           }

           @Override
           public void onCancel() {

           }
       };*/
       // mTencent.login(this, "all",listener);


    }
  /*  private class BaseUiListener implements IUiListener {


        @Override
        public void onComplete(Object o) {
            Log.d("fanhui响应字符串2", "jinrucomplete");
            JSONObject jo = (JSONObject) o;
            String openID = null;
            try {
                openID = jo.getString("openid");
                String accessToken = jo.getString("access_token");
                String expires = jo.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            getUserInfo();


        }

        @Override
        public void onError(UiError e) {
            Log.d("fanhui错误", "rensheng");
//            Util.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
//            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Log.d("fanhui取消当前登录操作", "rensheng");
//            Util.toastMessage(context, "onCancel: ");
//            Util.dismissDialog();
        }
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        jianchua();
        person = (Person) tampmap.get(DeliverConsts.KEY_LOGIN);
        if (person != null){
            Log.d("liuzhao","you"+person.getUsername());
            user_user.setVisibility(View.VISIBLE);
            user_login.setVisibility(View.GONE);
            tv_name.setText("用户名："+person.getUsername());
            tv_email.setText("邮箱："+person.getEmail());
            mImageLoader.displayImage(person.getLogo_url(),iv_bottom,options);



        }else {
            Log.d("liuzhao","无");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, liulistener);

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, liulistener);
            }
        }

        //特别注意：一定要添加以下代码，才可以从回调listener中获取到消息。share
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }


}
