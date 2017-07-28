package com.wanggt.freedom.codecreater.core.operate;

/**
 * 删除最后一个字符
 * @author freedom wang
 * @date 2017年6月21日下午5:15:48
 * @since 1.0
 */
public class DeleteLastCharOperate implements Operate {

	@Override
	public String getMark() {
		return "dl";
	}

	@Override
	public String operate(String code) {
		return code.substring(0, code.length() - 1);
	}
}
