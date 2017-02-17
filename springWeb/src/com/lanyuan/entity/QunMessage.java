package com.lanyuan.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QunMessage implements Serializable{

	private String name;//名称
	private String qq;//qq号
	private String mark;//描述
	private String count;//发表次数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
}
