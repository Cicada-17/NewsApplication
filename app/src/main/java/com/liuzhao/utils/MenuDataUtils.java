package com.liuzhao.utils;

import com.liuzhao.Bean.LeftItemMenu;
import com.liuzhao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2月05日0005.
 * 左侧菜单item 数据构造
 */

public class MenuDataUtils {
    public static List<LeftItemMenu> getItemMenus(){
        List<LeftItemMenu> menus=new ArrayList<LeftItemMenu>();
        menus.add(new LeftItemMenu(R.drawable.icon_zhanghaoxinxi,"账号信息"));
        menus.add(new LeftItemMenu(R.drawable.tab_find_press,"发现"));
        menus.add(new LeftItemMenu(R.drawable.icon_wodeguanzhu,"我的关注"));
        menus.add(new LeftItemMenu(R.drawable.icon_shoucang,"我的收藏"));
        menus.add(new LeftItemMenu(R.drawable.icon_yijianfankui,"意见反馈"));
        menus.add(new LeftItemMenu(R.drawable.icon_shezhi,"设置"));
        return  menus;
    }
}
