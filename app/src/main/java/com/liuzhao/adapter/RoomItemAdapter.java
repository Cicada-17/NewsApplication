package com.liuzhao.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzhao.Bean.RoomItemBean;
import com.liuzhao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2月19日0019.
 */

public class RoomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static  final int TYPE_ITEM = 0;
    private  static  final  int TYPE_FOOT =1;
    private Context context;
    private LayoutInflater mInflater;
    private List<RoomItemBean> roomItemBeens;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private Resources resources;
    private   String mask ;
    //新闻列表数据
    public void setRoomBean(List<RoomItemBean> newsBeens) {
        this.roomItemBeens = newsBeens;
    }

    public RoomItemAdapter(Context context){

        this.context = context;

        this.mInflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.defaultbg).build();

        mImageLoader = ImageLoader.getInstance();
        resources = context.getResources();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){

                View itemView = mInflater.inflate(R.layout.item_room_item_layout,parent,false);
                itemView.setOnClickListener(this);
                RoomItemAdapter.ItemViewHolder itemViewHolder = new RoomItemAdapter.ItemViewHolder(itemView);
                return  itemViewHolder;

            }else if (viewType == TYPE_FOOT){
            View footitemView = mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
            footitemView.setOnClickListener(this);
            RoomItemAdapter.FootItemViewHolder footViewHolder = new RoomItemAdapter.FootItemViewHolder(footitemView);
            return footViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RoomItemAdapter.ItemViewHolder){
            //普通
            RoomItemBean roomItemBean1 = roomItemBeens.get(position);
            holder.itemView.setTag(roomItemBean1);
            ((ItemViewHolder) holder).nickname.setText(roomItemBean1.getNickname());
            ((ItemViewHolder) holder).online.setText(roomItemBean1.getOnline());
            ((ItemViewHolder) holder).room_name.setText(roomItemBean1.getRoom_name());
            mImageLoader.displayImage(roomItemBean1.getRoom_src(),((ItemViewHolder) holder).iv_room);
            //根据标签名设置颜色
        }else if (holder instanceof RoomItemAdapter.FootItemViewHolder){
            //上拉加载布局

        }
    }

    @Override
    public int getItemCount() {

        return roomItemBeens ==null ? 0:roomItemBeens.size()+1;
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
            onItenClickListener.onItemClick(v,(RoomItemBean) v.getTag());
        }
    }

    //下面是自定义的viewholder

    private class  ItemViewHolder extends  RecyclerView.ViewHolder{
        private ImageView iv_room;
        private TextView nickname;
        private  TextView room_name;
        private  TextView online;

        public ItemViewHolder(View itemView) {
            super(itemView);
           iv_room = (ImageView) itemView.findViewById(R.id.iv_room);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            room_name = (TextView) itemView.findViewById(R.id.room_name);
            online = (TextView) itemView.findViewById(R.id.online);

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
        void onItemClick(View view,RoomItemBean roomItemBean);
    }
    private RoomItemAdapter.OnItenClickListener onItenClickListener;
    public  void setOnItenClickListener(RoomItemAdapter.OnItenClickListener onItenClickListener){
        this.onItenClickListener = onItenClickListener;
    }
}
