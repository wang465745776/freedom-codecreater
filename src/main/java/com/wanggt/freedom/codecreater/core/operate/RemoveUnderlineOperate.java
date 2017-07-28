package com.wanggt.freedom.codecreater.core.operate;

/**
 * 移除下划线,符号为：r_
 * @author freedom wang
 * @date 2017年6月21日下午5:15:48
 * @since 1.0
 */
public class RemoveUnderlineOperate implements Operate {

	@Override
	public String getMark() {
		return "r_";
	}

	@Override
	public String operate(String code) {
		return code.replace("_", "");
	}
}
