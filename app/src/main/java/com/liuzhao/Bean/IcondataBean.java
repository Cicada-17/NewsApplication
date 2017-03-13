package com.liuzhao.Bean;

public class IcondataBean {
	private String status;
	private String icon_url;
	private String icon_width;
	private String icon_height;
	public IcondataBean(String status, String icon_url, String icon_width, String icon_height) {
		super();
		this.status = status;
		this.icon_url = icon_url;
		this.icon_width = icon_width;
		this.icon_height = icon_height;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getIcon_width() {
		return icon_width;
	}
	public void setIcon_width(String icon_width) {
		this.icon_width = icon_width;
	}
	public String getIcon_height() {
		return icon_height;
	}
	public void setIcon_height(String icon_height) {
		this.icon_height = icon_height;
	}

}
