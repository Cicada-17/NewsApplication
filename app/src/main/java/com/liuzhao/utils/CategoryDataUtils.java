package com.liuzhao.utils;

import com.liuzhao.Bean.CategoryBean;
import com.liuzhao.common.Config;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2月06日0006.
 */

public class CategoryDataUtils {
    public static List<CategoryBean> getCategoryBeans(){
        List<CategoryBean>  beans=new ArrayList<>();
        beans.add(new CategoryBean("首页","http://baijia.baidu.com/"));
        beans.add(new CategoryBean("巨头","http://baijia.baidu.com/?tn=listarticle&labelid=100"));
        beans.add(new CategoryBean("人物","http://baijia.baidu.com/?tn=listarticle&labelid=101"));
        beans.add(new CategoryBean("电商","http://baijia.baidu.com/?tn=listarticle&labelid=102"));
        beans.add(new CategoryBean("创投","http://baijia.baidu.com/?tn=listarticle&labelid=103"));
        beans.add(new CategoryBean("智能硬件","http://baijia.baidu.com/?tn=listarticle&labelid=104"));
        beans.add(new CategoryBean("互联网+","http://baijia.baidu.com/?tn=listarticle&labelid=105"));
        beans.add(new CategoryBean("P2P","http://baijia.baidu.com/?tn=listarticle&labelid=106"));
        beans.add(new CategoryBean("前沿技术","http://baijia.baidu.com/?tn=listarticle&labelid=107"));
        beans.add(new CategoryBean("游戏","http://baijia.baidu.com/?tn=listarticle&labelid=108"));
        return beans;
    }
    public  static  List<CategoryBean> getDouyuBeans(){
        List<CategoryBean> beans = new ArrayList<>();
        beans.add(new CategoryBean("全部", Config.DOUYUROOMLIST));
        beans.add(new CategoryBean("英雄联盟", Config.DOUYUROOMLIST+"lol"));
        beans.add(new CategoryBean("主机游戏", Config.DOUYUROOMLIST+"TVgame"));
        beans.add(new CategoryBean("王者荣耀", Config.DOUYUROOMLIST+"wzry"));
        return  beans;
    }
}
