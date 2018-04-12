package com.wanggt.freedom.codecreater.core.parser;

/**
 * 模板类，用于保存模板代码中待解析模板
 * @author freedom wang
 * @date 2018年4月13日上午12:04:36
 * @version 1.0
 */
public class TemplateBean {

	private int startIndex;// 开始位置
	private int endIndex;// 结束位置
	private String startSymbol; // 开始标记
	private String endSymbol;// 结束标记
	private String code; // 开始标记和结束标记之间的内容

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
