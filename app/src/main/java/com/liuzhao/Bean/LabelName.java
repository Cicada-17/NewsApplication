package com.liuzhao.Bean;

public class LabelName {
	private String m_id;
	@Override
	public String toString() {
		return "LabelName [m_id=" + m_id + ", m_name=" + m_name + "]";
	}
	private String m_name;
	public LabelName(String m_id, String m_name) {
		super();
		this.m_id = m_id;
		this.m_name = m_name;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

}
