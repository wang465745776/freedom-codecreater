package com.wanggt.freedom.codecreater.core.operate;

/**
 * 将首字符大写
 * @author freedom wang
 * @date 2017年6月23日下午3:43:10
 * @since 1.0
 */
public class FirstCharUpperCaseOperate implements Operate {

	@Override
	public String getMark() {
		return "fu";
	}

	@Override
	public String operate(String code) {
		String firstChar = code.substring(0, 1);
		return firstChar.toUpperCase() + code.substring(1);
	}
}
