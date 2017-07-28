package com.wanggt.freedom.codecreater.core;

public class TemplateBean {

	private int startIndex;
	private int endIndex;
	private String startSymbol;
	private String endSymbol;
	private String code;

	public String getStartSymbol() {
		return startSymbol;
	}

	public void setStartSymbol(String startSymbol) {
		this.startSymbol = startSymbol;
	}

	public String getEndSymbol() {
		return endSymbol;
	}

	public void setEndSymbol(String endSymbol) {
		this.endSymbol = endSymbol;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
