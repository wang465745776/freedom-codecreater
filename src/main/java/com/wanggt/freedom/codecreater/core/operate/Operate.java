package com.wanggt.freedom.codecreater.core.operate;

/**
 * 所有的操作都必须要实现此接口
 * @author freedom wang
 * @date 2017年6月21日下午5:14:19
 * @since 1.0
 */
public interface Operate {

	/**
	 * 返回操作的标记
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午5:12:17
	 * @since 1.0
	 */
	public String getMark();

	/**
	 * 对代码进行操作
	 * @param code
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日下午5:13:04
	 * @since 1.0
	 */
	public String operate(String code);
}
