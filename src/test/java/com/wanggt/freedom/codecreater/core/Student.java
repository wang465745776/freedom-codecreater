package com.wanggt.freedom.codecreater.core;

public class Student {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Student(String name) {
		super();
		this.name = name;
	}

	public Student() {
		super();
	}
	
	@Override
	public String toString() {
		return "我的名字叫："+ this.name;
	}

}
