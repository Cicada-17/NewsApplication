package com.liuzhao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liuzhao.R;
import com.liuzhao.common.DefineView;

/**
 * Created by Administrator on 2月13日0013.
 */

public class WebActivity extends BaseActivity implements DefineView{
    private Button btn_back;
    private WebView find_content;
    private FrameLayout home_frameLayout;
    private LinearLayout error,empty,loading;
    private String LinkURl=null;
    private RelativeLayout relative_content;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_layout);
        setStatusBar();
        setNeedBackGesture(true);
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {
        btn_back = (Button) this.findViewById(R.id.btn_back);
        find_content = (WebView) findViewById(R.id.find_content);
        home_frameLayout = (FrameLayout) findViewById(R.id.prompt_framelayout);
        error = (LinearLayout) this.findViewById(R.id.error);
        loading = (LinearLayout) this.findViewById(R.id.loading);
        empty = (LinearLayout) this.findViewById(R.id.empty);
        relative_content = (RelativeLayout) findViewById(R.id.relative_content);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initValidata() {
        Intent intent = getIntent();
        LinkURl = intent.getStringExtra("Url");
        Log.d("liuzhaoUrl",LinkURl);
        relative_content.setVisibility(View.GONE);
        home_frameLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        find_content.setWebChromeClient(new MyWebChromeClient());
        find_content.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = find_content.getSettings();
        webSettings.setJavaScriptEnabled(true);//开启JavaScript
        webSettings.setDomStorageEnabled(true);//开启Dom
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //web页面处理
        //webSettings.setAllowFileAccess(true);//支持文件流
        //webSettings.setSupportZoom(true);//支持缩放
        // webSettings.setBuiltInZoomControls(true);//支持缩放
        //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // webSettings.setUseWideViewPort(true);//调整到适合webview大小 有问题
        //webSettings.setLoadWithOverviewMode(true);//调整合适到webview大小 没效果
        //webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);//屏幕自适应网页 =低分辨率
        //webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高网页加载速度，暂时阻塞图片加载，然后网页加载完成，在进行图片加载
        webSettings.setBlockNetworkImage(false);
        //缓存
        webSettings.setAppCacheEnabled(true);


    }

    class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.d("liuzhaoWebChrome","加载进度发送变化");
        }
    }
    class  MyWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("liuzhaoweb","拦截的url="+url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("liuzhaoweb","网页开始加载"+url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(!find_content.getSettings().getLoadsImagesAutomatically()) {
                find_content.getSettings().setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("liuzhaoweb","加载的资源"+url);
        }


    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());


    }

    @Override
    public void bindData() {
        if (LinkURl!=null) {
            relative_content.setVisibility(View.VISIBLE);
            home_frameLayout.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            //details_content.loadData(articleBean.getContext(),"text/html;charset=UTF-8",null);
            //details_content.loadUrl("http://36kr.com/p/5063582.html");
            find_content.loadUrl(LinkURl);
           //find_content.loadDataWithBaseURL(null,articleBean.getContext(),"text/html","UTF-8","");

        }else {
            relative_content.setVisibility(View.GONE);
            home_frameLayout.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }

    }
    class  CustomOnClickListener implements  View.OnClickListener{

        @Override
        public void onClick(View v) {
            Toast.makeText(WebActivity.this,"点击了分享按钮",Toast.LENGTH_SHORT).show();
            WebActivity.this.finish();

        }

    }
}
