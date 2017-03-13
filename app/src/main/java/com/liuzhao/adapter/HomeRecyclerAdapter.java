package com.liuzhao.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzhao.Bean.NewsBean;
import com.liuzhao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2月07日0007.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private static  final int TYPE_ITEM = 0;
    private  static  final  int TYPE_FOOT =1;
    private Context context;
    private LayoutInflater mInflater;
    private List<NewsBean> newsBeens;
    private int type = 0;//0是普通 1是视频列表
    private ImageLoader mImageLoader;
    private  DisplayImageOptions options;
    private String[] masks;
    private  int[] masks_colors;
    private Resources resources;
    private   String mask ;
    //新闻列表数据
    public void setNewsBeens(List<NewsBean> newsBeens) {
        this.newsBeens = newsBeens;
    }

    public HomeRecyclerAdapter(Context context,int type){
        this.type = type;
        this.context = context;

        this.mInflater = LayoutInflater.from(context);
         options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.defaultbg).build();

        mImageLoader = ImageLoader.getInstance();
        masks = new String[]{"业界","视频","网络","评论","人物","活动互动","软媒动态","囧科技","创业","通信","电商","微软","苹果"};
        masks_colors= new int[]{R.color.mask_tags_1,R.color.mask_tags_2
                ,R.color.mask_tags_3,R.color.mask_tags_4,R.color.mask_tags_5
                ,R.color.mask_tags_6,R.color.mask_tags_7,R.color.mask_tags_8
                ,R.color.mask_tags_9,R.color.mask_tags_10,R.color.mask_tags_11
                ,R.color.mask_tags_12};
        resources = context.getResources();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
        if (type == 0){
            //普通
            View itemView = mInflater.inflate(R.layout.item_home_news_layout,parent,false);
            itemView.setOnClickListener(this);
            ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
            return  itemViewHolder;
        }else if (type == 1){
            //视频
            View tvitemView = mInflater.inflate(R.layout.item_tv_news_layout,parent,false);
            tvitemView.setOnClickListener(this);
            TvViewHolder tvViewHolder = new TvViewHolder(tvitemView);
            return  tvViewHolder;
        }}else if (viewType == TYPE_FOOT){
            View footitemView = mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            footitemView.setOnClickListener(this);
            FootItemViewHolder footViewHolder = new FootItemViewHolder(footitemView);
            return footViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder){
            //普通
            NewsBean newsBean = newsBeens.get(position);
            holder.itemView.setTag(newsBean);
            ((ItemViewHolder) holder).item_news_tv_title.setText(newsBean.getM_title());
            ((ItemViewHolder) holder).item_news_tv_time.setText(newsBean.getM_create_time());
            mImageLoader.displayImage(newsBean.getM_image_url(),((ItemViewHolder) holder).item_news_iv_news);
            ((ItemViewHolder) holder).item_news_tv_ci.setText(newsBean.getM_label_names().toString());
            //根据标签名设置颜色
            int index = 0;

            for (int i = 0; i < masks.length; i++) {
                if (masks[i].equals(masks)) {
                    index += 1;
                    break;
                }
            }
            ((ItemViewHolder) holder).item_news_tv_arrow.setBackgroundColor(context.getResources().getColor(masks_colors[index]));
        }else if (holder instanceof TvViewHolder){
            NewsBean newsBean = newsBeens.get(position);
            holder.itemView.setTag(newsBean);
            Log.d("liuzhao homerecy",newsBean.getM_display_url());
            mImageLoader.displayImage(newsBean.getM_image_url(),((TvViewHolder) holder).tv_img,options);
            ((TvViewHolder) holder).tv_title.setText(newsBean.getM_title());
        }else if (holder instanceof FootItemViewHolder){
            //上拉加载布局

        }
    }

    @Override
    public int getItemCount() {

        return newsBeens ==null ? 0:newsBeens.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position +1 == getItemCount()){
            return  TYPE_FOOT;
        }else {
            return  TYPE_ITEM;
        }

    }

    @Override
    public void onClick(View v) {
        if (onItenClickListener!=null){
            onItenClickListener.onItemClick(v,(NewsBean) v.getTag());
        }
    }

    //下面是自定义的viewholder
    //普通item
    private class  ItemViewHolder extends  RecyclerView.ViewHolder{
        private ImageView item_news_iv_news;
        private TextView item_news_tv_title;
        private  TextView item_news_tv_ci;
        private  TextView item_news_tv_time;
        private  TextView item_news_tv_arrow;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_news_iv_news = (ImageView) itemView.findViewById(R.id.item_news_iv_news);
            item_news_tv_title = (TextView) itemView.findViewById(R.id.item_news_tv_title);
            item_news_tv_ci = (TextView) itemView.findViewById(R.id.item_news_tv_ci);
            item_news_tv_time = (TextView) itemView.findViewById(R.id.item_news_tv_time);
            item_news_tv_arrow = (TextView) itemView.findViewById(R.id.item_news_tv_arrow);
        }
    }
    //视频列表viewholder
    private  class TvViewHolder extends  RecyclerView.ViewHolder{
        private ImageView tv_img;
        private ImageView tv_icon;
        private TextView tv_title;
        private TextView tv_mask;

        public TvViewHolder(View itemView) {
            super(itemView);
            tv_img = (ImageView) itemView.findViewById(R.id.tv_img);

            tv_icon = (ImageView) itemView.findViewById(R.id.tv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_mask = (TextView) itemView.findViewById(R.id.tv_mask);

        }
    }
    //上拉加载布局
    private  class  FootItemViewHolder extends RecyclerView.ViewHolder {
        public FootItemViewHolder(View itemView) {
            super(itemView);
        }
    }
    //添加itemclicklistener接口
    public   interface OnItenClickListener{
        void onItemClick(View view,NewsBean newsBean);
    }
    private    OnItenClickListener onItenClickListener;
    public  void setOnItenClickListener(OnItenClickListener onItenClickListener){
        this.onItenClickListener = onItenClickListener;
    }

}

