package com.liuzhao.common;

import java.util.HashMap;

public class Config {
	public static final String CRAWLER_URL="http://it.ithome.com/ityejie/";
	public static final String FengTV_URL="http://v.ifeng.com/news/";
	public static final String NEXT_NEWSDATA = "http://it.ithome.com/ithome/getajaxdata.aspx";
	public static final String BAIDU_URL="http://baijia.baidu.com/";

	//腾讯appkey
	public static final String QQAPPID="1105916111";

	public static final String QQAPPKEY="QSn3bdQqYlTcIdSo";

	public  static  final  String RESULTS = "results";
	public static  final  String LOCATION = "location";
	public static  final  String NOW = "now";
	public  static  final  String LAST_UPDATE="last_update";

	public static  final  String DOUYUROOMLIST = "http://open.douyucdn.cn/api/RoomApi/live/";
	public  static  final  String DOUYUROOM = "http://m.douyu.com/html5/live?roomId=";

	public static  final String WEATHERNOW = "https://api.thinkpage.cn/v3/weather/now.json?key=6vky6yutqy0t0zgb&language=zh-Hans&unit=c&location=";

	public  static  final  String WEATHERDAILY ="https://api.thinkpage.cn/v3/weather/daily.json?key=6vky6yutqy0t0zgb&language=zh-Hans&unit=c&start=0&days=5&location=";

	public static final String XINHUANEWSTV = "http://www.news.cn/video/news.htm";
	public  static  final String NEWIMAGEURL = "http://www.news.cn/video/";
	public  static HashMap<String,String> Prevarticalid =new HashMap<String,String>(){
		{
			put("首页","771266");
			put("巨头","772647");
			put("人物","770986");
			put("电商","771060");
			put("创投","770591");
			put("智能硬件","772317");
			put("互联网+","772623");
			put("P2P","762598");
			put("前沿技术","770608");
			put("游戏","769628");
		}
	};
	public  static HashMap<String,String> Labelid =new HashMap<String,String>(){
		{
			put("首页","3");
			put("巨头","100");
			put("人物","101");
			put("电商","102");
			put("创投","103");
			put("智能硬件","104");
			put("互联网+","105");
			put("P2P","106");
			put("前沿技术","107");
			put("游戏","108");
		}
	};
	public  static HashMap<String,Integer> PAGE =new HashMap<String,Integer>(){
		{
			put("苹果",12);
			put("微软",11);
			put("电商",10);
			put("通信",9);
			put("创业",8);
			put("囧科技",7);
			put("软媒动态",6);
			put("活动互动",5);
			put("人物",4);
			put("评论",3);
			put("网络",2);
			put("业界",1);
		}
	};

	
}
