package com.liuzhao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Liu.lwidgetutil.AutoGallery;
import com.Liu.lwidgetutil.FlowIndicator;
import com.liuzhao.Bean.FindAdBean;
import com.liuzhao.Bean.RecentNewsBean;
import com.liuzhao.R;
import com.liuzhao.adapter.FindRecyclerAdapter;
import com.liuzhao.biz.RecentDataManager;
import com.liuzhao.common.DefineView;
import com.liuzhao.utils.OkhttpManager;
import com.liuzhao.widget.DragLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2月11日0011.
 */

public class FindActivity extends  BaseActivity implements DefineView, View.OnClickListener {
    //底部tab
    private TextView one;
    private  TextView two;
    private TextView three;
    private  TextView four;

    private Context context;

    private DragLayout drag_layout;
    private LayoutInflater minflater;
    private AutoGallery headline_image_gallery;
    private FlowIndicator headline_circle_indicator;
    private int gallerySelectedPositon = 0;//Gallerys索引

    private int circleSelectedPosition = 0; // 默认指示器的圆圈的位置为第一

    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    private List<FindAdBean> findAdBeens; //顶部广告数据

    private static final String KEY="EXTRA";
    private RecyclerView home_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private FrameLayout home_framelayout;
    private LinearLayout loading,empty,error;
    private FindRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastItem;
    private boolean isMore=true; //解决上拉重复加载的bug
    //进行分页效果--主要用于近期活动列表
    private int page=1;      //页码 默认为第一页
    private int pageSize=50;   //每页的item数量
    private List<RecentNewsBean> recentNewsBeans;  //近期活动列表数据
    Activity as ;
    String requestUrl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        minflater= LayoutInflater.from(this);
        context = this;
       as = this;
        setStatusBar();

        initView();
        initValidata();
        initListener();
        bindData();

    }

    @Override
    public void initView() {

/*        headline_image_gallery = (AutoGallery) this.findViewById(R.id.headline_image_gallery);
        headline_circle_indicator= (FlowIndicator) this.findViewById(R.id.headline_circle_indicator);*/

        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        setTabState(two, R.drawable.tab_two_fill, ContextCompat.getColor(context,R.color.mask_tags_8));

        home_framelayout=(FrameLayout)findViewById(R.id.prompt_framelayout);
        loading=(LinearLayout)findViewById(R.id.loading);
        empty=(LinearLayout)findViewById(R.id.empty);
        error=(LinearLayout)findViewById(R.id.error);
        home_recyclerview=(RecyclerView)findViewById(R.id.home_recyclerview);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipereFreshlayout);


    }

    @Override
    public void initValidata() {
        //设置swipeRefreshLayout的进度条的背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_white);
        //进度条的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        //设置进度条的偏移量
        swipeRefreshLayout.setProgressViewOffset(false,0,50);


        home_recyclerview.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        home_recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new FindRecyclerAdapter(this);
         requestUrl =  requestUrl="http://chuang.36kr.com/api/actapply?page="+page+"&pageSize="+pageSize;
        OkhttpManager.getAsync(requestUrl, new OkhttpManager.DataCallBack() {


            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("zttjiangqq","requestFailure...");
            }

            @Override
            public void requestSuccess(String result) {
                Log.d("zttjiangqq","requestSuccess...");
                    recentNewsBeans = RecentDataManager.getRecentDatas(result);
                    adapter.setRecentNewsBeanList(recentNewsBeans);
                    home_recyclerview.setAdapter(adapter);


            }
        });

        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaultbg).cacheInMemory(true)
                    .cacheOnDisk(true).build();
      /*  OkhttpManager.getAsync("https://z.36kr.com/api/p/sc/images?type=1", new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("liuzhaofind","顶部广告数据加载时失败");
            }

            @Override
            public void requestSuccess(String result) {
                Log.d("liuzhaofind","顶部广告数据加载时成功");
                Gson gson=new Gson();
                findAdBeens= gson.fromJson(result, FindAdData.class).getData();
                bindTopData();

            }
        });*/

    }

    @Override
    public void initListener() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                            OkhttpManager.getAsync(requestUrl, new OkhttpManager.DataCallBack() {


                                @Override
                                public void requestFailure(Request request, IOException e) {
                                    Log.d("zttjiangqq","requestFailure...");
                                }

                                @Override
                                public void requestSuccess(String result) {
                                    Log.d("zttjiangqq","requestSuccess...");
                                    recentNewsBeans = RecentDataManager.getRecentDatas(result);
                                    adapter.setRecentNewsBeanList(recentNewsBeans);
                                    home_recyclerview.setAdapter(adapter);


                                }
                            });
                        }

                        Toast.makeText(getApplicationContext(),"下拉刷新成功",Toast.LENGTH_SHORT).show();
                    }
                },50);
            }
        });
        home_recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem=linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        adapter.setOnItemClickListener(new FindRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object bean) {
               // Toast.makeText(getApplicationContext(),"点击了"+((RecentNewsBean)bean).toString(),Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(as,WebActivity.class);
                mIntent.putExtra("Url",((RecentNewsBean)bean).getLink());
                as.startActivity(mIntent);

            }
        });


    }

    @Override
    public void bindData() {
        adapter.setRecentNewsBeanList(recentNewsBeans);
        home_recyclerview.setAdapter(adapter);


    }
    public DragLayout getDrag_layout() {
        return drag_layout;

    }
    private  void  bindTopData(){
       /*   int topSize = findAdBeens.size();
      //设置指示器
        headline_circle_indicator.setCount(topSize);
        headline_circle_indicator.setSeletion(circleSelectedPosition);
        //设置 Gallery
        headline_image_gallery.setLength(topSize);
        Log.d("size", topSize+"");
        gallerySelectedPositon = topSize*50+circleSelectedPosition;
        headline_image_gallery.setSelection(gallerySelectedPositon);
        headline_image_gallery.setDelayMillis(4000);
        headline_image_gallery.start();

        headline_image_gallery.setAdapter(new FindActivity.GalleryAdapter());
        headline_image_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                circleSelectedPosition=position;
                gallerySelectedPositon=circleSelectedPosition%findAdBeens.size();
                headline_circle_indicator.setSeletion(gallerySelectedPositon);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
        }
    }
    class GalleryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return findAdBeens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FindActivity.Holder holder = null;
            if (convertView == null){
                holder = new FindActivity.Holder();
                convertView = minflater.inflate(R.layout.item_gallery_layout,null);
                holder.item_head_gallery_img = (ImageView) convertView.findViewById(R.id.item_head_gallery_img);
                convertView.setTag(holder);
            }else {
                holder = (FindActivity.Holder) convertView.getTag();
            }
            //显示数据
            //  Picasso.with(mView.getContext()).load(adHeadBeens.get(position%adHeadBeens.size()).getImgurl()).into(holder.item_head_gallery_img);
            mImageLoader.displayImage(findAdBeens.get(position%findAdBeens.size()).getImg_url(), holder.item_head_gallery_img,options);
            return convertView;
        }
    }
    private static  class Holder{
        ImageView item_head_gallery_img;
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

}
