package com.liuzhao.Bean;

public class XinhuaNewsBean extends  ImageDataBean{
	private String title;
	private String href;
	private String imageurl;
	private String time;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public XinhuaNewsBean(String title, String href, String imageurl, String time) {
		super();
		this.title = title;
		this.href = href;
		this.imageurl = imageurl;
		this.time = time;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
