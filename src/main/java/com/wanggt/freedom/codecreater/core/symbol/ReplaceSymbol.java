package com.wanggt.freedom.codecreater.core.symbol;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.core.TemplateParser;
import com.wanggt.freedom.codecreater.core.ValueOperate;
import com.wanggt.freedom.codecreater.core.observer.HasOperate;
import com.wanggt.freedom.codecreater.core.observer.HasParamParser;
import com.wanggt.freedom.codecreater.core.operate.Operate;
import com.wanggt.freedom.codecreater.param.ParamParser;
import com.wanggt.freedom.codecreater.util.ProjectConfig;
import com.wanggt.freedom.codecreater.util.dynamic.DynamicJavaUtil;

/**
 * 替换标记
 * @author freedom wang
 * @date 2017年6月23日下午3:21:20
 * @since 1.0
 */
public class ReplaceSymbol implements Symbol, HasOperate, HasParamParser {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(ReplaceSymbol.class);

	/** 使用动态Java解析的标记 */
	private String javaMark;

	/** 操作开始标记 */
	private String operateBegin;

	/** 操作结束标记 */
	private String operateEnd;

	/** 代码生成器里注册的操作对象 */
	private List<Operate> operates;

	/** 代码生成器生成的参数解析器 */
	private ParamParser paramParser;

	/**
	 * 初始化分支标记对象，并进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午10:51:07
	 * @since 1.0
	 */
	public ReplaceSymbol() {
		init();
	}

	/**
	 * 调用此代码进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午10:52:22
	 * @since 1.0
	 */
	private void init() {
		javaMark = ProjectConfig.getProperty("replate_java_mark");
		operateBegin = ProjectConfig.getProperty("replace_operate_begin");
		operateEnd = ProjectConfig.getProperty("replace_operate_end");
	}

	@Override
	public String getSymbolStart() {
		return ProjectConfig.getProperty("replace_symbol_begin");
	}

	@Override
	public String getSymbolEnd() {
		return ProjectConfig.getProperty("replace_symbol_end");
	}

	@Override
	public String parse(String code) {
		String processCode = code;
		String returnValue = "";
		String operate = "";

		if (processCode == null || processCode.equals("")) {
			logger.error("替换语句不能为空,默认忽略此替换语句,code:{}", code);
			return returnValue.toString();
		}

		// 提取操作参数
		operate = TemplateParser.getSymbolContentStart(processCode, operateBegin, operateEnd);
		processCode = TemplateParser.removeSymbolStart(processCode, operateBegin, operateEnd);

		// 判断是否存在[replate_java_mark]标记,若存在，则表明使用动态Java解析
		boolean dynamicJavaParser = false;
		if (processCode.startsWith(javaMark)) {
			dynamicJavaParser = true;
			processCode = processCode.substring(javaMark.length());
		}

		// 解析或者查询替换语句，得到替换的值
		Object value = null;
		try {
			if (dynamicJavaParser) {
				// 动态Java解析语句
				value = new DynamicJavaUtil().dynamicOneStatement(processCode);
			} else {
				// 根据变量，查找到变量值
				value = paramParser.getParam(processCode);
			}
		} catch (Exception e) {
			logger.error("替换语句解析或查询异常,默认忽略此替换语句,code:{}", code);
		}
		if (value == null) {
			logger.warn("替换语句查询不能为空,默认忽略此替换语句,code:{}", code);
		}
		returnValue = value != null ? String.valueOf(value) : "";

		// 若存在操作参数，则对变量值进行相应的操作
		if (!returnValue.equals("") && !operate.equals("")) {
			returnValue = ValueOperate.dealValue(operate, returnValue.toString(), operates);
		}

		return returnValue;
	}

	@Override
	public void setOperates(List<Operate> operates) {
		this.operates = operates;
	}

	@Override
	public void setParamParser(ParamParser paramParser) {
		this.paramParser = paramParser;
	}
}
