package com.wanggt.freedom.codecreater.core.observer;

import java.util.List;

import com.wanggt.freedom.codecreater.core.symbol.Symbol;

/**
 * 实现了此接口的标记类，在代码生成器上注册时可以获取到代码生成器上注册的所有标记对象
 * @author freedom wang
 * @date 2017年6月23日下午2:48:00
 * @since 1.0
 */
public interface HasSymbol {

	/**
	 * 设置代码生成器里注册的标记对象
	 * @param symbols
	 * @author freedom wang
	 * @date 2017年6月23日下午2:46:51
	 * @since 1.0
	 */
	void setSymbols(List<Symbol> symbols);
}
