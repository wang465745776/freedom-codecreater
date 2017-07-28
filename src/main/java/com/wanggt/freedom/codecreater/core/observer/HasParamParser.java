package com.wanggt.freedom.codecreater.core.observer;

import com.wanggt.freedom.codecreater.param.ParamParser;

/**
 * 实现了此接口的标记类，在代码生成器上注册时可以获取到代码生成器上创建的参数解析器
 * @author freedom wang
 * @date 2017年6月23日下午2:59:43
 * @since 1.0
 */
public interface HasParamParser {

	/**
	 * 设置代码生成器里创建的参数解析器
	 * @param paramParser
	 * @author freedom wang
	 * @date 2017年6月23日下午12:56:58
	 * @since 1.0
	 */
	void setParamParser(ParamParser paramParser);
}
