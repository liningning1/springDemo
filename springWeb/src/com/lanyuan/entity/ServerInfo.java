package com.lanyuan.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class ServerInfo implements Serializable {

	private Integer id;
	private String cpuUsage;//cpu使用率
	private String setCpuUsage;//预设cpu使用率
	private String jvmUsage;//当前 jvm 使用率
	private String setJvmUsage;//预设jvm 使用率
	private String ramUsage;//当前 ram 使用率
	private String setRamUsage;//预设ram使用率
	private Date operTime;//发送时间
	private String email;//发送的邮件
	private String stringTime;//时间字符串
	private String mark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public String getSetCpuUsage() {
		return setCpuUsage;
	}
	public void setSetCpuUsage(String setCpuUsage) {
		this.setCpuUsage = setCpuUsage;
	}
	public String getJvmUsage() {
		return jvmUsage;
	}
	public void setJvmUsage(String jvmUsage) {
		this.jvmUsage = jvmUsage;
	}
	public String getSetJvmUsage() {
		return setJvmUsage;
	}
	public void setSetJvmUsage(String setJvmUsage) {
		this.setJvmUsage = setJvmUsage;
	}
	public String getRamUsage() {
		return ramUsage;
	}
	public void setRamUsage(String ramUsage) {
		this.ramUsage = ramUsage;
	}
	public String getSetRamUsage() {
		return setRamUsage;
	}
	public void setSetRamUsage(String setRamUsage) {
		this.setRamUsage = setRamUsage;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStringTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(operTime!=null)
		{
			stringTime = sdf.format(operTime);
		}
		return stringTime;
	}
	public void setStringTime(String stringTime) {
		this.stringTime = stringTime;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
}
