package com.wanggt.freedom.codecreater.core.operate;

/**
 * 将所有字符小写
 * @author freedom wang
 * @date 2017年6月21日下午5:15:48
 * @since 1.0
 */
public class LowerCaseOperate implements Operate {

	@Override
	public String getMark() {
		return "l";
	}

	@Override
	public String operate(String code) {
		return code.toLowerCase();
	}
}
