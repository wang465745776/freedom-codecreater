package com.wanggt.freedom.codecreater.core.symbol;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.core.parser.TemplateBean;
import com.wanggt.freedom.codecreater.exception.ErrorFormatException;
import com.wanggt.freedom.codecreater.util.ProjectConfig;

/**
 * 局部变量标记。
 * 
 * 标记语法是：#set# name=freedom #set#
 * 
 * @author freedom wang
 * @date 2018年4月13日上午11:08:44
 * @since 3.0.0
 */
public class SetSymbol implements ParamSymbol {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(SetSymbol.class);

	@Override
	public String getSymbolStart() {
		return ProjectConfig.getProperty("set_symbol_begin");
	}

	@Override
	public String getSymbolEnd() {
		return ProjectConfig.getProperty("set_symbol_end");
	}

	@Override
	public String parse(TemplateBean templateBean) {
		// 如果要解析的字符串为空，则不进行解析，返回一个空字符串
		if (templateBean.getCode() == null || "".equals(templateBean.getCode())) {
			return "";
		}

		// 去除前後空格
		String parseCode = templateBean.getCode();
		parseCode = parseCode.trim();

		// 判断格式是否正确
		if (parseCode.indexOf("=") == -1 || parseCode.indexOf("=") == 0
				|| parseCode.indexOf("=") == parseCode.length() - 1) {
			String errorMessage = "设置局部变量模板错误,模板代码为:" + templateBean.getCode();
			logger.error(errorMessage);
			throw new ErrorFormatException(errorMessage);
		}

		return parseCode;
	}

	@Override
	public Hashtable<Object, Object> parseParam(TemplateBean templateBean) {
		String parseCode = parse(templateBean);

		String[] keyValue = parseCode.split("=");

		Hashtable<Object, Object> properties = new Hashtable<>();
		properties.put(keyValue[0], keyValue[1]);
		return properties;
	}
}
