package com.liuzhao.Bean;

public class AdHeadBean {


	//����ͼƬ

	private String imgurl;//ͼƬurl
	public AdHeadBean(String imgurl, String href, String title) {
		super();
		this.imgurl = imgurl;
		this.href = href;
		this.title = title;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String href;//��ַ����
	private String title;

}
