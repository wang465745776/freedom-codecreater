package com.wanggt.freedom.codecreater.core.operate;

/**
 * 获取第一个字符
 * @author freedom wang
 * @date 2017年6月23日下午3:43:10
 * @since 1.0
 */
public class FirstCharOperate implements Operate {

	@Override
	public String getMark() {
		return "f";
	}

	@Override
	public String operate(String code) {
		return code.substring(0, 1);
	}
}
