package com.liuzhao.Bean;
//���ż����

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
public class NewsBean  implements Parcelable {

	private String ID;//
	private String m_create_time;//创建时间
	private String m_summary;//简介

	protected NewsBean(Parcel in) {
		ID = in.readString();
		m_create_time = in.readString();
		m_summary = in.readString();
		m_title = in.readString();
		hotcount = in.readString();
		m_image_url = in.readString();
		m_display_url = in.readString();
		m_writer_name = in.readString();
		m_writer_url = in.readString();
		m_writer_account_type = in.readString();
		m_attr_exclusive = in.readString();
		m_attr_first_pub = in.readString();
	}

	public static final Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
		@Override
		public NewsBean createFromParcel(Parcel in) {
			return new NewsBean(in);
		}

		@Override
		public NewsBean[] newArray(int size) {
			return new NewsBean[size];
		}
	};

	@Override
	public String toString() {
		return "NewsBean [ID=" + ID + ", m_create_time=" + m_create_time + ", m_summary=" + m_summary + ", m_title="
				+ m_title + ", hotcount=" + hotcount + ", m_label_names=" + m_label_names + ", m_image_url="
				+ m_image_url + ", m_display_url=" + m_display_url + ", m_writer_name=" + m_writer_name
				+ ", m_writer_url=" + m_writer_url + ", m_writer_account_type=" + m_writer_account_type
				+ ", m_attr_exclusive=" + m_attr_exclusive + ", m_attr_first_pub=" + m_attr_first_pub + "]";
	}
	private String m_title;//����
	 private String hotcount;
	 private List<LabelName> m_label_names;
	 private String m_image_url;
	 private String m_display_url;//url
	public  NewsBean(){}
	 public NewsBean(String iD, String m_create_time, String m_summary, String m_title, String hotcount,
			List<LabelName> m_label_names, String m_image_url, String m_display_url, String m_writer_name,
			String m_writer_url, String m_writer_account_type, String m_attr_exclusive, String m_attr_first_pub) {
		super();
		ID = iD;
		this.m_create_time = m_create_time;
		this.m_summary = m_summary;
		this.m_title = m_title;
		this.hotcount = hotcount;
		this.m_label_names = m_label_names;
		this.m_image_url = m_image_url;
		this.m_display_url = m_display_url;
		this.m_writer_name = m_writer_name;
		this.m_writer_url = m_writer_url;
		this.m_writer_account_type = m_writer_account_type;
		this.m_attr_exclusive = m_attr_exclusive;
		this.m_attr_first_pub = m_attr_first_pub;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getM_create_time() {
		return m_create_time;
	}
	public void setM_create_time(String m_create_time) {
		this.m_create_time = m_create_time;
	}
	public String getM_summary() {
		return m_summary;
	}
	public void setM_summary(String m_summary) {
		this.m_summary = m_summary;
	}
	public String getM_title() {
		return m_title;
	}
	public void setM_title(String m_title) {
		this.m_title = m_title;
	}
	public String getHotcount() {
		return hotcount;
	}
	public void setHotcount(String hotcount) {
		this.hotcount = hotcount;
	}
	public List<LabelName> getM_label_names() {
		return m_label_names;
	}
	public void setM_label_names(List<LabelName> m_label_names) {
		this.m_label_names = m_label_names;
	}
	public String getM_image_url() {
		return m_image_url;
	}
	public void setM_image_url(String m_image_url) {
		this.m_image_url = m_image_url;
	}
	public String getM_display_url() {
		return m_display_url;
	}
	public void setM_display_url(String m_display_url) {
		this.m_display_url = m_display_url;
	}
	public String getM_writer_name() {
		return m_writer_name;
	}
	public void setM_writer_name(String m_writer_name) {
		this.m_writer_name = m_writer_name;
	}
	public String getM_writer_url() {
		return m_writer_url;
	}
	public void setM_writer_url(String m_writer_url) {
		this.m_writer_url = m_writer_url;
	}
	public String getM_writer_account_type() {
		return m_writer_account_type;
	}
	public void setM_writer_account_type(String m_writer_account_type) {
		this.m_writer_account_type = m_writer_account_type;
	}
	public String getM_attr_exclusive() {
		return m_attr_exclusive;
	}
	public void setM_attr_exclusive(String m_attr_exclusive) {
		this.m_attr_exclusive = m_attr_exclusive;
	}
	public String getM_attr_first_pub() {
		return m_attr_first_pub;
	}
	public void setM_attr_first_pub(String m_attr_first_pub) {
		this.m_attr_first_pub = m_attr_first_pub;
	}
	private String m_writer_name;
	 private String m_writer_url;
	 private String m_writer_account_type;
	 private String m_attr_exclusive;
	 private String m_attr_first_pub;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(ID);
		dest.writeString(m_create_time);
		dest.writeString(m_summary);
		dest.writeString(m_title);
		dest.writeString(hotcount);
		dest.writeString(m_image_url);
		dest.writeString(m_display_url);
		dest.writeString(m_writer_name);
		dest.writeString(m_writer_url);
		dest.writeString(m_writer_account_type);
		dest.writeString(m_attr_exclusive);
		dest.writeString(m_attr_first_pub);
	}
}
