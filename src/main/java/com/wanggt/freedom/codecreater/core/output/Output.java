package com.wanggt.freedom.codecreater.core.output;

/**
 * 所有代码输出类都必须要实现此方法
 * @author freedom wang
 * @date 2017年6月25日上午11:32:33
 * @since 1.0
 */
public interface Output {

	/**
	 * 调用此方法输出代码
	 * @param code 解析之后的模板代码
	 * @author freedom wang
	 * @date 2017年6月25日上午11:32:05
	 * @since 1.0
	 */
	void output(String code);
}
