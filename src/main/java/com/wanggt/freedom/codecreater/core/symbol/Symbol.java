package com.wanggt.freedom.codecreater.core.symbol;

import com.wanggt.freedom.codecreater.core.parser.TemplateBean;

/**
 * 每一种模板标记必须继承此接口
 * @author freedom wang
 * @date 2017年6月22日下午10:22:10
 * @since 1.0
 */
public interface Symbol {

	/**
	 * 返回开始标记
	 * @return
	 * @author freedom wang
	 * @date 2017年6月22日下午10:18:08
	 * @since 1.0
	 */
	String getSymbolStart();

	/**
	 * 返回结束标记
	 * @return
	 * @author freedom wang
	 * @date 2017年6月22日下午10:17:52
	 * @since 1.0
	 */
	String getSymbolEnd();

	/**
	 * 解析标记之间的模板代码
	 * @param code
	 * @return
	 * @author freedom wang
	 * @date 2017年6月22日下午10:21:36
	 * @since 1.0
	 */
	String parse(TemplateBean templateBean);
}
