package com.wanggt.freedom.codecreater.core.operate;

/**
 * 大写操作，将需要处理的字符全部进行大写处理
 * @author freedom wang
 * @date 2017年6月21日下午5:15:48
 * @since 1.0
 */
public class UpperCaseOperate implements Operate {

	@Override
	public String getMark() {
		return "u";
	}

	@Override
	public String operate(String code) {
		return code.toUpperCase();
	}
}
