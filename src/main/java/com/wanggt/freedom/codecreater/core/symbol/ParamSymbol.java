package com.wanggt.freedom.codecreater.core.symbol;

import java.util.Hashtable;

import com.wanggt.freedom.codecreater.core.parser.TemplateBean;

/**
 * 实现此接口的标记表示为参数标记，参数标记不对模板代码做操作，只用于设置局部变量
 * @author freedom wang
 * @date 2018年4月13日上午11:01:39
 * @since 3.0.0
 */
public interface ParamSymbol extends Symbol{
	
	/**
	 * 解析局部变量
	 * @param templateBean 局部变量模板
	 * @return
	 * @author freedom wang
	 * @date 2018年4月13日上午11:40:30
	 * @since 3.0.0
	 */
	Hashtable<Object, Object> parseParam(TemplateBean templateBean);
}
