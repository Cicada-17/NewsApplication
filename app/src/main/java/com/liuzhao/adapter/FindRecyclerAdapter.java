package com.liuzhao.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzhao.Bean.RecentNewsBean;
import com.liuzhao.R;
import com.liuzhao.utils.DateUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2月12日0012.
 */

public class FindRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private static final int TYPE_RECENT=0;
    private static final int TYPE_FOOT=1;
    private Context mContext;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader ;
    private DisplayImageOptions options;
    private Resources resources;
    private List<RecentNewsBean> recentNewsBeanList;
    public  void  setRecentNewsBeanList (List<RecentNewsBean> recentNewsBeanList){
        this.recentNewsBeanList = recentNewsBeanList;
    }
    public  FindRecyclerAdapter(Context pContext){

        this.mContext = pContext;
        this.mInflater = LayoutInflater.from(pContext);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.defaultbg).build();
        imageLoader = ImageLoader.getInstance();
        resources = mContext.getResources();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType==TYPE_RECENT){
            view=mInflater.inflate(R.layout.item_recent_news_layout,parent,false);
            view.setOnClickListener(this);
            return new RecentViewHolder(view);
        }else if(viewType==TYPE_FOOT){
            view=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            view.setOnClickListener(this);
            return new FootItemViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  RecentViewHolder){
            //近期活动
            RecentNewsBean recentNewsBean=recentNewsBeanList.get(position);
            holder.itemView.setTag(recentNewsBean);
            ((RecentViewHolder) holder).recent_item_tv_title.setText(recentNewsBean.getTitle());
            imageLoader.displayImage(recentNewsBean.getListImageUrl(), ((RecentViewHolder) holder).recent_item_img_logo);
            //活动地点
            ((RecentViewHolder) holder).recent_item_tv_location.setText(recentNewsBean.getCity());
            //活动开始时间设置
            String beginDate= DateUtil.getFormatDate(new Date(Long.parseLong(recentNewsBean.getActivityBeginTime())));
            String endDate= DateUtil.getFormatDate(new Date(Long.parseLong(recentNewsBean.getActivityEndTime())));
            if (beginDate.equals(endDate)) {
                ((RecentViewHolder) holder).recent_item_tv_timetext.setText(beginDate);
            }else {
                ((RecentViewHolder) holder).recent_item_tv_timetext.setText(beginDate+"到"+endDate);
            }
            //报名状态
            long nowTime=System.currentTimeMillis();
            long begin=Long.parseLong(recentNewsBean.getActivityBeginTime());
            long end=Long.parseLong(recentNewsBean.getActivityEndTime());
            if(nowTime<=begin){
                //报名中
                ((RecentViewHolder) holder).recent_item_tv_status.setText("报名中");
                ((RecentViewHolder) holder).recent_item_tv_status.setBackgroundResource(R.drawable.icon_activity_jin);
            }else if(nowTime>begin&&nowTime<=end){
                //活动中
                ((RecentViewHolder) holder).recent_item_tv_status.setText("活动中");
                ((RecentViewHolder) holder).recent_item_tv_status.setBackgroundResource(R.drawable.icon_activity_wei);
            }else {
                //已结束
                ((RecentViewHolder) holder).recent_item_tv_status.setText("已结束");
                ((RecentViewHolder) holder).recent_item_tv_status.setBackgroundResource(R.drawable.icon_activity_yi);
            }
        }else if (holder instanceof FootItemViewHolder){
            //上拉加载更多
        }
    }

    @Override
    public int getItemCount() {
        return recentNewsBeanList!=null?recentNewsBeanList.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1==getItemCount()){
            return TYPE_FOOT;
        }else {
            return  TYPE_RECENT;
        }    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(v,v.getTag());
        }

    }
    public static class RecentViewHolder extends  RecyclerView.ViewHolder{
        ImageView recent_item_img_logo;
        TextView recent_item_tv_title;
        TextView recent_item_tv_location;
        TextView recent_item_tv_status;
        TextView recent_item_tv_timetext;
        public RecentViewHolder(View itemView) {
            super(itemView);
            recent_item_img_logo=(ImageView)itemView.findViewById(R.id.recent_item_img_logo);
            recent_item_tv_title=(TextView)itemView.findViewById(R.id.recent_item_tv_title);
            recent_item_tv_location=(TextView)itemView.findViewById(R.id.recent_item_tv_location);
            recent_item_tv_status=(TextView)itemView.findViewById(R.id.recent_item_tv_status);
            recent_item_tv_timetext=(TextView)itemView.findViewById(R.id.recent_item_tv_timetext);
        }
    }
    private class FootItemViewHolder extends  RecyclerView.ViewHolder{

        public FootItemViewHolder(View itemView) {
                super(itemView);
            }
    }


    //添加ItemClickListener接口
    public interface OnItemClickListener{
        void onItemClick(View view, Object bean);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
