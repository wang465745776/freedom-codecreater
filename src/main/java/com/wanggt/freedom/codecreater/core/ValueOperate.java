package com.wanggt.freedom.codecreater.core;

import java.util.List;

import com.wanggt.freedom.codecreater.core.operate.Operate;

public class ValueOperate {

	/**
	 * 此方法对值进行一些操作
	 * @param operate
	 * @param value
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午6:30:32
	 * @since 1.0
	 */
	public static String dealValue(String operate, String value, List<Operate> operates) {
		String lastValue = value;

		// 当值不为空且不为""的时候才对值进行操作
		if (lastValue != null && lastValue.length() > 0) {
			String[] operateArray = operate.split(",");

			for (String oneOperate : operateArray) {
				for (Operate operate2 : operates) {
					if (operate2.getMark().equals(oneOperate)) {
						lastValue = operate2.operate(lastValue);
					}
				}
			}
		}

		return lastValue;
	}
}
