package com.liuzhao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuzhao.Bean.LeftItemMenu;
import com.liuzhao.R;

import java.util.List;

/**
 * Created by Administrator on 2月05日0005.
 */

public class LeftItemAdapter extends BaseAdapter {
    public LayoutInflater minflater;
    public List<LeftItemMenu> itemMenus;
  /*  public LeftItemAdapter(){
        minflater = LayoutInflater.from(NewApplication.getInstance());
        itemMenus = MenuDataUtils.getItemMenus();
        Log.d("liuzhao",itemMenus.get(0).getTitle());
    }*/

    @Override
    public int getCount() {
        return itemMenus!=null?itemMenus.size():0;
    }

    @Override
    public Object getItem(int position) {
        return itemMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            Item item = null;
        if (convertView == null){
            item = new Item();
            convertView = minflater.inflate(R.layout.item_left,null);
            item.img = (ImageView) convertView.findViewById(R.id.item_left_view_img);
            item.text = (TextView) convertView.findViewById(R.id.item_left_view_title);
            convertView.setTag(item);
        }else {
            item = (Item) convertView.getTag();
        }
        item.img.setImageResource(itemMenus.get(position).getLeftIcon());
        item.text.setText(itemMenus.get(position).getTitle());
        return convertView;
    }
    private  static  class Item{
        ImageView img;
        TextView text;
    }
}
