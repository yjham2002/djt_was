package com.appg.djTalk.stat;

public class StatModel {
	private String dept;
	private String desc;
	private int value;
	
	public StatModel(String dept, String desc, int value) {
		super();
		this.dept = dept;
		this.desc = desc;
		this.value = value;
	}
	
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
