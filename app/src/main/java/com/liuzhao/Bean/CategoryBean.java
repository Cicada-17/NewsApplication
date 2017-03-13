package com.liuzhao.Bean;

import java.io.Serializable;

public class CategoryBean implements Serializable{
	/**
	 * @return the title
	 */
	//������
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	public CategoryBean(String title, String href) {
		super();
		this.title = title;
		this.href = href;
	}
	
	private String title;//����
	private String href;//��ַ����
}
