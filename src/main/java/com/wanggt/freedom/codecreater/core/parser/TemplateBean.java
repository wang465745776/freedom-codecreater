package com.wanggt.freedom.codecreater.core.parser;

import java.util.Hashtable;

/**
 * 模板类，用于保存模板代码中待解析模板
 * 
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

	private TemplateBean preTemplate;

	private TemplateBean nextTemplate;

	private Hashtable<Object, Object> localProperties = new Hashtable<>();// 局部变量

	public Hashtable<Object, Object> getLocalProperties() {
		return localProperties;
	}

	public void setLocalProperties(Hashtable<Object, Object> localProperties) {
		this.localProperties = localProperties;
	}

	/**
	 * 添加局部变量
	 * @param properties 局部变量
	 * @author freedom wang
	 * @date 2018年4月13日下午12:36:11
	 * @since 3.0.0
	 */
	public void addLocalProperties(Hashtable<Object, Object> properties) {
		// 循环添加局部变量
		for (Object key : properties.keySet()) {
			this.localProperties.put(key, properties.get(key));
		}
	}

	/**
	 * 添加局部变量
	 * @param key 键
	 * @param value 值
	 * @author freedom wang
	 * @date 2018年4月13日下午2:21:08
	 * @since 3.0.0
	 */
	public void addLocalProperties(Object key, Object value) {
		this.localProperties.put(key, value);
	}

	public TemplateBean getPreTemplate() {
		return preTemplate;
	}

	public void setPreTemplate(TemplateBean preTemplate) {
		this.preTemplate = preTemplate;
	}

	public TemplateBean getNextTemplate() {
		return nextTemplate;
	}

	public void setNextTemplate(TemplateBean nextTemplate) {
		this.nextTemplate = nextTemplate;
	}

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

	/**
	 * 获取待解析模板代码
	 * 
	 * @return
	 * @author freedom wang
	 * @date 2018年4月13日上午9:56:37
	 * @since 1.0
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
