package com.wanggt.freedom.codecreater.core.symbol;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.core.ValueOperate;
import com.wanggt.freedom.codecreater.core.observer.HasOperate;
import com.wanggt.freedom.codecreater.core.observer.HasParamParser;
import com.wanggt.freedom.codecreater.core.operate.Operate;
import com.wanggt.freedom.codecreater.core.parser.TemplateBean;
import com.wanggt.freedom.codecreater.core.parser.TemplateParser;
import com.wanggt.freedom.codecreater.param.ParamParser;
import com.wanggt.freedom.codecreater.util.ProjectConfig;
import com.wanggt.freedom.codecreater.util.dynamic.DynamicJavaUtil;

/**
 * 分支标记
 * @author freedom wang
 * @date 2017年6月22日下午9:58:49
 * @since 1.0
 */
public class BranchSymbol implements Symbol, HasOperate, HasParamParser {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(BranchSymbol.class);

	/** 分支语句判断开始标记 */
	private String judgmentBegin;

	/** 分支语句判断结束标记 */
	private String judgmentEnd;

	/** 操作开始标记 */
	private String operateBegin;

	/** 操作结束标记 */
	private String operateEnd;

	/** 使用动态Java解析的标记 */
	private String javaMark;

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
	public BranchSymbol() {
		init();
	}

	/**
	 * 调用此代码进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午10:52:22
	 * @since 1.0
	 */
	private void init() {
		judgmentBegin = ProjectConfig.getProperty("branch_judgment_begin");
		judgmentEnd = ProjectConfig.getProperty("branch_judgment_end");
		operateBegin = ProjectConfig.getProperty("branch_operate_begin");
		operateEnd = ProjectConfig.getProperty("branch_operate_end");
		javaMark = ProjectConfig.getProperty("branch_java_mark");
	}

	@Override
	public String getSymbolStart() {
		return ProjectConfig.getProperty("branch_symbol_begin");
	}

	@Override
	public String getSymbolEnd() {
		return ProjectConfig.getProperty("branch_symbol_end");
	}

	@Override
	public String parse(TemplateBean templateBean) {
		String processCode = templateBean.getCode();
		String returnCode = "";

		if (processCode != null && !processCode.equals("")) {
			String operate = "";
			String param = "";

			// 提取判断参数，并在分支语句中去除判断参数
			param = TemplateParser.getSymbolContentStart(processCode, judgmentBegin, judgmentEnd);
			processCode = TemplateParser.removeSymbolStart(processCode, judgmentBegin, judgmentEnd);

			// 判断是否存在[replate_java_mark]标记,若存在，则表明使用动态Java解析
			boolean dynamicJavaParser = false;
			if (param.startsWith(javaMark)) {
				dynamicJavaParser = true;
				param = param.substring(javaMark.length());
			}

			// 提取出操作参数，并在分支语句中去除操作参数
			operate = TemplateParser.getSymbolContentStart(processCode, operateBegin, operateEnd);
			processCode = TemplateParser.removeSymbolStart(processCode, operateBegin, operateEnd);

			// 判断参数不为空，则需要对判断参数进行判断
			if (param != null && !param.equals("")) {
				boolean judgmentResult = false;// 判断结果

				// 动态执行判断参数语句
				try {
					if (dynamicJavaParser) {
						// 动态Java解析语句
						judgmentResult = (boolean) (new DynamicJavaUtil().dynamicOneStatement(param));
					} else {
						// 根据变量，查找到变量值
						judgmentResult = (boolean) paramParser.getParam(param, templateBean);
					}
				} catch (Exception e) {
					judgmentResult = false;
					logger.error("分支语句的判断参数获取失败,默认为判断参数值为false,忽略此分支语句，判断参数为：{}", param);
					logger.error("异常信息为：{}", e.getMessage());
				}

				// 根据最后的判断结果，决定是否要加入代码，如果要加入代码，需要对其中的变量进行解析
				if (judgmentResult) {
					returnCode = processCode;

					// 若存在操作参数，则对最终生成的语句进行相应的操作
					if (!operate.equals("")) {
						returnCode = ValueOperate.dealValue(operate, returnCode, operates);
					}
				}
			} else {
				logger.warn("定义了一个分支语句，但是没有提供判断参数，默认忽略此语句,分支语句为:{}", templateBean.getCode());
			}
		} else {
			logger.warn("定义了一个分支语句，但是内容是空，默认忽略此语句,code:{}", templateBean.getCode());
		}

		return returnCode.toString();
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
