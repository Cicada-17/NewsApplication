package com.liuzhao.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.Liu.utils.PullToRefreshListView;
import com.liuzhao.Bean.ArticleBean;
import com.liuzhao.R;
import com.liuzhao.biz.ArticleDataManager;
import com.liuzhao.common.Config;
import com.liuzhao.common.DefineView;
import com.liuzhao.tencent.Util;
import com.liuzhao.utils.OkhttpManager;
import com.squareup.okhttp.Request;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2月09日0009.
 */

public class ItNewsDetaActivity extends  BaseActivity implements DefineView{
    private Button btn_back;
    private  Button btn_share;
    private  Button btn_font;
    private  Button btn_night;
    private TextView details_title;
    private  TextView details_name;
    private  TextView details_time;
    private  TextView details_source;
    private WebView details_content;
    private PullToRefreshListView home_listview;
    private FrameLayout home_frameLayout;
    private LinearLayout error,empty,loading;
    private String titleUrl;
    private  String imageurl;
    WebSettings webSettings ;

    public static Tencent mTencent;
  //  private NewsBean newsBeans;
    private ArticleBean articleBean;
    //字体大小
    int fontSize = 1;
    private RelativeLayout relative_content;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            bindData();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        setStatusBar();
        setNeedBackGesture(true);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Config.QQAPPID, this);
        }
        /*        LinearLayout linear_bar = (LinearLayout) findViewById(R.id.details_linear_bar);
        linear_bar.setVisibility(View.VISIBLE);
        int statusHeight = getStatusBarHeight();
        android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams)linear_bar.getLayoutParams();
        params.height = statusHeight;
        linear_bar.setLayoutParams(params);*/
        initView();
        initValidata();
        initListener();

       // bindData();



    }

    @Override
    public void initView() {
        btn_back = (Button) this.findViewById(R.id.btn_back);
        btn_share = (Button) this.findViewById(R.id.btn_share);
        btn_font = (Button) this.findViewById(R.id.btn_font);
        btn_night = (Button) this.findViewById(R.id.btn_night);
        details_title = (TextView) this.findViewById(R.id.details_title);
        details_name = (TextView) this.findViewById(R.id.details_name);
        details_time = (TextView) this.findViewById(R.id.details_time);
        details_content = (WebView) this.findViewById(R.id.details_content);
        details_source = (TextView) this.findViewById(R.id.details_source);
        home_listview= (PullToRefreshListView) this.findViewById(R.id.home_listview);
        home_frameLayout = (FrameLayout) this.findViewById(R.id.prompt_framelayout);
        error = (LinearLayout) this.findViewById(R.id.error);
        loading = (LinearLayout) this.findViewById(R.id.loading);
        empty = (LinearLayout) this.findViewById(R.id.empty);
        relative_content = (RelativeLayout) this.findViewById(R.id.relative_content);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initValidata() {
        Intent intent = getIntent();
        titleUrl = intent.getStringExtra("titleUrl");
        imageurl = intent.getStringExtra("imageurl");
        Log.d("liuzhaoqq","传入数据是="+titleUrl);
        relative_content.setVisibility(View.GONE);
        home_frameLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        //设置weibview
        details_content.setWebChromeClient(new MyWebChromeClient());
        details_content.setWebViewClient(new MyWebViewClient());
         webSettings = details_content.getSettings();
        webSettings.setJavaScriptEnabled(true);//开启JavaScript
        webSettings.setDomStorageEnabled(true);//开启Dom
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码

        webSettings.setSupportZoom(true);
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



        OkhttpManager.getAsync(titleUrl, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("liuzhaoItnews","数据加载失败");
                relative_content.setVisibility(View.GONE);
                home_frameLayout.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestSuccess(String result) {
                Log.d("liuzhaoItnews","数据加载成功");

                Log.d("liuzhaoIt",result);
                final Document document= Jsoup.parse(result);
                articleBean=new ArticleDataManager().getArticleBean(document);
                Log.d("liuzhaoresult",articleBean.getTitle());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        articleBean=new ArticleDataManager().getArticleBean(document);
                        handler.sendMessage(handler.obtainMessage());
                    }
                }).start();




            }
        });

    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(new CustomOnClickListener());
        btn_share.setOnClickListener(new CustomOnClickListener());
        btn_night.setOnClickListener(new CustomOnClickListener());
        btn_font.setOnClickListener(new CustomOnClickListener());

    }

    @Override
    public void bindData() {
//        Log.d("liuzhaores",articleBean.getTitle());
        if (articleBean!=null) {
            relative_content.setVisibility(View.VISIBLE);
            home_frameLayout.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            Log.d("liuzhaoItnews", "文章内容" + articleBean.getTitle());
            details_title.setText(articleBean.getTitle());
            details_name.setText(articleBean.getRead());
            details_time.setText(articleBean.getTime());
            details_source.setText("来源:" + articleBean.getSource());
            //details_content.loadData(articleBean.getContext(),"text/html;charset=UTF-8",null);
            //details_content.loadUrl("http://iyiou.baijia.baidu.com/article/771558");
            Log.d("liuzhao_content",articleBean.getContext());
/*            String asd = "<pclass=\"text\">上周，荣耀官方宣布2月21日将在北京举行新品发布会，推出旗舰产品荣耀V9和荣耀8青春版。并公布了孙杨与吴亦凡两位代言人的预热海报，吸引了不少人的关注，而今天电科技收到了此次发布会的邀请函。</p><pclass=\"image\"><imgsrc=\"http://c.hiphotos.baidu.com/news/w%3D638/sign=7027159b7a8da9774e2f85288850f872/7af40ad162d9f2d36d9bf247a0ec8a136227cc94.jpg\"/></p><pclass=\"image\"><img src=\"http://b.hiphotos.baidu.com/news/w%3D638/sign=73191187b7096b6381195d5334328733/5243fbf2b2119313c92129186c380cd791238d3c.jpg\"/></p><pclass=\"text\">此次的新机邀请函较为简单，不过创意依旧十足，一张明信片正反面分别以“罚单”与“预警”为主题，分别强调了荣耀V9的速度以及荣耀8青春版的颜值。</p><pclass=\"image\"><imgsrc=\"http://b.hiphotos.baidu.com/news/w%3D638/sign=80b6a908ff03738dde4a0f218b1ab073/03087bf40ad162d9cb64b4b418dfa9ec8b13cd94.jpg\"/></p><pclass=\"text\">邀请函明信片一面是荣耀V9“超速通知”，内容是荣耀V9以超极速运行，因此发出超速罚单,\n" +
                    "\" +\n" +
                    "                    \"通知中给出了发布会的时间地点，颇有新意，想必此次荣耀V9肯定会在配置上有所突破，极有可能会搭载最新华为麒麟960芯片，另外超速摄影也暗示V9将采用双摄头方案。</p>";*/
            details_content.loadDataWithBaseURL(null,getNewContent(articleBean.getContext()),"text/html","UTF-8","");


            // details_content.loadDataWithBaseURL(null,asd,"text/html","UTF-8","");
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
            switch (v.getId()){
                case  R.id.btn_back:
                    ItNewsDetaActivity.this.finish();
                    break;
                case  R.id.btn_share:
                    share(articleBean);
                    Toast.makeText(ItNewsDetaActivity.this,"点击了分享按钮",Toast.LENGTH_SHORT).show();

                    break;
                case R.id.btn_night:
                    Toast.makeText(ItNewsDetaActivity.this,"点击了分享按钮",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_font:
                    fontSize++;

                    if (fontSize > 3) {
                        fontSize = 1;
                    }
                    switch (fontSize) {


                        case 4:
                            webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                            break;
                        case 1:
                            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                            break;
                        case 2:
                            webSettings.setTextSize(WebSettings.TextSize.LARGER);
                            break;
                        case 3:
                            webSettings.setTextSize(WebSettings.TextSize.LARGEST);
                            break;
                    }
                    Toast.makeText(ItNewsDetaActivity.this,"点击了分享按钮",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Util.toastMessage(ItNewsDetaActivity.this, "onCancel: ");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util.toastMessage(ItNewsDetaActivity.this, "onComplete: " + response.toString());
            Log.d("liuzhaoshare","传成功"+response);
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(ItNewsDetaActivity.this, "onError: " + e.errorMessage, "e");
        }
    };
    //分享信息到QQ图文
    public void share(ArticleBean articleBean)
    {

        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, titleUrl);
//分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        params.putString(QQShare.SHARE_TO_QQ_TITLE, articleBean.getTitle());
//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageurl);
//分享的消息摘要，最长50个字
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "");
//手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "liu新闻");
/*分享额外选项，两种类型可选（默认是不隐藏分享到QZone按钮且不自动打开分享到QZone的对话框）：
        QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
        QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮*/
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);

        mTencent.shareToQQ(this, params , qqShareListener);
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
            if(!details_content.getSettings().getLoadsImagesAutomatically()) {
                details_content.getSettings().setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("liuzhaoweb","加载的资源"+url);
        }


    }
    private String getNewContent(String htmltext){

        Document doc=Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            //element.attr("src","http:"+element.attr("data-original"));
            element.attr("width","100%").attr("height","auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);

        //特别注意：一定要添加以下代码，才可以从回调listener中获取到消息。share
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }

}
