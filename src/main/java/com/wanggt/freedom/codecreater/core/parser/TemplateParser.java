package com.wanggt.freedom.codecreater.core.parser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @param code
	 * @param symbols
	 * @return
	 * @author freedom wang
	 * @date 2017年6月21日上午9:56:06
	 * @since 1.0
	 */
	public static String analyzeTemplate(String code, List<Symbol> symbols) {
		StringBuffer analyticalCode = new StringBuffer(code);// 解析得到的代码

		try {
			while (hasTemplateCode(analyticalCode.toString(), symbols)) {
				TemplateBean templateBean = getFirstTemplateSymbol(analyticalCode.toString(), symbols);

				String templateCode = templateBean.getCode();

				// 考虑多层模板嵌套的情况，使用递归进行处理
				if (hasTemplateCode(templateCode, symbols)) {
					templateCode = analyzeTemplate(templateCode, symbols);
				}

				// 根据不同的模板符号进行不同的处理
				for (Symbol oneSymbol : symbols) {
					if (oneSymbol.getSymbolStart().equals(templateBean.getStartSymbol())) {
						String parsedCode = oneSymbol.parse(templateCode);

						analyticalCode.replace(templateBean.getStartIndex(),
								templateBean.getEndIndex() + templateBean.getEndSymbol().length(), parsedCode);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return analyticalCode.toString();
	}

	/**
	 * 获取代码中的模板代码及其位置
	 * @param code 模板代码
	 * @param symbols 标记集
	 * @return 若不存在返回null
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
	 * 获取模板代码中指定标记的第一个模板对象
	 * @param code 模板代码
	 * @param fromIndex 开始位置
	 * @param startSymbol 开始标记
	 * @param endSymbol 结束标记
	 * @return 若获取失败，返回null
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
	 * 获取第一个模板对象
	 * @param string
	 * @param fromIndex
	 * @param symbol
	 * @return
	 * @author freedom wang
	 * @date 2017年6月23日上午10:00:24
	 * @since 1.0
	 */
	public static TemplateBean getFirstTemplateBean(String string, int fromIndex, Symbol symbol) {
		return getFirstTemplateBean(string, fromIndex, symbol.getSymbolStart(), symbol.getSymbolEnd());
	}

	/**
	 * 获取模板代码中指定标记的第一个位置，若不存在开始标记或者不存在第一个开始标记匹配的结束标记，则返回-1。
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
