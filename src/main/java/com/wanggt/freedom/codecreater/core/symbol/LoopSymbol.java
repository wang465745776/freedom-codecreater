package com.wanggt.freedom.codecreater.core.symbol;

import java.lang.reflect.Array;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.core.TemplateBean;
import com.wanggt.freedom.codecreater.core.TemplateParser;
import com.wanggt.freedom.codecreater.core.ValueOperate;
import com.wanggt.freedom.codecreater.core.observer.HasOperate;
import com.wanggt.freedom.codecreater.core.observer.HasParamParser;
import com.wanggt.freedom.codecreater.core.operate.Operate;
import com.wanggt.freedom.codecreater.param.ParamParser;
import com.wanggt.freedom.codecreater.util.ProjectConfig;

/**
 * 循环标记
 * @author freedom wang
 * @date 2017年6月23日上午11:30:07
 * @since 1.0
 */
public class LoopSymbol implements Symbol, HasParamParser, HasOperate {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(LoopSymbol.class);

	/** 循环参数开始标记 */
	private String paramBegin = "";

	/** 循环参数结束标记 */
	private String paramEnd = "";

	/** 循环语句操作开始标记 */
	private String operateBegin = "";

	/** 循环语句操作结束标记 */
	private String operateEnd = "";

	/** 循环字段开始标记 */
	private String arrayFieldBegin = "";

	/** 循环字段结束标记 */
	private String arrayFieldEnd = "";

	/** 循环字段操作开始标记 */
	private String fieldOperateBegin = "";

	/** 循环字段操作结束标记 */
	private String fieldOperateEnd = "";

	/** 参数解析器 */
	private ParamParser paramParser;

	/** 代码生成器上注册的操作对象 */
	private List<Operate> operates;

	/**
	 * 初始化分支标记对象，并进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午10:51:07
	 * @since 1.0
	 */
	public LoopSymbol() {
		init();
	}

	/**
	 * 调用此代码进行初始化
	 * @author freedom wang
	 * @date 2017年6月23日上午10:52:22
	 * @since 1.0
	 */
	private void init() {
		paramBegin = ProjectConfig.getProperty("loop_param_begin");
		paramEnd = ProjectConfig.getProperty("loop_param_end");
		operateBegin = ProjectConfig.getProperty("loop_operate_begin");
		operateEnd = ProjectConfig.getProperty("loop_operate_end");
		arrayFieldBegin = ProjectConfig.getProperty("loop_array_field_begin");
		arrayFieldEnd = ProjectConfig.getProperty("loop_array_field_end");
		fieldOperateBegin = ProjectConfig.getProperty("loop_array_field_operate_begin");
		fieldOperateEnd = ProjectConfig.getProperty("loop_array_field_operate_end");
	}

	@Override
	public String getSymbolStart() {
		return ProjectConfig.getProperty("loop_symbol_begin");
	}

	@Override
	public String getSymbolEnd() {
		return ProjectConfig.getProperty("loop_symbol_end");
	}

	@Override
	public String parse(String code) {
		// 要操作的代码
		String processCode = code;
		// 定义一个变量用于保存最终生成的语句
		StringBuffer returnValue = new StringBuffer();

		// 如果模板代码不为空，则进行处理
		if (processCode != null && !processCode.equals("")) {
			String operate = "";
			String param = "";

			// 提取循环参数
			param = TemplateParser.getSymbolContentStart(processCode, paramBegin, paramEnd);
			processCode = TemplateParser.removeSymbolStart(processCode, paramBegin, paramEnd);

			// 提取操作参数
			operate = TemplateParser.getSymbolContentStart(processCode, operateBegin, operateEnd);
			processCode = TemplateParser.removeSymbolStart(processCode, operateBegin, operateEnd);

			// 判断循环参数是否存在
			if (param != null && !param.equals("")) {
				// 取得循环参数
				Object paramValue = null;
				try {
					paramValue = paramParser.getParam(param);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("获取循环参数失败,循环语句为：{},循环参数为：{}", code, param);
					logger.error("错误信息为：{}", e.getMessage());
				}

				// 如果循环参数的值为数组，则进行后续操作，否则提示错误
				if (paramValue != null && paramValue.getClass().isArray()) {
					// 定义一个变量用于临时保存循环语句，所有的操作都将在这个临时变量中进行
					StringBuffer loopCode = new StringBuffer(processCode);

					// 循环每一列
					for (int i = 0; i < Array.getLength(paramValue); i++) {

						// 判断循环语句中是否存在参数，若存在，则解析出参数的值
						while (TemplateParser.hasTemplateCode(loopCode.toString(), arrayFieldBegin, arrayFieldEnd)) {
							// 获取第一个模板信息
							TemplateBean templateBean = TemplateParser.getFirstTemplateBean(loopCode.toString(), 0,
									arrayFieldBegin, arrayFieldEnd);

							String fieldOperate = "";
							String arrayField = templateBean.getCode();// 获取数组中的对象的属性

							// 提取出数组字段的操作参数
							fieldOperate = TemplateParser.getSymbolContentStart(arrayField, fieldOperateBegin,
									fieldOperateEnd);
							arrayField = TemplateParser.removeSymbolStart(arrayField, fieldOperateBegin, fieldOperateEnd);

							// 取得数组指定索引对象的属性值
							String fieldValue = getArrayProperty(paramValue, arrayField, i);

							// 对求出的属性值进行操作处理
							if (fieldValue != null && fieldValue.length() > 0 && !fieldOperate.equals("")) {
								fieldValue = ValueOperate.dealValue(fieldOperate, fieldValue, operates);
							}

							loopCode.replace(templateBean.getStartIndex(), templateBean.getEndIndex()
									+ templateBean.getEndSymbol().length(), fieldValue);
						}

						// 将解析后的循环语句保存到最终语句中
						returnValue.append(loopCode);
						loopCode.replace(0, loopCode.length(), processCode);
					}

					// 若存在操作参数，则对最终生成的语句进行相应的操作
					if (returnValue.length() > 0 && !operate.equals("")) {
						returnValue.replace(0, returnValue.length(),
								ValueOperate.dealValue(operate, returnValue.toString(), operates));
					}
				} else {
					logger.warn("循环语句提供的循环参数为空或者不是数组，默认忽略此语句,循环语句为:{}", code);
				}
			} else {
				logger.warn("循环语句没有提供循环参数，默认忽略此循环语句,循环语句为:{}", code);
			}
		} else {
			logger.warn("循环语句内容是空，默认忽略此循环语句,code:{}", code);
		}

		return returnValue.toString();
	}

	/**
	 * 此方法获取数组属性
	 * @param param Bean中数组的属性名
	 * @param arrayParam 数组元数据中的属性名
	 * @param index
	 * @return
	 * @author tang freedom
	 * @date 2017年1月6日上午10:45:16
	 * @since 1.0
	 */
	public String getArrayProperty(Object arrayObject, String fieldName, int index) {
		String arrayParamValue = "";

		// 获取数组中的对应索引的对象
		Object object = Array.get(arrayObject, index);

		// 取得对象中对应属性的值
		Object fieldValue = ParamParser.getFieldValue(object, fieldName);

		if (fieldValue != null) {
			arrayParamValue = String.valueOf(fieldValue);
		}

		return arrayParamValue;
	}

	@Override
	public void setParamParser(ParamParser paramParser) {
		this.paramParser = paramParser;
	}

	@Override
	public void setOperates(List<Operate> operates) {
		this.operates = operates;
	}
}
