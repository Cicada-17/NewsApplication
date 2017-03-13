package com.liuzhao.Bean;
//������
public class ArticleBean {
	public String title;//���±���
	public String time;//����ʱ��
	public String source;//������Դ
	public String read;//����
	@Override
	public String toString() {
		return "ArticleBean [title=" + title + ", time=" + time + ", source=" + source + ", read=" + read + ", context="
				+ context + "]";
	}
	public ArticleBean(){}
	public ArticleBean(String title, String time, String source, String read, String context) {
		super();
		this.title = title;
		this.time = time;
		this.source = source;
		this.read = read;
		this.context = context;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String context;//��������
}
