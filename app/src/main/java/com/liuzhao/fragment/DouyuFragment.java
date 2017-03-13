package com.liuzhao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liuzhao.Bean.CategoryBean;
import com.liuzhao.Bean.RoomItemBean;
import com.liuzhao.R;
import com.liuzhao.activity.VitamioActivity;
import com.liuzhao.adapter.RoomItemAdapter;
import com.liuzhao.biz.RoomItemManager;
import com.liuzhao.common.DefineView;
import com.liuzhao.fragment.base.BaseFragment;
import com.liuzhao.utils.OkhttpManager;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2月18日0018.
 */

public class DouyuFragment extends BaseFragment implements DefineView {
    private View mView;
    private CategoryBean categoryBean;
    private RecyclerView home_recyclerview;
    private FrameLayout home_frameLayout;
    private LinearLayout error,empty,loading;
    private GridLayoutManager gridLayoutManager ;
    private static final  String KEY = "EXTRA";
    //private List<NewsBean> newsBeens ;
    private List<RoomItemBean> roomItemBeen;

    //private HomeRecyclerAdapter homeRecyclerAdapter;
    private RoomItemAdapter roomItemAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  int lastItem;
    private  boolean isMore = true;//解决上拉加载重复问题
    private  boolean isTv = false;
    private  static int page = 0;
    private       String url;
    public static DouyuFragment newInstance(CategoryBean extra){
        Bundle bundle = new Bundle();
        //序列化的Category
        bundle.putSerializable(KEY,extra);
        DouyuFragment fragment = new DouyuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            categoryBean =(CategoryBean) bundle.getSerializable(KEY);
        }
        url = categoryBean.getHref()+"?offset=";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView==null){
            mView = inflater.inflate(R.layout.page_fragment_layout,container,false);

            initView();
            initValidata();
            initListener();
            bindData();
        }

        return mView;
    }

    @Override
    public void initView() {
        home_frameLayout = (FrameLayout) mView.findViewById(R.id.prompt_framelayout);
        error = (LinearLayout) mView.findViewById(R.id.error);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        home_recyclerview = (RecyclerView) mView.findViewById(R.id.home_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipereFreshlayout);





    }

    @Override
    public void initValidata() {
        //设置swipeRefreshLayout的进度条颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_white);
        //进度条的颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light
                ,android.R.color.holo_orange_light,android.R.color.holo_red_light);


        swipeRefreshLayout.setProgressViewOffset(false,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));

        home_recyclerview.setHasFixedSize(true);
        gridLayoutManager =  new GridLayoutManager(getContext(),2);
        home_recyclerview.setLayoutManager(gridLayoutManager);
        Log.d("liuzhaopage",categoryBean.getTitle());

        roomItemAdapter = new RoomItemAdapter(getActivity());

        //设置分割线
        //设置动画
        //数据获取
        //?page=2&pagesize=20&labelid=100&prevarticalid=772647

        OkhttpManager.getAsync(url+page, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("liuzhao","request失败");
            }

            @Override
            public void requestSuccess(String result) {
                Log.d("liuzhao","request成功");
                roomItemBeen = new RoomItemManager().getRoomItem(result);
                bindData();

            }
        });

    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                            OkhttpManager.getAsync(   url+page , new OkhttpManager.DataCallBack() {
                                @Override
                                public void requestFailure(Request request, IOException e) {
                                    Log.d("liuzhao","request失败");
                                }

                                @Override
                                public void requestSuccess(String result) {
                                    roomItemBeen = new RoomItemManager().getRoomItem(result);
                                    bindData();

                                }
                            });
                        }
                        Toast.makeText(getActivity(),"下拉刷新成功",Toast.LENGTH_SHORT).show();
                    }
                },5);
            }
        });
        //在recycler中根据监听滚动，做上拉刷新
        home_recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastItem + 1==gridLayoutManager.getItemCount()){
                    if (isMore){
                        isMore = false;
                        //加载数据
                        page++;
                        OkhttpManager.getAsync(url+page, new OkhttpManager.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {
                                Log.d("liuzhao","request失败");
                            }

                            @Override
                            public void requestSuccess(String result) {

                                List<RoomItemBean> temps = new RoomItemManager().getRoomItem(result);
                                roomItemBeen.addAll(temps);
                                roomItemAdapter.notifyDataSetChanged();
                                isMore = true;
                                Log.d("liuzhaoasd",page+"");



                            }
                        });

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = gridLayoutManager.findLastVisibleItemPosition();

            }
        });
        //添加点击事件
        roomItemAdapter.setOnItenClickListener(new RoomItemAdapter.OnItenClickListener() {

            @Override
            public void onItemClick(View view, RoomItemBean roomItemBean) {
                Log.d("douyufanh",roomItemBean.getNickname());
                Intent mintent = new Intent(getActivity(), VitamioActivity.class);
                mintent.putExtra("room_id", roomItemBean.getRoom_id());
                getActivity().startActivity(mintent);
            }


        });


    }


    @Override
    public void bindData() {
        roomItemAdapter.setRoomBean(roomItemBeen);
        home_recyclerview.setAdapter(roomItemAdapter);

    }
}
