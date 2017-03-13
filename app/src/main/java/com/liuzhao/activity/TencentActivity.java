package com.liuzhao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzhao.R;
import com.liuzhao.common.Config;
import com.liuzhao.tencent.Util;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2月16日0016.
 */

public class TencentActivity extends BaseActivity implements View.OnClickListener {
    public static Tencent mTencent;

    private static final String mAppid = Config.QQAPPID;
    private Button tencenttext;
    private Button fenxiang;
    private Button huoquxinxi;
    private ImageView touxiang;
    private TextView tencent_tv;

    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tencent_test_layout);

        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }
        tencenttext = (Button) findViewById(R.id.tencenttext);
        fenxiang = (Button) findViewById(R.id.fenxiang);
        huoquxinxi = (Button) findViewById(R.id.huoquxinxi);
        touxiang = (ImageView) findViewById(R.id.touxiang);
        tencent_tv = (TextView) findViewById(R.id.tencent_tv);

        fenxiang.setOnClickListener(this);
        tencenttext.setOnClickListener(this);
        huoquxinxi.setOnClickListener(this);

        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaultbg).cacheInMemory(true)
                .cacheOnDisk(true).build();
    }




    private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(TencentActivity.this, params, qqShareListener);
                }
            }
        });
    }


    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Util.toastMessage(TencentActivity.this, "onCancel: ");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(TencentActivity.this, "onComplete: " + response.toString());
            Log.d("liuzhaoshare","传成功"+response);
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(TencentActivity.this, "onError: " + e.errorMessage, "e");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);

        //特别注意：一定要添加以下代码，才可以从回调listener中获取到消息。share
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }

    public void login()
    {
        mTencent = Tencent.createInstance(Config.QQAPPID, this.getApplicationContext());
        if (!mTencent.isSessionValid())
        {
            IUiListener listener = new BaseUiListener();
            mTencent.login(this, "all",listener);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tencenttext:
                login();

                break;
            case R.id.fenxiang:
                share();
                Log.d("liuzhaofen","进入");
                break;
            case R.id.huoquxinxi:
                getUserInfo();
                break;

        }


    }
    private class BaseUiListener implements IUiListener {


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
        public void onError(UiError e) {
            Log.d("tencent错误信息", e.errorDetail);
//            Util.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
//            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Log.d("tencent取消了当前的登录操作", "取消了当前的登录操作");
//            Util.toastMessage(context, "onCancel: ");
//            Util.dismissDialog();
        }
    }
    //分享qq空间
    private void shareToQzone () {
        //分享类型
        Bundle params = new Bundle();
        ArrayList<String> list = new ArrayList<String>();
        list.add("http://tp-y.zdmimg.com/201702/16/58a5b881d09224741.jpg_d250.jpg");
        params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, String.valueOf(QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT));
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.smzdm.com/");//必填跳转地址
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);// ArrayList<String> 图片
        mTencent.shareToQzone(this, params, qqShareListener);
    }

    //获取用户信息
    public void getUserInfo()
    {
        IUiListener infolistener = new IUiListener() {
            @Override
            public void onComplete(final Object o) {
                if(o == null){
                    return;
                }
                try {

                    JSONObject jo = (JSONObject) o;
                    tencent_tv.setText(jo.getString("nickname"));
                    Log.d("liuzhaohuoqu2",jo.getString("nickname"));
                    Log.d("liuzhaohuoqu3",jo.getString("figureurl_qq_2"));

                    mImageLoader.displayImage(jo.getString("figureurl_qq_2"),touxiang,options);



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

    //分享信息到QQ图文
    public void share()
    {

        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
//分享的消息摘要，最长50个字
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "liu新闻");
/*分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
        QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
        QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮*/
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);

        mTencent.shareToQQ(this, params , qqShareListener);
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        Log.d("liuzhaohuoqu","asd2");
                        tencent_tv.setText(response.getString("nickname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                Bitmap bitmap = (Bitmap)msg.obj;
                touxiang.setImageBitmap(bitmap);
            }
        }

    };


}