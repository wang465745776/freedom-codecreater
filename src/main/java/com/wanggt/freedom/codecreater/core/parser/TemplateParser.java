package com.wanggt.freedom.codecreater.core.parser;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wanggt.freedom.codecreater.core.symbol.ParamSymbol;
import com.wanggt.freedom.codecreater.core.symbol.Symbol;

/**
 * 模板解析器
 * @author freedom wang
 * @date 2017年6月23日上午10:26:07
 * @since 1.0
 */
public class TemplateParser {
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(TemplateParser.class);

	/**
	 * 解析模板代码
	 * @param code 待解析模板代码
	 * @param symbols 标记集
	 * @return 已解析代码
	 * @author freedom wang
	 * @date 2017年6月21日上午9:56:06
	 * @since 1.0
	 */
	public static String analyzeTemplate(String code, List<Symbol> symbols) {
		StringBuffer analyticalCode = new StringBuffer(code);// 解析过程变量
		TemplateBean rootTemplate = new TemplateBean(); // 根模板对象

		try {
			// 循环所有的模板代码
			while (hasTemplateCode(analyticalCode.toString(), symbols)) {
				// 获取模板对象
				TemplateBean templateBean = getFirstTemplateSymbol(analyticalCode.toString(), symbols);

				// 解析模板对象
				String parsedCode = parseTemplate(rootTemplate, templateBean, symbols);

				// 替换模板代码
				analyticalCode.replace(templateBean.getStartIndex(),
						templateBean.getEndIndex() + templateBean.getEndSymbol().length(), parsedCode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return analyticalCode.toString();
	}

	/**
	 * 解析模板對象
	 * @param templateBean
	 * @author freedom wang
	 * @date 2018年4月13日上午9:54:56
	 * @since 3.0.0
	 */
	public static String parseTemplate(TemplateBean preTemplate, TemplateBean template, List<Symbol> symbols) {
		// TODO 参数校验
		// if (templateBean1 == null) {
		// throw new NullPointerException("解析模板对象失败，因为模板对象为空！");
		// }

		// 模板内容准确性校验

		//
		template.setPreTemplate(preTemplate);

		if (hasTemplateCode(template.getCode(), symbols)) {
			while (hasTemplateCode(template.getCode(), symbols)) {
				TemplateBean nextTemplate = getFirstTemplateSymbol(template.getCode(), symbols);
				nextTemplate.setPreTemplate(template);

				// 考虑多层模板嵌套的情况，使用递归进行处理
				if (hasTemplateCode(nextTemplate.getCode(), symbols)) {
					nextTemplate.setCode(parseTemplate(template, nextTemplate, symbols));
				}

				// 根据不同的模板符号进行不同的处理
				for (Symbol oneSymbol : symbols) {
					if (oneSymbol.getSymbolStart().equals(nextTemplate.getStartSymbol())) {

						// 如果是变量标记，则设置变量为父模板对象的局部变量
						if (oneSymbol instanceof ParamSymbol) {
							Properties param = ((ParamSymbol) oneSymbol).parseParam(nextTemplate);
							preTemplate.addLocalProperties(param);
						} else {
							String parsedCode = oneSymbol.parse(nextTemplate);

							parsedCode = template.getCode().substring(0, nextTemplate.getStartIndex()) + parsedCode;
							parsedCode = parsedCode + template.getCode()
									.substring(nextTemplate.getEndIndex() + nextTemplate.getEndSymbol().length());

							template.setCode(parsedCode);
						}

						// 退出循环
						break;
					}
				}
			}
		}
		
		// 根据不同的模板符号进行不同的处理
		for (Symbol oneSymbol : symbols) {
			if (oneSymbol.getSymbolStart().equals(template.getStartSymbol())) {

				// 如果是变量标记，则设置变量为父模板对象的局部变量
				if (oneSymbol instanceof ParamSymbol) {
					Properties param = ((ParamSymbol) oneSymbol).parseParam(template);
					preTemplate.addLocalProperties(param);

					// 变量标记要去除
					template.setCode("");
				} else {
					template.setCode(oneSymbol.parse(template));
				}

				// 退出循环
				break;
			}
		}

		return template.getCode();
	}

	/**
	 * 获取模板代码中的模板对象，模板对象中包含了模板代码及其位置等信息，若不存在则返回null
	 * 
	 * @param code 模板代码
	 * @param symbols 标记集
	 * @return
	 * @author freedom wang
	 * @date 2017年6月23日上午10:02:02
	 * @since 1.0
	 */
	public static TemplateBean getFirstTemplateSymbol(String code, List<Symbol> symbols) {
		TemplateBean templateBean = null;

		// 判断代码中是否存在任意一种模板标记
		if (hasTemplateCode(code, symbols)) {
			// 找到模板中第一个出现的模板符号
			int firstSymbolIndex = -1;
			Symbol firstSymbol = null;
			for (Symbol oneSymbol : symbols) {
				int index = getFirstIndex(code, 0, oneSymbol);
				// 因为多个模板符号都可能存在，所以必须取得真正的第一个模板符号所在的位置
				if (index != -1 && (firstSymbolIndex == -1 || index < firstSymbolIndex)) {
					firstSymbolIndex = index;
					firstSymbol = oneSymbol;
				}
			}

			// 找到第一个模板符号的第一个模板代码
			templateBean = getFirstTemplateBean(code, 0, firstSymbol);
		}

		return templateBean;
	}

	/**
	 * 判断模板代码中是否存在待解析模板，若存在则返回true,否则返回false
	 * 
	 * @param code 模板代码
	 * @param startSymbol 开始标记
	 * @param endSymbol 结束标记
	 * @return 若存在返回true,否则返回false
	 * @author freedom wang
	 * @date 2017年1月1日下午7:19:21
	 * @since 1.0
	 */
	public static boolean hasTemplateCode(String code, String startSymbol, String endSymbol) {
		boolean flag = false;
		int startIndex = code.indexOf(startSymbol);

		if (startIndex != -1) {
			int endIndex = getEndSymbolIndex(code, startIndex + startSymbol.length(), startSymbol, endSymbol);

			if (endIndex != -1) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * 判断模板代码中是否存在待解析模板，若存在则返回true,否则返回false。
	 * @param code 模板代码
	 * @param symbols 用于匹配的标记集
	 * @return 若存在则返回true,否则返回false
	 * @author freedom wang
	 * @date 2017年1月1日上午11:32:38
	 * @since 1.0
	 */
	public static boolean hasTemplateCode(String code, List<Symbol> symbols) {
		// 循环标记集，判断模板代码中是否存在待解析模板
		for (Symbol oneSymbol : symbols) {
			if (hasTemplateCode(code, oneSymbol.getSymbolStart(), oneSymbol.getSymbolEnd())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取模板代码中指定标记的第一个模板对象,若获取失败，返回null.
	 * 
	 * @param code 模板代码
	 * @param fromIndex 开始位置
	 * @param startSymbol 开始标记
	 * @param endSymbol 结束标记
	 * @return
	 * @author freedom wang
	 * @date 2017年1月6日上午10:47:28
	 * @since 1.0
	 */
	public static TemplateBean getFirstTemplateBean(String code, int fromIndex, String startSymbol, String endSymbol) {
		int startIndex = code.indexOf(startSymbol, fromIndex);

		if (startIndex != -1) {
			int endIndex = getEndSymbolIndex(code, startIndex + startSymbol.length(), startSymbol, endSymbol);

			if (endIndex != -1) {
				TemplateBean templateBean = new TemplateBean();
				templateBean.setStartIndex(startIndex);
				templateBean.setEndIndex(endIndex);
				templateBean.setCode(code.substring(startIndex + startSymbol.length(), endIndex));
				templateBean.setStartSymbol(startSymbol);
				templateBean.setEndSymbol(endSymbol);
				return templateBean;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取模板代码中指定标记的第一个模板对象,若获取失败，返回null。
	 * 
	 * @param code 模板代码
	 * @param fromIndex 开始标记
	 * @param symbol 标记对象
	 * @return
	 * @author freedom wang
	 * @date 2017年6月23日上午10:00:24
	 * @since 1.0
	 */
	public static TemplateBean getFirstTemplateBean(String code, int fromIndex, Symbol symbol) {
		return getFirstTemplateBean(code, fromIndex, symbol.getSymbolStart(), symbol.getSymbolEnd());
	}

	/**
	 * 获取模板代码中指定标记的第一个位置，若不存在开始标记或者不存在第一个开始标记匹配的结束标记，则返回-1。
	 * 
	 * @param code 模板代码
	 * @param fromIndex 起始位置
	 * @param startSymbol 开始标记
	 * @param endSymbol 结束标记
	 * @return 若存在则返回开始标记所在位置，若不存在开始标记或者不存在第一个开始标记匹配的结束标记，则返回-1
	 * @author freedom wang
	 * @date 2017年1月6日上午10:48:01
	 * @since 1.0
	 */
	public static int getFirstIndex(String code, int fromIndex, String startSymbol, String endSymbol) {
		int startIndex = code.indexOf(startSymbol, fromIndex);

		if (startIndex != -1) {
			int endIndex = getEndSymbolIndex(code, startIndex + startSymbol.length(), startSymbol, endSymbol);

			if (endIndex != -1) {
				return startIndex;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

	/**
	 * 获取模板代码中指定标记的第一个位置，若不存在开始标记或者不存在第一个开始标记匹配的结束标记，则返回-1。
	 * 
	 * @param code 模板代码
	 * @param fromIndex 起始位置
	 * @param symbol 标记对象
	 * @return 若存在则返回开始标记坐在位置，若不存在返回-1
	 * @author freedom wang
	 * @date 2017年6月23日上午9:55:02
	 * @since 1.0
	 */
	public static int getFirstIndex(String code, int fromIndex, Symbol symbol) {
		return getFirstIndex(code, fromIndex, symbol.getSymbolStart(), symbol.getSymbolEnd());
	}

	/**
	 * 获取以某标记开头的模板代码中，标记包裹的内容
	 * @param code
	 * @param symbolBegin
	 * @param symbolEnd
	 * @return
	 * @author freedom wang
	 * @date 2017年6月23日上午10:31:25
	 * @since 1.0
	 */
	public static String getSymbolContentStart(String code, String symbolBegin, String symbolEnd) {
		String content = "";

		if (code.startsWith(symbolBegin) && code.contains(symbolEnd)) {
			content = code.substring(symbolBegin.length(), code.indexOf(symbolEnd));
		}

		return content;
	}

	/**
	 * 移除已某标记开头的模板代码中，第一个标记部分
	 * 
	 * @param code
	 * @param symbolBegin
	 * @param symbolEnd
	 * @return
	 * @author freedom wang
	 * @date 2017年6月23日上午10:33:26
	 * @since 1.0
	 */
	public static String removeSymbolStart(String code, String symbolBegin, String symbolEnd) {
		if (code.startsWith(symbolBegin) && code.contains(symbolEnd)) {
			code = code.substring(code.indexOf(symbolEnd) + symbolEnd.length());
		}

		return code;
	}

	/**
	 * 获取模板代码中结束标签所在的位置，若不存在则返回-1。
	 * 
	 * @param code 模板代码
	 * @param fromIndex 模板代码的开始位置
	 * @param startSymbol 开始标记
	 * @param endSymbol 结束标记
	 * @return 若存在，则返回结束标签所在的位置，若不存在，则返回-1
	 * @author freedom wang
	 * @date 2017年6月27日下午3:07:21
	 * @since 1.0
	 */
	public static int getEndSymbolIndex(String code, int fromIndex, String startSymbol, String endSymbol) {
		int mark = 1;
		int endIndex = -1;

		while (mark > 0) {
			endIndex = getCompareSymbolIndex(code, fromIndex, startSymbol, endSymbol);
			int compare = compareSymbolIndex(code, fromIndex, startSymbol, endSymbol);
			mark += compare;

			if (compare == 0) {
				endIndex = -1;
				break;
			} else if (compare > 0) {
				fromIndex = endIndex + startSymbol.length();
			} else {
				fromIndex = endIndex + endSymbol.length();
			}
		}

		return endIndex;
	}

	/**
	 * 判断代码中的标记是开始标签近还是结束标签近
	 * 
	 * @param code 代码
	 * @param fromIndex 开始位置
	 * @param startSymbol 开始标签
	 * @param endSymbol 结束标签
	 * @return int,若开始标签比较近，则返回1，若结束标签比较近，则返回-1,若都不存在返回0
	 * @author freedom wang
	 * @date 2017年6月27日下午2:04:55
	 * @since 1.0
	 */
	public static int compareSymbolIndex(String code, int fromIndex, String startSymbol, String endSymbol) {
		int startIndex = code.indexOf(startSymbol, fromIndex);
		int endIndex = code.indexOf(endSymbol, fromIndex);

		if ((startIndex == -1 && endIndex != -1) || (startIndex != -1 && endIndex == -1)) {
			return startIndex > endIndex ? 1 : -1;
		}

		if (startIndex != -1 && endIndex != -1) {
			return startIndex > endIndex ? -1 : 1;
		}

		return 0;
	}

	/**
	 * 判断代码中的标记是开始标签近还是结束标签近，并获取比较近的标签的位置
	 * 
	 * @param code 代码
	 * @param fromIndex 开始位置
	 * @param startSymbol 开始标签
	 * @param endSymbol 结束标签
	 * @return int,若开始标签比较近，则返回开始标签的位置，若结束标签比较近，则返回结束标签的位置,若都不存在返回-1
	 * @author freedom wang
	 * @date 2017年6月27日下午2:10:52
	 * @since 1.0
	 */
	public static int getCompareSymbolIndex(String code, int fromIndex, String startSymbol, String endSymbol) {
		int startIndex = code.indexOf(startSymbol, fromIndex);
		int endIndex = code.indexOf(endSymbol, fromIndex);

		if ((startIndex == -1 && endIndex != -1) || (startIndex != -1 && endIndex == -1)) {
			return startIndex > endIndex ? startIndex : endIndex;
		}

		if (startIndex != -1 && endIndex != -1) {
			return startIndex > endIndex ? endIndex : startIndex;
		}

		return -1;
	}
}
